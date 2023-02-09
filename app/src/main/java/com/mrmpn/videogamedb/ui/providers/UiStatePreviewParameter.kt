package com.mrmpn.videogamedb.ui.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.mrmpn.videogamedb.ui.screens.trendingList.TrendingListViewModel

class UiStatePreviewParameter : PreviewParameterProvider<TrendingListViewModel.UiState> {
    override val values = sequenceOf(
        TrendingListViewModel.UiState.InitialState,
        TrendingListViewModel.UiState.Loading,
        TrendingListViewModel.UiState.Error("There has been an error"),
        TrendingListViewModel.UiState.Success(GamePreviewParameterProvider().values.toList())
    )
}
