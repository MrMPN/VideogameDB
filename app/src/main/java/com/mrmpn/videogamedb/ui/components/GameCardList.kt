package com.mrmpn.videogamedb.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrmpn.videogamedb.domain.Game
import com.mrmpn.videogamedb.ui.providers.GamePreviewParameterProvider
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * A list of [GameCard]s.
 *
 * @param games The games to display.
 * @param modifier Modifier to be applied to the list.
 */
@Composable
fun GameCardList(games: ImmutableList<Game>, modifier: Modifier = Modifier) {
    var expandedGame: Game? by remember { mutableStateOf(null) }

    fun onGameClick(game: Game) {
        expandedGame = if (expandedGame == game) null else game
    }

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
            GameCard(game = game, expanded = game == expandedGame, onClickViewMore = { onGameClick(game) })
        }
    }
}

@Composable
@Preview
private fun GameCardListPreview() {
    GameCardList(games = GamePreviewParameterProvider().values.toImmutableList())
}
