package com.mrmpn.videogamedb.data.remote

import com.mrmpn.videogamedb.data.GameDataSource
import com.mrmpn.videogamedb.data.models.GameDataModel
import com.mrmpn.videogamedb.data.models.RawgAPIResponse
import javax.inject.Inject

class RAWGGameDataSource @Inject constructor(private val rawgApiClient: RawgApiClient) : GameDataSource {
    override suspend fun fetchTrendingGames(): ApiResponse<RawgAPIResponse<GameDataModel>> {
        return rawgApiClient.getGames()
    }
}