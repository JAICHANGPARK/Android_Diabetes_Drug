package com.dreamwalkers.elab_yang.mmk.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dreamwalkers.elab_yang.mmk.R
import com.dreamwalkers.elab_yang.mmk.activity.appinfos.DBManagementActivity
import com.dreamwalkers.elab_yang.mmk.activity.appinfos.DeveloperActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)

        db_management_button.setOnClickListener {
            startActivity(Intent(this, DBManagementActivity::class.java))

        }

        developer_button.setOnClickListener {
            startActivity(Intent(this, DeveloperActivity::class.java))
        }

    }
}
