package com.mrmpn.videogamedb.data.remote

import com.mrmpn.videogamedb.data.models.GameDataModel
import com.mrmpn.videogamedb.data.models.RawgAPIResponse
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.path

class RawgApiClient(private val httpClient: HttpClient) {

    companion object {
        const val GAMES_PATH = "/api/games"
    }

    val apiPaths = Companion

    suspend fun getGames(): ApiResponse<RawgAPIResponse<GameDataModel>> {
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url { path(GAMES_PATH) }
        }
    }}