package com.peregrinejs

import android.annotation.SuppressLint
import android.net.Uri
import android.webkit.WebView
import androidx.webkit.WebMessageCompat
import androidx.webkit.WebMessagePortCompat
import androidx.webkit.WebViewCompat

@SuppressLint("RequiresFeature")
internal class MessageChannelConnection(
    private val webView: WebView,
    private val url: Uri,
    private val didReceiveMessage: (message: String) -> Unit
) {
    private lateinit var port: WebMessagePortCompat

    private val callback = object : WebMessagePortCompat.WebMessageCallbackCompat() {
        override fun onMessage(port: WebMessagePortCompat, message: WebMessageCompat?) {
            message?.data?.let { receive(it) }
        }
    }

    init {
        webView.post {
            val (port1, port2) = WebViewCompat.createWebMessageChannel(webView)

            port1.setWebMessageCallback(callback)

            // Transfers `port2` to the WebView by sending a 'message' event to `window`
            WebViewCompat.postWebMessage(
                webView,
                WebMessageCompat("peregrine:connect", arrayOf(port2)),
                url
            )

            port = port1
        }
    }

    fun send(message: String) {
        webView.post {
            port.postMessage(WebMessageCompat(message))
        }
    }

    fun close() {
        webView.post {
            port.close()
        }
    }

    private fun receive(message: String) {
        didReceiveMessage(message)
    }
}
