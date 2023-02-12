package com.mrmpn.videogamedb.di

import android.content.Context
import com.mrmpn.videogamedb.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideHttpClientEngine(): HttpClientEngine = CIO.create()

    @Provides
    @ApiUrl
    fun provideBaseUrl(): String = "https://api.rawg.io/"

    @Provides
    @ApiKey
    fun provideApiKey(@ApplicationContext context: Context): String =
        context.resources.getString(R.string.rawg_api_key)
}