package com.mrmpn.videogamedb.utils

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule

typealias ComposeRule<T> = AndroidComposeTestRule<ActivityScenarioRule<T>, T>

inline fun <reified T> loadFileText(
    caller: T,
    filePath: String
): String =
    T::class.java.getResource(filePath)?.readText() ?: throw IllegalArgumentException(
        "Could not find file $filePath. Make sure to put it in the correct resources folder for $caller's runtime."
    )