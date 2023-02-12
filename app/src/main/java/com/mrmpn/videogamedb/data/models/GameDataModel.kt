package com.mrmpn.videogamedb.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class GameDataModel(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("background_image")
    val backgroundImage: String?,
//    @SerialName("genres")
//    val genres: List<GenreDataModel>,
//    @SerialName("metacritic")
//    val metacritic: Int,
//    @SerialName("parent_platforms")
//    val parentPlatforms: List<ParentPlatformDataModel>,
//    @SerialName("rating")
//    val rating: Double,
    @SerialName("released")
    val released: String,
//    @SerialName("tags")
//    val tags: List<TagDataModel>,
)

@Serializable
data class GenreDataModel(
    @SerialName("id")
    val id: Int,
    @SerialName("image_background")
    val imageBackground: String,
    @SerialName("name")
    val name: String,
)

@Serializable
data class ParentPlatformDataModel(
    @SerialName("platform")
    val platform: PlatformDataModel
)

@Serializable
data class TagDataModel(
    @SerialName("games_count")
    val gamesCount: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("image_background")
    val imageBackground: String,
    @SerialName("language")
    val language: String,
    @SerialName("name")
    val name: String,
    @SerialName("slug")
    val slug: String
)

@Serializable
data class PlatformDataModel(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("slug")
    val slug: String
)
