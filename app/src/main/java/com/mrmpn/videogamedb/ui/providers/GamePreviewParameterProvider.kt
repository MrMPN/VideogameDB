package com.mrmpn.videogamedb.ui.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.mrmpn.videogamedb.ui.models.Game
import java.time.LocalDate

class GamePreviewParameterProvider : PreviewParameterProvider<Game> {
    override val values = sequenceOf(
        Game(
            0,
            "God of War",
            "LocalDate.of(2022, 11, 5)",
            "https://media.rawg.io/media/resize/1920/-/screenshots/55f/55fe715e129d5365b48b35b5fc8052be.jpg"
        ),
        Game(
            1,
            "Elden Ring",
            "LocalDate.of(2022, 2, 24)"
        ),
        Game(
            2,
            "Final Fantasy XVI",
            "LocalDate.of(2023, 7, 5)",
            "https://media.rawg.io/media/resize/1920/-/screenshots/bc2/bc255e09533797521b2ff1e364113a0b.jpg"
        ),
    )
}
