package com.mrmpn.videogamedb.domain

import java.time.LocalDate

interface Game {
    val id: Int
    val name: String
    val releaseDate: LocalDate
    val backgroundImage: String?
    val genres: List<Genre>
    val parentPlatforms: List<Platform>
}

interface Genre {
    val id: Int
    val imageBackground: String
    val name: String
}

interface Platform {
    val id: Int
    val name: String
}
