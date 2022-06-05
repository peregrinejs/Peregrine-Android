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
