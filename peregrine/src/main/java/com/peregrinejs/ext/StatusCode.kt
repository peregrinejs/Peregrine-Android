package com.peregrinejs.ext

internal typealias StatusCode = Int

internal val StatusCode.reasonPhrase: String
    get() = when (this) {
        200 -> "OK"
        400 -> "Bad Request"
        404 -> "Not Found"
        500 -> "Internal Server Error"
        else -> error("Unknown status code: $this")
    }
