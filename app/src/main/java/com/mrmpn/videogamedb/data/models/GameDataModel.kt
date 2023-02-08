package com.mrmpn.videogamedb.data.models

import java.time.LocalDate

data class GameDataModel(
    val id: Int,
    val title: String,
    val released: LocalDate,
    val image: String?,
)
