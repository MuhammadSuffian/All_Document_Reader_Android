package com.example.document_reader

import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class DocumentViewerActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_viewer)
        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        val uri: Uri? = intent.getParcelableExtra("documentUri")
        uri?.let {
            webView.loadUrl(it.toString())
        }
    }
}
