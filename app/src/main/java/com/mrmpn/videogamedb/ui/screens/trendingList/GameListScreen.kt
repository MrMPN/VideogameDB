package com.mrmpn.videogamedb.ui.screens.trendingList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mrmpn.videogamedb.R
import com.mrmpn.videogamedb.ui.models.Game
import com.mrmpn.videogamedb.ui.providers.GamePreviewParameterProvider
import com.mrmpn.videogamedb.ui.theme.Grey
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.format.DateTimeFormatter

@Composable
fun GameListScreen(viewModel: TrendingListViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    when (val s = state) {
        TrendingListViewModel.UiState.InitialState -> Unit
        is TrendingListViewModel.UiState.Loading -> Unit
        is TrendingListViewModel.UiState.Error -> Unit
        is TrendingListViewModel.UiState.Success -> GameListScreen(games = s.games.toImmutableList())
    }
}

@Composable
fun GameListScreen(games: ImmutableList<Game>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = games,
            key = { game ->
                game.id
            }
        ) { game ->
            GameCard(game = game)
        }
    }
}

@Composable
fun GameCard(game: Game, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Grey, contentColor = Color.White)
    ) {
        AsyncImage(
            model = game.image,
            placeholder = ColorPainter(Color.LightGray),
            error = ColorPainter(Color.LightGray),
            fallback = ColorPainter(Color.LightGray),
            modifier = Modifier.aspectRatio(16f / 9f),
            contentScale = ContentScale.Crop,
            contentDescription = "Game image"
        )
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = game.title,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .testTag(stringResource(id = R.string.item_game_title))
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                text = game.releaseDate.format(DateTimeFormatter.ISO_DATE)
            )
        }
    }
}

@Composable
@Preview
private fun GameListScreenPreview() {
    VideogameDBTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            GameListScreen(
                games = GamePreviewParameterProvider().values.toImmutableList()
            )
        }
    }
}
