package com.mrmpn.videogamedb.data.remote

import com.mrmpn.videogamedb.data.models.GameDataModel
import com.mrmpn.videogamedb.data.models.RawgAPIResponse

interface GameDataSource {
    suspend fun fetchTrendingGames(): ApiResponse<RawgAPIResponse<GameDataModel>>
}
