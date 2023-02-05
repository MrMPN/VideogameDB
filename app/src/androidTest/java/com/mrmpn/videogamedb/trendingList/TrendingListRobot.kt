package com.mrmpn.videogamedb.trendingList

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import com.mrmpn.videogamedb.ActivityComposeTestRule
import com.mrmpn.videogamedb.R
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import com.mrmpn.videogamedb.ui.trendingList.Game
import com.mrmpn.videogamedb.ui.trendingList.TrendingListScreen
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

fun launchTrendingList(
    rule: ActivityComposeTestRule,
    block: TrendingListRobot.() -> Unit
): TrendingListRobot {
    return TrendingListRobot(rule).apply(block)
}

class TrendingListRobot(
    private val rule: ActivityComposeTestRule
) {

    val games = persistentListOf(
        Game(0, "God of War", LocalDate.of(2022, 11, 5)),
        Game(1, "Elden Ring", LocalDate.of(2022, 2, 24))
    )

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
            rule.onAllNodesWithContentDescription(
                rule.activity.getString(R.string.item_game_title)
            ).onFirst().assertContentDescriptionEquals(games[0].title)
        }
    }
}
