package com.mrmpn.videogamedb.di

import android.util.Log
import com.mrmpn.videogamedb.data.remote.RawgApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {

    val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    fun provideHttpClient(
        engine: HttpClientEngine
    ): HttpClient {
        val httpClient = HttpClient(engine) {
            expectSuccess = true

            install(ContentNegotiation) {
                json(json = json)
            }

            addDefaultResponseValidation()

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Net", message)
                    }

                }
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 30_000
                socketTimeoutMillis = 30_000
            }
        }
        return httpClient
    }

    @Provides
    fun provideRawgApiClient(
        httpClient: HttpClient,
        @ApiUrl baseUrl: String = "",
        @ApiKey apiKey: String = ""
    ): RawgApiClient {
        val test = httpClient.config {
            defaultRequest {
                url(baseUrl)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                url.parameters.append("key", apiKey)
            }
        }
        return RawgApiClient(test)
    }
}
