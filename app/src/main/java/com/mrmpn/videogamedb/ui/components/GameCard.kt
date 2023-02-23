package com.mrmpn.videogamedb.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mrmpn.videogamedb.R
import com.mrmpn.videogamedb.domain.Game
import com.mrmpn.videogamedb.ui.dateFormat
import com.mrmpn.videogamedb.ui.mapper.logo
import com.mrmpn.videogamedb.ui.providers.GameToBooleanProvider
import com.mrmpn.videogamedb.ui.theme.Grey
import com.mrmpn.videogamedb.ui.theme.GreyLight
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme

/**
 * A card that displays a game. Two version provided, stateless and stateful.
 *
 * @param modifier Modifier to be applied to the card.
 * @param game The game to display.
 * @param expanded Whether the card should be expanded or not.
 * @param onClickViewMore Callback to be invoked when the user clicks on the "View more" button.
 */

const val GameCardImageRatio = 16f / 9f

@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    game: Game,
) {
    var expanded by remember { mutableStateOf(false) }
    GameCard(
        modifier = modifier,
        game = game,
        expanded = expanded,
        onClickViewMore = { expanded = !expanded }
    )
}

@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    game: Game,
    expanded: Boolean = false,
    onClickViewMore: (Game) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = Grey, contentColor = Color.White)
    ) {
        AsyncImage(
            model = game.backgroundImage,
            placeholder = ColorPainter(Color.LightGray),
            error = ColorPainter(Color.LightGray),
            fallback = ColorPainter(Color.LightGray),
            modifier = Modifier.aspectRatio(GameCardImageRatio),
            contentScale = ContentScale.Crop,
            contentDescription = "Game image"
        )
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                game.parentPlatforms.forEach { platform ->
                    platform.logo?.let { logo ->
                        Image(
                            modifier = Modifier
                                .size(16.dp)
                                .testTag(stringResource(id = R.string.tag_parent_platform)),
                            painter = painterResource(id = logo),
                            contentDescription = platform.name
                        )
                    }
                }
            }
            Text(
                text = game.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .testTag(stringResource(id = R.string.tag_item_game_title))
            )
            if (expanded) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.item_game_release) + ":",
                    )
                    Text(
                        text = game.releaseDate.format(dateFormat).capitalize(Locale.current)
                    )
                }
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = GreyLight
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.genres) + ":",
                    )

                    Text(
                        text = buildAnnotatedString {
                            game.genres.forEachIndexed { index, genre ->
                                if (index != 0) {
                                    append(", ")
                                }
                                pushStyle(style = SpanStyle(textDecoration = TextDecoration.Underline))
                                append(genre.name)
                                pop()
                            }
                        },
                        maxLines = 1
                    )
                }
                Divider(
                    modifier = Modifier.padding(top = 12.dp),
                    color = GreyLight
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = { onClickViewMore(game) })
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = if (expanded) R.string.view_less else R.string.view_more),
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}

@Composable
@Preview
private fun GameCardPreview(
    @PreviewParameter(GameToBooleanProvider::class, 2) gameBooleanPair: Pair<Game, Boolean>
) {
    var expanded by remember { mutableStateOf(gameBooleanPair.second) }

    VideogameDBTheme {
        GameCard(
            game = gameBooleanPair.first,
            expanded = expanded,
            onClickViewMore = {
                expanded = !expanded
            }
        )
    }
}
