package com.mrmpn.videogamedb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mrmpn.videogamedb.data.GameRepository
import com.mrmpn.videogamedb.data.remote.RAWGGameDataSource
import com.mrmpn.videogamedb.data.remote.RawgApiClient
import com.mrmpn.videogamedb.data.remote.RawgApiClient.*
import com.mrmpn.videogamedb.di.KtorModule
import com.mrmpn.videogamedb.ui.screens.trendingList.GameListViewModel
import com.mrmpn.videogamedb.ui.screens.trendingList.GameListViewModel.UiState
import com.mrmpn.videogamedb.utils.MainDispatcherRule
import com.mrmpn.videogamedb.utils.MockEngineWrapper
import com.mrmpn.videogamedb.utils.MockEngineWrapper.*
import com.mrmpn.videogamedb.utils.containsExactlyInstancesOf
import com.mrmpn.videogamedb.utils.loadFileText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockWrapper = MockEngineWrapper(mainDispatcherRule.testDispatcher)
    private val httpClient = KtorModule.provideHttpClient(mockWrapper.engine)
    private val rawgApi: RawgApiClient = KtorModule.provideRawgApiClient(httpClient)

    private val gameDataSource = RAWGGameDataSource(rawgApi)
    private val gameRepository = GameRepository(gameDataSource)

    private lateinit var viewModel: GameListViewModel

    private val json = loadFileText(this, "/mockResponses/GetGames200.json")


    @Before
    fun setUp() {
        viewModel = GameListViewModel(gameRepository)
    }

    @Test
    fun `on HTTP code 200`() =
        runTest() {
            //Assemble
            mockWrapper.addMockResponses(
                MockResponse(
                    path = rawgApi.apiPaths.GAMES_PATH,
                    jsonAsString = json,
                    statusCode = HttpStatusCode.OK //200
                )
            )

            val uiStates = mutableListOf<UiState>()

            //Here we DO need for this to be executed as soon as there's an emission, if we wanna catch all states
            backgroundScope.launch(UnconfinedTestDispatcher()) {
                viewModel.uiState.toList(uiStates)
            }

            //Act
            advanceUntilIdle() //Implicitly calls viewModel.loadGames()

            //Assert
            uiStates.containsExactlyInstancesOf(
                UiState.InitialState::class.java,
                UiState.Loading::class.java,
                UiState.Success::class.java
            )
        }

    @Test
    fun `on HTTP code 400`() = runTest() {
        //Assemble
        mockWrapper.addMockResponses(
            MockResponse(
                path = rawgApi.apiPaths.GAMES_PATH,
                statusCode = HttpStatusCode.BadRequest // 400
            )
        )
        val uiStates = mutableListOf<UiState>()

        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.toList(uiStates)
        }

        //Act
        advanceUntilIdle() //Implicitly calls viewModel.loadGames()

        //Assert
        uiStates.containsExactlyInstancesOf(
            UiState.InitialState::class.java,
            UiState.Loading::class.java,
            UiState.Error::class.java
        )
    }

    @Test
    fun `on HTTP code 500 and then HTPP code 200`() =
        runTest() {
            //Assemble
            mockWrapper.addMockResponses(
                MockResponse(
                    path = rawgApi.apiPaths.GAMES_PATH,
                    statusCode = HttpStatusCode.InternalServerError // 500
                ),
                MockResponse(
                    path = rawgApi.apiPaths.GAMES_PATH,
                    jsonAsString = json,
                    statusCode = HttpStatusCode.OK // 200
                )
            )
            val uiStates = mutableListOf<UiState>()

            backgroundScope.launch(UnconfinedTestDispatcher()) {
                viewModel.uiState.toList(uiStates)
            }

            //Act
            advanceUntilIdle() //Implicitly calls viewModel.loadGames()
            viewModel.loadGames()
            advanceUntilIdle()

            //Assert
            uiStates.containsExactlyInstancesOf(
                UiState.InitialState::class.java,
                UiState.Loading::class.java,
                UiState.Error::class.java,
                UiState.Loading::class.java,
                UiState.Success::class.java
            )
        }
}
