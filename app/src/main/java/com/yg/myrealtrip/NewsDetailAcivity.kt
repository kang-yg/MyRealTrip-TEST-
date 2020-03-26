package com.yg.myrealtrip

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.newsdetail_activity.*

class NewsDetailAcivity : AppCompatActivity() {

    lateinit var webViewSetting: WebSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newsdetail_activity)

        val title: String = intent.getStringExtra("title")
        Log.d("myData", "intent title : ${title}")
        val link: String = intent.getStringExtra("link")
        Log.d("myData", "intent link : ${link}")


        //Title
        newsdetail_title.text = title

        //WebView
        webViewSetting = newsdetail_webview.settings
        newsdetail_webview.webViewClient = WebViewClient()
        newsdetail_webview.webChromeClient = WebChromeClient()
        webViewSetting.javaScriptEnabled = true
        webViewSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        newsdetail_webview.loadUrl(link)
    }
}