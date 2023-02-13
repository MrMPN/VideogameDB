package com.mrmpn.videogamedb.domain

interface IGameRepository {

    suspend fun getTrendingGames(): Result<List<Game>>
}
