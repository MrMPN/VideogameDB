package com.mrmpn.videogamedb.ui.screens.trendingList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrmpn.videogamedb.ui.components.GameCardList
import com.mrmpn.videogamedb.ui.providers.UiStatePreviewParameter
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun GameListScreen(viewModel: TrendingListViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    GameListScreen(state = state)
}

@Composable
fun GameListScreen(state: TrendingListViewModel.UiState) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        when (state) {
            is TrendingListViewModel.UiState.InitialState -> GameCardList(persistentListOf())
            is TrendingListViewModel.UiState.Loading -> {
                Box {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            is TrendingListViewModel.UiState.Error -> {
                Box {
                    state.errorMessage?.let { Text(text = it, modifier = Modifier.align(Alignment.Center)) }
                }
            }
            is TrendingListViewModel.UiState.Success -> GameCardList(games = state.games.toImmutableList())
        }
    }
}

@Composable
@Preview
private fun GameListScreenPreview(
    @PreviewParameter(UiStatePreviewParameter::class) state: TrendingListViewModel.UiState
) {
    VideogameDBTheme {
        GameListScreen(state)
    }
}
