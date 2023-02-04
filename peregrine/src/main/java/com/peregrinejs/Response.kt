package com.peregrinejs

import kotlinx.serialization.json.*

internal enum class ResponseStatus {
    SUCCESS, ERROR
}

internal data class Response(
    val id: String,
    val status: ResponseStatus,
    val data: JsonElement,
) {
    fun serialize(): JsonObject {
        val dataKey = when (status) {
            ResponseStatus.SUCCESS -> "data"
            ResponseStatus.ERROR -> "error"
        }

        return buildJsonObject {
            put("id", id)
            put("status", status.name.lowercase())
            put(dataKey, data)
        }
    }
}
