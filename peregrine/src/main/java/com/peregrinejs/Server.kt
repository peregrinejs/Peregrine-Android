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

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import com.peregrinejs.ext.*

internal class Server(private val frame: WebFrame) {
    private val appHandler = AppPathHandler(frame.context, frame.configuration.baseUrl)
    private val rpcHandler = RPCPathHandler(frame)

    fun handle(request: WebResourceRequest): WebResourceResponse? {
        val url = request.url

        return when (url.origin) {
            frame.configuration.appUrl.origin -> handleAppUrl(url)
            frame.configuration.peregrineUrl.origin -> handlePeregrineUrl(url)
            else -> null
        }
    }

    private fun handleAppUrl(url: Uri): WebResourceResponse? {
        return if (frame.configuration.local && frame.configuration.appUrl contains url)
            appHandler.handle(url.normalizedPath)
        else
            null
    }

    private fun handlePeregrineUrl(url: Uri): WebResourceResponse? {
        val segments = url.pathSegments

        return when (segments.first()) {
            PEREGRINE_RPC_DIRECTIVE -> rpcHandler.handle(url.normalizedPath)
            PEREGRINE_USER_DIRECTIVE -> handleUserDirective(url)
            else -> null
        }
    }

    private fun handleUserDirective(url: Uri): WebResourceResponse? {
        val segments = url.pathSegments
        val directivePath = "/${segments.drop(1).joinToString("/")}"
        val directiveUrl = url.buildUpon()
            .path(directivePath)
            .build()

        return frame.assetLoader.shouldInterceptRequest(directiveUrl)
    }
}
