package com.mrmpn.videogamedb

import com.google.common.truth.Truth.assertThat
import com.mrmpn.videogamedb.ui.screens.trendingList.TrendingListViewModel
import org.junit.Rule
import org.junit.Test

class TrendingListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: TrendingListViewModel

    @Test
    fun `on trendinglist viewmodel init validate that isLoading is true`() {
        viewModel = TrendingListViewModel()
        assertThat(viewModel.uiState.value.isLoading)
            .isEqualTo(true)
    }

    @Test
    fun `on trendinglist viewmodel init validate that games is an empty list`() {
        viewModel = TrendingListViewModel()
        assertThat(viewModel.uiState.value.games)
            .isEmpty()
    }

    @Test
    fun `on trendinglist viewmodel init get gamelist returns success with games list data`() {
        TODO()
        // Assemble
        // Act
        // Assert
    }

    @Test
    fun `on trendinglist viewmodel init get gamelist returns error with socket connection exception`() {
        TODO()
        // Assemble
        // Act
        // Assert
    }

    @Test
    fun `on trendinglist viewmodel init get gamelist returns error with http exception`() {
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
