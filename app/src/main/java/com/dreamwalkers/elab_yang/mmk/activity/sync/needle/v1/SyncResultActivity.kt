package com.dreamwalkers.elab_yang.mmk.activity.sync.needle.v1

import android.os.Bundle
import com.dreamwalkers.elab_yang.mmk.R
import com.dreamwalkers.elab_yang.mmk.activity.AbstractActivityBased
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_sync_result.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger

class SyncResultActivity : AbstractActivityBased() {

    lateinit var simpleDateFormat: SimpleDateFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_result)
        simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
        val values = intent.getStringExtra("SyncValue")
        result_text.text = values
        toast(values)
        val resultObject = JSONObject(values)
        val resultArray: JSONArray = resultObject.getJSONArray("received")
        result_text.append("\n\n")
        result_text.append(resultArray.toString())
        result_text.append("\n\n")
        Logger.getLogger(DataSyncActivity::class.java.name).warning("bbb" + resultArray.length())
        if (resultArray.length() != 0){
            Logger.getLogger(DataSyncActivity::class.java.name).warning("aaa" + resultArray.length())
            processJsonArray(resultArray)
        }else{
            result_text.text = "처리할 데이터가 없어요 "
        }
    }

    private fun processJsonArray(array: JSONArray) {

        for (i in 0 until array.length()) {

            Logger.getLogger(this@SyncResultActivity.localClassName).warning("" + i)
            val id = array.getJSONObject(i).getString("id")
            val year = array.getJSONObject(i).getString("value").substring(0, 4)
            val month = array.getJSONObject(i).getString("value").substring(4, 6)
            val days = array.getJSONObject(i).getString("value").substring(6, 8)
            val hour = array.getJSONObject(i).getString("value").substring(8, 10)
            val minutes = array.getJSONObject(i).getString("value").substring(10, 12)

            result_text.append("$id :: $year-$month-$days $hour:$minutes\n")
            val valueDate = simpleDateFormat.parse("$year-$month-$days $hour:$minutes:00")
            result_text.append(valueDate.time.toString())
            val calendar = Calendar.getInstance()
            calendar.time = valueDate
            val saveTime = calendar.get(Calendar.HOUR_OF_DAY)
            when (saveTime) {
                in 5..10 -> {
                    result_text.append("공복" + "\n")
                }
                in 11..15 -> {
                    result_text.append("점심식전" + "\n")
                }
                in 16..20 -> {
                    result_text.append("저녁식전" + "\n")
                }
                else -> {
                    result_text.append("취침전" + "\n")
                }
            }
        }

    }

    override fun bindView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initPaper() {
        Paper.init(this)
    }


    override fun initSetting() {
        initPaper()
    }
}
