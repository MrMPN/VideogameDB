package com.mrmpn.videogamedb.ui.screens.trendingList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrmpn.videogamedb.data.GameRepository
import com.mrmpn.videogamedb.ui.models.Game
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(private val gameRepository: GameRepository) : ViewModel() {

    sealed interface UiState {
        object InitialState : UiState
        object Loading : UiState
        data class Success(val games: List<Game>) : UiState
        data class Error(val errorMessage: String? = null) : UiState
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.InitialState)
    val uiState = _uiState.asStateFlow()

    init {
        loadGames()
    }

    fun loadGames() {
        viewModelScope.launch {
            _uiState.update { UiState.Loading }
            val games = gameRepository.getTrendingGames()
                .map { gameDataModel -> Game.fromDataModel(gameDataModel) }
            _uiState.update { UiState.Success(games.toImmutableList()) }
        }
    }
}
