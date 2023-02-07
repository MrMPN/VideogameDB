package com.mrmpn.videogamedb.ui.models

import java.time.LocalDate

data class Game(
    val id: Int,
    val title: String,
    val releaseDate: LocalDate,
    val image: String? = null
)
