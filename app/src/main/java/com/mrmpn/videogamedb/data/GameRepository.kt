package com.mrmpn.videogamedb.data

import com.mrmpn.videogamedb.data.models.GameDataModel
import javax.inject.Inject

class GameRepository @Inject constructor(private val gameDataSource: GameDataSource) {
    suspend fun getTrendingGames(): List<GameDataModel> {
        return gameDataSource.fetchTrendingGames()
    }
}
