package com.dreamwalkers.elab_yang.mmk.activity

import android.support.v7.app.AppCompatActivity

abstract class AbstractActivityBased : AppCompatActivity() {

    abstract fun initPaper()
    abstract fun bindView()
    abstract fun initSetting()

}