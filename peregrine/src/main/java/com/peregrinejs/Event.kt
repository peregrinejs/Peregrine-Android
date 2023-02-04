package com.peregrinejs

import kotlinx.serialization.json.*

@Suppress("FunctionName")
inline fun <reified T> Event(json: T) = Event(Json.encodeToJsonElement(json))

class Event(val data: JsonElement) {
    internal fun serialize(): JsonElement {
        return data
    }
}
