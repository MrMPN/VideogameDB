package com.mrmpn.videogamedb.data

import com.mrmpn.videogamedb.data.remote.ApiResponse
import com.mrmpn.videogamedb.data.remote.GameDataSource
import com.mrmpn.videogamedb.domain.Game
import com.mrmpn.videogamedb.domain.IGameRepository
import javax.inject.Inject

class GameRepository @Inject constructor(private val gameDataSource: GameDataSource) : IGameRepository {
    override suspend fun getTrendingGames(): Result<List<Game>> {
        return when (val apiResult = gameDataSource.fetchTrendingGames()) {
            is ApiResponse.Success -> Result.success(apiResult.body.results)
            is ApiResponse.Error.HttpError -> Result.failure(apiResult.e)
            is ApiResponse.Error.NetworkError -> Result.failure(apiResult.e)
            is ApiResponse.Error.SerializationError -> Result.failure(apiResult.e)
            is ApiResponse.Error.UnknownError -> Result.failure(apiResult.e)
        }
    }
}
