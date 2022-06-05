// Peregrine for Android: native container for hybrid apps
// Copyright (C) 2022 Caracal LLC
//
// This program is free software: you can redistribute it and/or modify it
// under the terms of the GNU General Public License version 3 as published
// by the Free Software Foundation.
//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
// more details.
//
// You should have received a copy of the GNU General Public License version 3
// along with this program. If not, see <https://www.gnu.org/licenses/>.

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
