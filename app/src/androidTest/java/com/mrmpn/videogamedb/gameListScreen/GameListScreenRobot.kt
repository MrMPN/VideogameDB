package com.mrmpn.videogamedb.gameListScreen

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import com.mrmpn.videogamedb.ActivityComposeTestRule
import com.mrmpn.videogamedb.R
import com.mrmpn.videogamedb.ui.providers.GamePreviewParameterProvider
import com.mrmpn.videogamedb.ui.screens.trendingList.GameListScreen
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import kotlinx.collections.immutable.toImmutableList

internal fun onGameList(
    rule: ActivityComposeTestRule,
    block: GameListScreenRobot.() -> Unit
): GameListScreenRobot {
    return GameListScreenRobot(rule).apply(block)
}

class GameListScreenRobot(
    private val rule: ActivityComposeTestRule
) {

    val games = GamePreviewParameterProvider().values.toImmutableList()

    fun startGameListComposable() {
        rule.setContent {
            VideogameDBTheme {
                GameListScreen(games)
            }
        }
    }

    fun startGameListComposableWithViewModel() {
        rule.setContent {
            VideogameDBTheme {
                GameListScreen()
            }
        }
    }

    infix fun verify(block: TrendingListVerifier.() -> Unit) {
        TrendingListVerifier(rule).apply(block)
    }

    inner class TrendingListVerifier(
        private val rule: ActivityComposeTestRule
    ) {
        fun trendingGameListContentIsDisplayed() {
            rule.onAllNodesWithTag(
                rule.activity.getString(R.string.item_game_title)
            ).onFirst().assertTextEquals(games[0].title)
        }
    }
}
