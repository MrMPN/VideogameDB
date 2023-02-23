package com.mrmpn.videogamedb.utils

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule

typealias ComposeRule<T> = AndroidComposeTestRule<ActivityScenarioRule<T>, T>