package com.peregrinejs

import android.webkit.WebResourceResponse
import androidx.webkit.WebViewAssetLoader
import com.peregrinejs.ext.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.*
import kotlinx.serialization.json.*

internal class RPCPathHandler(private val frame: WebFrame) : WebViewAssetLoader.PathHandler {
    private var connection: MessageChannelConnection? = null

    private val defaultHeaders = mapOf(
        "Access-Control-Allow-Origin" to frame.configuration.allowedOrigin,
        "Cache-Control" to "no-cache",
        "Server" to "Peregrine",
    )

    override fun handle(path: Path): WebResourceResponse {
        val (_, operation) = path.segments

        return when (operation) {
            "subscribe" -> handleSubscribe()
            else -> WebResourceResponses.BadRequest(headers = defaultHeaders)
        }
    }

    private fun handleSubscribe(): WebResourceResponse {
        connection?.close()
        connection = MessageChannelConnection(
            webView = frame.webView,
            url = frame.configuration.appUrl,
            didReceiveMessage = ::receiveWebMessage
        )

        for ((observableName, observable) in frame.configuration.observables) {
            require(observableName.endsWith('$')) { "Observable names must end with a dollar sign ($)" }

            observable
                .onEach { event -> handleEvent(observableName, event) }
                .launchIn(frame.scope)
        }

        return WebResourceResponses.OK(headers = defaultHeaders)
    }

    private fun handleEvent(observable: String, event: Event) {
        val message = buildJsonObject {
            put("observable", observable)
            put("data", event.serialize())
        }

        connection?.send(Json.encodeToString(message))
    }

    private fun receiveWebMessage(message: String) {
        val request = Request.deserialize(Json.decodeFromString(message))
        val function = frame.configuration.functions[request.function]

        val call = Call(
            request,
            didRespond = { response ->
                connection?.send(Json.encodeToString(response.serialize()))
            }
        )

        if (function == null) {
            call.fail(
                "Remote function not found: '${request.function}'",
                "REMOTE_FUNCTION_NOT_FOUND"
            )
        } else {
            frame.scope.launch {
                function(call)
            }
        }
    }
}
