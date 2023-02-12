package com.mrmpn.videogamedb.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.JsonConvertException
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T> =
    try {
        val response = request { block() }
        ApiResponse.Success(response.body())
    } catch (e: ResponseException) {
        ApiResponse.Error.HttpError(e.response.status.value, e.response.bodyAsText(), e)
    } catch (e: IOException) {
        ApiResponse.Error.NetworkError(e)
    } catch (e: SerializationException) {
        ApiResponse.Error.SerializationError(e)
    } catch (e: JsonConvertException) {
        ApiResponse.Error.SerializationError(e)
    } catch (e: Exception) {
        ApiResponse.Error.UnknownError(e)
    }