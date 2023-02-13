package com.mrmpn.videogamedb.di

import com.mrmpn.videogamedb.data.remote.GameDataSource
import com.mrmpn.videogamedb.data.remote.RAWGGameDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindGameDataSource(rawgGameDataSource: RAWGGameDataSource): GameDataSource
}
