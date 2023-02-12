package com.mrmpn.videogamedb.data

import com.mrmpn.videogamedb.data.models.GameDataModel
import com.mrmpn.videogamedb.data.models.RawgAPIResponse
import com.mrmpn.videogamedb.data.remote.ApiResponse

interface GameDataSource {
    suspend fun fetchTrendingGames(): ApiResponse<RawgAPIResponse<GameDataModel>>
}