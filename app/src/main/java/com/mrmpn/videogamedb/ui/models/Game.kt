package com.mrmpn.videogamedb.ui.models

import com.mrmpn.videogamedb.data.models.GameDataModel
import java.time.LocalDate

data class Game(
    val id: Int,
    val title: String,
    val releaseDate: LocalDate,
    val image: String? = null
) {
    fun toDataModel() = GameDataModel(
        id = id,
        title = title,
        released = releaseDate,
        image = image
    )

    companion object {
        fun fromDataModel(gameDataModel: GameDataModel) = Game(
            id = gameDataModel.id,
            title = gameDataModel.title,
            releaseDate = gameDataModel.released,
            image = gameDataModel.image
        )
    }
}
