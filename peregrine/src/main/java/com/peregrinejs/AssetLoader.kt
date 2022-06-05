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
