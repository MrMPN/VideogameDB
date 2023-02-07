package com.mrmpn.videogamedb.trendingList

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import com.mrmpn.videogamedb.ActivityComposeTestRule
import com.mrmpn.videogamedb.R
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import com.mrmpn.videogamedb.ui.providers.GamePreviewParameterProvider
import com.mrmpn.videogamedb.ui.screens.trendingList.TrendingListScreen
import kotlinx.collections.immutable.toImmutableList

fun launchTrendingList(
    rule: ActivityComposeTestRule,
    block: TrendingListRobot.() -> Unit
): TrendingListRobot {
    return TrendingListRobot(rule).apply(block)
}

class TrendingListRobot(
    private val rule: ActivityComposeTestRule
) {

    val games = GamePreviewParameterProvider().values.toImmutableList()

    fun startTrendingListScreen() {
        rule.setContent {
            VideogameDBTheme {
                TrendingListScreen(games)
            }
        }
    }

    infix fun verify(block: TrendingListVerifier.() -> Unit) {
        TrendingListVerifier(rule).apply(block)
    }

    inner class TrendingListVerifier(
        private val rule: ActivityComposeTestRule
    ) {
        fun trendingListContentIsDisplayed() {
            rule.onAllNodesWithTag(
                rule.activity.getString(R.string.item_game_title)
            ).onFirst().assertTextEquals(games[0].title)
        }
    }
}
