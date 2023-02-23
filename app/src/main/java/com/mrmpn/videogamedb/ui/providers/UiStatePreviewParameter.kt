package com.mrmpn.videogamedb.ui.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.mrmpn.videogamedb.ui.screens.trendingList.GameListViewModel

class UiStatePreviewParameter : PreviewParameterProvider<GameListViewModel.UiState> {
    override val values = sequenceOf(
        GameListViewModel.UiState.InitialState,
        GameListViewModel.UiState.Loading,
        GameListViewModel.UiState.Error("There has been an error"),
        GameListViewModel.UiState.Success(GamePreviewParameterProvider().values.toList())
    )
}
