package com.mrmpn.videogamedb.ui.screens.trendingList

import androidx.lifecycle.ViewModel
import com.mrmpn.videogamedb.ui.models.Game
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TrendingListViewModel : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String? = null,
        val games: ImmutableList<Game> = persistentListOf()
    )

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(isLoading = true) }
    }
}
