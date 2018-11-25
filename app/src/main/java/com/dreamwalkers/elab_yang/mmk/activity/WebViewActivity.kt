package com.dreamwalkers.elab_yang.mmk.activity

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import com.dreamwalkers.elab_yang.mmk.R
import com.dreamwalkers.elab_yang.mmk.consts.IntentConst
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    internal lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        alertDialog = SpotsDialog.Builder().setContext(this).build()
        alertDialog.show()

        with(web_view) {
            settings.javaScriptEnabled = true
            webChromeClient = android.webkit.WebChromeClient()

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    alertDialog.dismiss()
                }
            }
        }

        if (intent != null) {
            if (!intent.getStringExtra(IntentConst.WEB_URL).isEmpty()) {
                web_view.loadUrl(intent.getStringExtra(IntentConst.WEB_URL))
            }
        }

    }
}
