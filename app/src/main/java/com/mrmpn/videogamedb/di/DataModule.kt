package com.mrmpn.videogamedb.di

import com.mrmpn.videogamedb.data.remote.GameDataSource
import com.mrmpn.videogamedb.data.remote.RAWGGameDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindGameDataSource(rawgGameDataSource: RAWGGameDataSource): GameDataSource
}
