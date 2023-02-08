package com.mrmpn.videogamedb.data

import com.mrmpn.videogamedb.data.models.GameDataModel
import com.mrmpn.videogamedb.ui.providers.GamePreviewParameterProvider
import kotlinx.coroutines.delay

class GameRepository {
    suspend fun getTrendingGames(): List<GameDataModel> {
        delay(100)
        return GamePreviewParameterProvider().values.map { it.toDataModel() }.toList()
    }
}