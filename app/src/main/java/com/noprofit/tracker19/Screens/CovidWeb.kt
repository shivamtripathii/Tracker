package com.noprofit.tracker19.Screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.noprofit.tracker19.R
import kotlinx.android.synthetic.main.activity_covid_web.*

class CovidWeb : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid_web)
        lottieAnimationView.visibility = View.VISIBLE
        val num = intent.getIntExtra("key",2)
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                lottieAnimationView.visibility=View.INVISIBLE
            }
        }
        if(num==2)
            webView.loadUrl("https://www.coronatracker.com/")
        else if(num==3)
            webView.loadUrl("https://www.covid19india.org/")
        else if(num==1)
            webView.loadUrl("https://www.bing.com/covid/")
    }

    override fun onBackPressed() {
        finish();
    }
}
