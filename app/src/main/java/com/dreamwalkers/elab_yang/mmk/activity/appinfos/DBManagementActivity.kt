package com.dreamwalkers.elab_yang.mmk.activity.appinfos

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.dreamwalkers.elab_yang.mmk.R
import com.dreamwalkers.elab_yang.mmk.activity.DeleteDataBaseActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_db_management.*

class DBManagementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db_management)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener { v ->
            //            val builder = AlertDialog.Builder(this@DBManagementActivity)
//            builder.setTitle("알림")
//            builder.setMessage("장비(지능형 식판) 검색을 종료하시겠어요?")
//            builder.setPositiveButton(android.R.string.yes, { dialog, which -> finish() })
//            builder.setNegativeButton(android.R.string.no, { dialog, which -> dialog.dismiss() })
//            builder.show()
            finish()
        }

        initSetting()
        initButton()


    }


    private fun initSetting() {
        initToasty()
    }

    private fun initToasty() {
        Toasty.Config.getInstance().apply()
    }

    private fun initButton() {
        backup_button.setOnClickListener {
            Toasty.error(this, "공사중...", Toast.LENGTH_SHORT).show()
        }

        export_button.setOnClickListener {
            Toasty.error(this, "공사중...", Toast.LENGTH_SHORT).show()
        }

        delete_button.setOnClickListener {
            startActivity(Intent(this, DeleteDataBaseActivity::class.java))
        }
    }
}
