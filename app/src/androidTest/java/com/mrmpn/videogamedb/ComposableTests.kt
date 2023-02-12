package com.mrmpn.videogamedb

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import com.mrmpn.videogamedb.ui.components.GameCardList
import com.mrmpn.videogamedb.ui.providers.GamePreviewParameterProvider
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import kotlinx.collections.immutable.toImmutableList
import org.junit.Rule
import org.junit.Test

class ComposableTests {

    @get:Rule
    val activityRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun gameCardListLoadsCorrectly() {
        val tag = activityRule.activity.getString(R.string.tag_item_game_title)
        val games = GamePreviewParameterProvider().values.toImmutableList()

        activityRule.setContent {
            VideogameDBTheme {
                GameCardList(games)
            }
        }
        activityRule.onAllNodesWithTag(tag).assertCountEquals(games.size)
    }
}
