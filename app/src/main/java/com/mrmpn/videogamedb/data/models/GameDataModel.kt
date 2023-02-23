package com.mrmpn.videogamedb.data.models

import com.mrmpn.videogamedb.domain.Game
import com.mrmpn.videogamedb.domain.Genre
import com.mrmpn.videogamedb.domain.Platform
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class GameDataModel(
    override val id: Int,
    override val name: String,
    @SerialName("background_image")
    override val backgroundImage: String?,
    override val genres: List<GenreDataModel>,
//    @SerialName("metacritic")
//    val metacritic: Int,
    @SerialName("parent_platforms")
    val rootParentPlatforms: List<ParentPlatformDataModel>,
//    @SerialName("rating")
//    val rating: Double,
    @Contextual
    @SerialName("released")
    override val releaseDate: LocalDate,
//    @SerialName("tags")
//    val tags: List<TagDataModel>,
) : Game {
    override val parentPlatforms: List<Platform> =
        rootParentPlatforms.map { it.platform }
}

@Serializable
data class GenreDataModel(
    override val id: Int,
    @SerialName("image_background")
    override val imageBackground: String,
    override val name: String,
) : Genre

@Serializable
data class ParentPlatformDataModel(
    val platform: PlatformDataModel
)

@Serializable
data class TagDataModel(
    @SerialName("games_count")
    val gamesCount: Int,
    val id: Int,
    @SerialName("image_background")
    val imageBackground: String,
    val language: String,
    val name: String,
    val slug: String
)

@Serializable
data class PlatformDataModel(
    override val id: Int,
    override val name: String,
    val slug: String
) : Platform
