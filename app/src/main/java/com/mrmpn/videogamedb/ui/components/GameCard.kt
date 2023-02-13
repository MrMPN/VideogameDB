package com.mrmpn.videogamedb.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mrmpn.videogamedb.R
import com.mrmpn.videogamedb.domain.Game
import com.mrmpn.videogamedb.ui.providers.GamePreviewParameterProvider
import com.mrmpn.videogamedb.ui.theme.Grey
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

const val GameCardImageRatio = 16f / 9f

@Composable
fun GameCard(game: Game, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
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
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = game.name,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .testTag(stringResource(id = R.string.tag_item_game_title))
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                text = game.releaseDate.format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                )
            )
        }
    }
}

@Composable
@Preview()
private fun GameCardPreview(@PreviewParameter(GamePreviewParameterProvider::class) game: Game) {
    VideogameDBTheme {
        GameCard(game = game)
    }
}
