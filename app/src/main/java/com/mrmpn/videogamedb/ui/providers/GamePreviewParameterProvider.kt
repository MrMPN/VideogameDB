package com.mrmpn.videogamedb.ui.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.mrmpn.videogamedb.data.json
import com.mrmpn.videogamedb.domain.Game
import kotlinx.serialization.decodeFromString

/**
 * This might be a bit too much, but I wanted to use real json data for the previews.
 */
class GamePreviewParameterProvider : PreviewParameterProvider<Game> {

    override val values = load()

    fun load(): Sequence<Game> {
        return json.decodeFromString<List<Game>>(
            readFileAsTextUsingInputStream(
                getFileFullPath("/previewJsons/games.json")
            )
        ).asSequence()
    }

}