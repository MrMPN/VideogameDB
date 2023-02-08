package com.mrmpn.videogamedb.data

import com.mrmpn.videogamedb.data.models.GameDataModel
import com.mrmpn.videogamedb.ui.providers.GamePreviewParameterProvider
import kotlinx.coroutines.delay

class GameRepository(private val gameDataSource: GameDataSource) {
    suspend fun getTrendingGames(): List<GameDataModel> {
        return gameDataSource.fetchTrendingGames()
    }
}