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

import android.webkit.WebResourceResponse
import com.peregrinejs.ext.*
import java.io.InputStream

internal class WebResourceResponses {
    class OK(
        mimeType: String? = null,
        headers: Map<String, String>? = null,
        data: InputStream? = null
    ) : WebResourceResponse(
        mimeType,
        "UTF-8",
        200,
        200.reasonPhrase,
        headers,
        data
    )

    class BadRequest(
        mimeType: String? = null,
        headers: Map<String, String>? = null,
        data: InputStream? = "Bad Request".byteInputStream()
    ) : WebResourceResponse(
        mimeType,
        "UTF-8",
        400,
        400.reasonPhrase,
        headers,
        data
    )

    class NotFound(
        mimeType: String? = null,
        headers: Map<String, String>? = null,
        data: InputStream? = "Not Found".byteInputStream()
    ) : WebResourceResponse(
        mimeType,
        "UTF-8",
        404,
        404.reasonPhrase,
        headers,
        data
    )
}
