package com.mrmpn.videogamedb.data

import com.mrmpn.videogamedb.data.models.GameDataModel
import com.mrmpn.videogamedb.ui.providers.GamePreviewParameterProvider
import kotlinx.coroutines.delay

interface GameDataSource {
    suspend fun fetchTrendingGames(): List<GameDataModel>
}

class RAWGGameDataSource : GameDataSource {
    override suspend fun fetchTrendingGames(): List<GameDataModel> {
        delay(100)
        return GamePreviewParameterProvider().values.map { it.toDataModel() }.toList()
    }
}