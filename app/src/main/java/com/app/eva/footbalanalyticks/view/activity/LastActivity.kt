package com.app.eva.footbalanalyticks.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.app.eva.footbalanalyticks.R
import com.app.eva.footbalanalyticks.databinding.ActivityLastBinding
import com.app.eva.footbalanalyticks.model.WebViewSetupHelper

class LastActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    lateinit var binding: ActivityLastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webView = binding.webView
        val combinedData = intent.getStringExtra("combinedData")
        val webViewSetupHelper = WebViewSetupHelper(this, webView, combinedData)
        webViewSetupHelper.setupWebView()
        webViewSetupHelper.load()
        if(webView.canGoBack()){
            webView.goBack()
        }
    }
}