package com.peregrinejs

import android.content.res.AssetManager
import com.peregrinejs.ext.*
import java.io.InputStream
import java.net.URLConnection

internal class AssetLoader(private val assets: AssetManager) : FileLoader {
    companion object {
        const val DEFAULT_MIME_TYPE = "text/plain"
    }

    override fun open(path: Path): InputStream {
        return assets.open(path.trimStart('/'), AssetManager.ACCESS_STREAMING)
    }

    override fun guessMimeType(path: Path): String {
        return when (path.extension) {
            ".js", ".mjs" -> "application/javascript"
            ".wasm" -> "application/wasm"
            else -> URLConnection.guessContentTypeFromName(path) ?: DEFAULT_MIME_TYPE
        }
    }
}
