package com.mrmpn.videogamedb

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mrmpn.videogamedb.ui.providers.GamePreviewParameterProvider
import com.mrmpn.videogamedb.ui.screens.trendingList.GameListScreen
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import com.mrmpn.videogamedb.utils.waitUntilNodeCount
import kotlinx.collections.immutable.toImmutableList
import org.junit.Rule
import org.junit.Test

class ComposableTests {

    @get:Rule
    val activityRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun gameListScreenComposableDisplaysList() {
        val tag = activityRule.activity.getString(R.string.item_game_title)
        val games = GamePreviewParameterProvider().values.toImmutableList()

        activityRule.setContent {
            VideogameDBTheme {
                GameListScreen(games)
            }
        }
        activityRule
            .waitUntilNodeCount(hasTestTag(tag), games.size)
    }
}
