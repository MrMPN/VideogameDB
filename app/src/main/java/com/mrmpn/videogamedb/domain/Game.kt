package com.mrmpn.videogamedb.domain

import java.time.LocalDate

interface Game {
    val id: Int
    val name: String
    val releaseDate: LocalDate
    val backgroundImage: String?
}
