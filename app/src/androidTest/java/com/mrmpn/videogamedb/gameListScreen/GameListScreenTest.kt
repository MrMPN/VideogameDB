package com.mrmpn.videogamedb.gameListScreen

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import com.mrmpn.sharedtestcode.MockEngineWrapper
import com.mrmpn.sharedtestcode.MockEngineWrapper.*
import com.mrmpn.sharedtestcode.loadFileText
import com.mrmpn.videogamedb.R
import com.mrmpn.videogamedb.data.models.GameDataModel
import com.mrmpn.videogamedb.data.models.RawgAPIResponse
import com.mrmpn.videogamedb.data.remote.RawgApiClient
import com.mrmpn.videogamedb.di.ApiKey
import com.mrmpn.videogamedb.di.ApiUrl
import com.mrmpn.videogamedb.di.AppModule
import com.mrmpn.videogamedb.di.HiltComponentActivity
import com.mrmpn.videogamedb.di.KtorModule
import com.mrmpn.videogamedb.ui.screens.trendingList.GameListScreen
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.HttpClientEngine
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.decodeFromString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@UninstallModules(AppModule::class)
@HiltAndroidTest
class GameListScreenTest {

    @Module
    @InstallIn(SingletonComponent::class)
    object TestModule {
        @Provides
        @Singleton
        fun provideTestDispatcher(): CoroutineDispatcher = UnconfinedTestDispatcher()

        @Provides
        @Singleton
        fun provideMockEngineWrapper(testDispatcher: CoroutineDispatcher): MockEngineWrapper =
            MockEngineWrapper(testDispatcher)

        @Provides
        fun provideHttpClientEngine(mockEngineWrapper: MockEngineWrapper): HttpClientEngine =
            mockEngineWrapper.engine

        @Provides
        @ApiUrl
        fun provideBaseUrl(): String = ""

        @Provides
        @ApiKey
        fun provideApiKey(): String = ""
    }

    @Inject
    lateinit var testDispatcher: CoroutineDispatcher

    @Inject
    lateinit var mockWrapper: MockEngineWrapper

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = createAndroidComposeRule<HiltComponentActivity>()

    private val json = loadFileText(this, "/mockResponses/GetGames200.json")

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun gameListScreenDisplaysList() = runTest(testDispatcher) {
        mockWrapper.addMockResponses(
            MockResponse(
                path = RawgApiClient.GAMES_PATH,
                jsonAsString = json,
                statusCode = HttpStatusCode.OK //200
            )
        )

        val response: RawgAPIResponse<GameDataModel> = KtorModule.json.decodeFromString(json)

        val tag = activityRule.activity.getString(R.string.tag_item_game_title)

        activityRule.setContent {
            VideogameDBTheme {
                GameListScreen()
            }
        }
        advanceUntilIdle()
        activityRule.onAllNodesWithTag(tag).assertCountEquals(response.results.size)
    }
}
