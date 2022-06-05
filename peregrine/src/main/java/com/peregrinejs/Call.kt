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
