package com.mrmpn.videogamedb.ui.trendingList

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import java.time.LocalDate

class GamePreviewParameterProvider : PreviewParameterProvider<Game> {
    override val values = sequenceOf(
        Game(0, "God of War", LocalDate.of(2022, 11, 5)),
        Game(1, "Elden Ring", LocalDate.of(2022, 2, 24)),
        Game(2, "Final Fantasy XVI", LocalDate.of(2023, 7, 5)),
    )
}
