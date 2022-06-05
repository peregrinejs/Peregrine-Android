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

import android.content.Context
import android.net.Uri
import android.webkit.WebResourceResponse
import androidx.webkit.WebViewAssetLoader
import com.peregrinejs.ext.*
import java.io.IOException

internal class AppPathHandler(
    context: Context,
    private val baseUrl: Uri
) : WebViewAssetLoader.PathHandler {
    private val loader = AssetLoader(context.assets)

    private val defaultHeaders = mapOf(
        "Cache-Control" to "no-cache",
        "Server" to "Peregrine",
    )

    override fun handle(path: Path): WebResourceResponse {
        val fileUrl = resolve(path)
        val filePath = fileUrl.path!!

        val stream = try {
            loader.open(filePath)
        } catch (e: IOException) {
            return WebResourceResponses.NotFound(headers = defaultHeaders)
        }

        val mimeType = loader.guessMimeType(filePath)

        return WebResourceResponses.OK(
            mimeType,
            headers = defaultHeaders,
            data = stream
        )
    }

    private fun resolve(path: Path): Uri {
        val resolvedPath = path.resolve(indexFile = "index.html")

        return baseUrl.buildUpon()
            .appendEncodedPath(resolvedPath.trim('/'))
            .build()
    }
}
