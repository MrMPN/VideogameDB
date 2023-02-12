package com.mrmpn.videogamedb.ui.models

import com.mrmpn.videogamedb.data.models.GameDataModel
import java.time.LocalDate

data class Game(
    val id: Int,
    val name: String,
    val releaseDate: String,
    val backgroundImage: String? = null
) {
    fun toDataModel() = GameDataModel(
        id = id,
        name = name,
        released = releaseDate,
        backgroundImage = backgroundImage
    )

    companion object {
        fun fromDataModel(gameDataModel: GameDataModel) = Game(
            id = gameDataModel.id,
            name = gameDataModel.name,
            releaseDate = gameDataModel.released,
            backgroundImage = gameDataModel.backgroundImage
        )
    }
}
