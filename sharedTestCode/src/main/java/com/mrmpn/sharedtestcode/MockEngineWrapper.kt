package com.mrmpn.sharedtestcode

import com.google.common.collect.LinkedListMultimap
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandler
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Wrapper around [MockEngine] to make it possible to add mock responses
 * after the engine has been created
 */
class MockEngineWrapper(dispatcher: CoroutineDispatcher) {

    data class RequestPath(val method: HttpMethod, val encodedPath: String)
    data class MockResponse(
        val method: HttpMethod = HttpMethod.Get,
        val path: String,
        val jsonAsString: String? = null,
        val statusCode: HttpStatusCode = HttpStatusCode.OK
    )

    private val handlers: LinkedListMultimap<RequestPath, MockRequestHandler> =
        LinkedListMultimap.create()


    val engine = MockEngine.create {
        this.dispatcher = dispatcher
        reuseHandlers = true
        addHandler { request ->
            val path = RequestPath(request.method, request.url.encodedPath)
            handlers.get(path).firstOrNull()
                ?.let { handler ->
                    handlers.remove(path, handler)
                    handler(request)
                }
                ?: error("No handler installed for path $path")
        }
    }

    private fun addHandler(request: RequestPath, handler: MockRequestHandler) {
        handlers.put(request, handler)
    }

    /**
     * Add a mock response to the engine
     *
     * @param method The HTTP method to respond to
     * @param path The path to respond to
     * @param jsonAsString The path to the JSON file to respond with. If null, an empty JSON object will be returned
     * @param statusCode The HTTP status code to respond with
     */
    private fun addMockResponse(
        method: HttpMethod = HttpMethod.Get,
        path: String,
        jsonAsString: String? = null,
        statusCode: HttpStatusCode
    ) {
        addHandler(RequestPath(method, path)) { request ->
            when (request.url.encodedPath) {
                path -> {
                    respond(
                        content = jsonAsString?.let { ByteReadChannel(jsonAsString) }
                            ?: ByteReadChannel(""),
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                        status = statusCode
                    )
                }

                else -> {
                    error("Unhandled ${request.url.encodedPath}")
                }
            }
        }
    }

    /**
     * Add multiple mock responses to the engine
     *
     * @param responses The responses to add
     */
    fun addMockResponses(vararg responses: MockResponse) {
        responses.forEach { response ->
            addMockResponse(
                method = response.method,
                path = response.path,
                jsonAsString = response.jsonAsString,
                statusCode = response.statusCode
            )
        }
    }
}