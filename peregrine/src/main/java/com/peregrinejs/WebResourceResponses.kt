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
