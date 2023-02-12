package com.mrmpn.videogamedb.data

import com.mrmpn.videogamedb.data.models.GameDataModel
import com.mrmpn.videogamedb.data.remote.ApiResponse
import javax.inject.Inject

class GameRepository @Inject constructor(private val gameDataSource: GameDataSource) {
    suspend fun getTrendingGames(): Result<List<GameDataModel>> {
        return when(val apiResult = gameDataSource.fetchTrendingGames()) {
            is ApiResponse.Success -> Result.success(apiResult.body.results)
            is ApiResponse.Error.HttpError -> Result.failure(apiResult.e)
            is ApiResponse.Error.NetworkError -> Result.failure(apiResult.e)
            is ApiResponse.Error.SerializationError -> Result.failure(apiResult.e)
            is ApiResponse.Error.UnknownError -> Result.failure(apiResult.e)
        }
    }
}
