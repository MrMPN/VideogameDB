package com.mrmpn.videogamedb

import com.google.common.truth.Truth.assertThat
import com.mrmpn.videogamedb.data.GameRepository
import com.mrmpn.videogamedb.data.RAWGGameDataSource
import com.mrmpn.videogamedb.ui.providers.GamePreviewParameterProvider
import com.mrmpn.videogamedb.ui.screens.trendingList.GameListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val gameDataSource = RAWGGameDataSource()
    private val gameRepository = GameRepository(gameDataSource)

    private lateinit var viewModel: GameListViewModel

    @Before
    fun setUp() {
        viewModel = GameListViewModel(gameRepository)
    }

    @Test
    fun `on trendinglist viewmodel init validate that isLoading is true`() {
        assertThat(viewModel.uiState.value)
            .isEqualTo(GameListViewModel.UiState.Loading)
    }

    @Test
    fun `on trendinglist viewmodel init get gamelist returns success with games list data`() = runTest {
        advanceUntilIdle()
        assertThat(viewModel.uiState.value)
            .isInstanceOf(GameListViewModel.UiState.Success::class.java)
        assertThat((viewModel.uiState.value as GameListViewModel.UiState.Success).games)
            .hasSize(GamePreviewParameterProvider().values.toList().size)
    }

    @Test
    fun `on trendinglist viewmodel init get gamelist returns error`() {
        TODO()
        // Assemble
        // Act
        // Assert
    }

    @Test
    fun `on receiving error retry get gamelist returns success with games list data`() {
        TODO()
        // Assemble
        // Act
        // Assert
    }
}
