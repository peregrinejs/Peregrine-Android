package com.peregrinejs

import kotlinx.serialization.json.*
import java.lang.IllegalStateException

class Request internal constructor(
    internal val id: String,
    internal val function: String,
    val data: JsonElement,
) {
    internal companion object {
        internal fun deserialize(element: JsonObject): Request {
            val idElement = element["id"] ?: throw IllegalStateException("'id' key must exist")
            val functionElement = element["function"] ?: throw IllegalStateException("'function' key must exist")
            val dataElement = element["data"] ?: JsonNull

            return Request(
                id = idElement.jsonPrimitive.content,
                function = functionElement.jsonPrimitive.content,
                data = dataElement,
            )
        }
    }

    val text: String
        get() = data.jsonPrimitive.content

    inline fun <reified T> json(): T {
        return Json.decodeFromJsonElement(data)
    }
}
