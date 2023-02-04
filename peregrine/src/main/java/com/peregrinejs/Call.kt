package com.peregrinejs

import kotlinx.serialization.json.*

class Call internal constructor(
    val request: Request,
    internal val didRespond: (response: Response) -> Unit
) {
    fun respond() {
        respond(JsonNull)
    }

    fun respond(text: String) {
        respond(Json.encodeToJsonElement(text))
    }

    inline fun <reified T> respond(json: T) {
        respond(Json.encodeToJsonElement(json))
    }

    fun respond(data: JsonElement) {
        val response = Response(
            id = request.id,
            status = ResponseStatus.SUCCESS,
            data = data,
        )

        respond(response)
    }

    fun fail(message: String = "Error", code: String? = null) {
        fail(CallError(message, code))
    }

    private fun respond(response: Response) {
        didRespond(response)
    }

    private fun fail(error: CallError) {
        val response = Response(
            id = request.id,
            status = ResponseStatus.ERROR,
            data = Json.encodeToJsonElement(error),
        )

        respond(response)
    }
}
