package com.peregrinejs

import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.webkit.WebViewClientCompat

internal class WebViewClient(val frame: WebFrame) : WebViewClientCompat() {
    private val server = Server(frame)

    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
        return server.handle(request) ?: super.shouldInterceptRequest(view, request)
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, request.url)
        frame.context.startActivity(intent)

        return true
    }
}
