package com.mrmpn.videogamedb.data

import com.mrmpn.videogamedb.data.models.GameDataModel

class GameRepository(private val gameDataSource: GameDataSource) {
    suspend fun getTrendingGames(): List<GameDataModel> {
        return gameDataSource.fetchTrendingGames()
    }
}
