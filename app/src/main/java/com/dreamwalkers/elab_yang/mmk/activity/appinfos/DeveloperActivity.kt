package com.dreamwalkers.elab_yang.mmk.activity.appinfos

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.dreamwalkers.elab_yang.mmk.R
import com.dreamwalkers.elab_yang.mmk.adapter.appinfo.DeveloperAdapter
import com.dreamwalkers.elab_yang.mmk.model.appinfos.Developer
import kotlinx.android.synthetic.main.activity_developer.*

class DeveloperActivity : AppCompatActivity() {

    lateinit var developerAdapter: DeveloperAdapter
    lateinit var developerList: ArrayList<Developer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)
        setSupportActionBar(toolbar)

        developerList = getDeveloper()
        developerAdapter = DeveloperAdapter(this, developerList)

        with(recycler_view) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = developerAdapter
        }
    }

    private fun getDeveloper(): ArrayList<Developer> {
        return arrayListOf(Developer("Dreamwalker", "https://github.com/JAICHANGPARK"),
                Developer("kin7274", "https://github.com/kin7274")
        )
    }
}
