package com.mrmpn.videogamedb.gameListScreen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class GameListScreenTest {

    @get:Rule
    val activityRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun gameListScreenStatelessDisplaysList() {
        onGameList(activityRule) {
            startGameListComposable()
        } verify {
            trendingGameListContentIsDisplayed()
        }
    }

    @Test
    fun gameListScreenStatefulDisplaysList() {
        onGameList(activityRule) {
            startGameListComposableWithViewModel()
        } verify {
            trendingGameListContentIsDisplayed()
        }
    }
}
