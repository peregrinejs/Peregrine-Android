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
