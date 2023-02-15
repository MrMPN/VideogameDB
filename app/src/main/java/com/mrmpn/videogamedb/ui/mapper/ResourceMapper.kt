package com.mrmpn.videogamedb.ui.mapper

import com.mrmpn.videogamedb.R
import com.mrmpn.videogamedb.domain.Platform

//@DrawableRes
val Platform.logo: Int?
    get() = when (this.id) {
        1 -> R.drawable.logo_windows
        2 -> R.drawable.logo_playstation
        3 -> R.drawable.logo_xbox
        5 -> R.drawable.logo_apple
        6 -> R.drawable.logo_linux
        7 -> R.drawable.logo_nintendo
        else -> null
    }