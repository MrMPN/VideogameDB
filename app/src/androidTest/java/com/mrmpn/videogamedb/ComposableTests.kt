package com.mrmpn.videogamedb

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mrmpn.sharedtestcode.loadFileText
import com.mrmpn.videogamedb.data.json
import com.mrmpn.videogamedb.data.models.RawgAPIResponse
import com.mrmpn.videogamedb.domain.Game
import com.mrmpn.videogamedb.ui.components.GameCard
import com.mrmpn.videogamedb.ui.components.GameCardList
import com.mrmpn.videogamedb.ui.dateFormat
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.decodeFromString
import org.junit.Rule
import org.junit.Test

class ComposableTests {

    @get:Rule
    val activityRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var jsonToLoad: String

    @Test
    fun gameCardListLoadsCorrectly() {
        jsonToLoad = loadFileText(this, "/mockResponses/GetGames200.json")

        val tag = activityRule.activity.getString(R.string.tag_item_game_title)

        val games = json.decodeFromString<RawgAPIResponse<Game>>(jsonToLoad).results

        activityRule.setContent {
            VideogameDBTheme {
                GameCardList(games.toImmutableList())
            }
        }
        activityRule.onAllNodesWithTag(tag).assertCountEquals(games.size)
    }

    @Test
    fun gameCardListOnlyAllowsOneExpandedCard() {
        jsonToLoad = loadFileText(this, "/mockResponses/GetGames200.json")

        val viewMore = activityRule.activity.getString(R.string.view_more)
        val viewLess = activityRule.activity.getString(R.string.view_less)

        val games = json.decodeFromString<RawgAPIResponse<Game>>(jsonToLoad).results

        activityRule.setContent {
            VideogameDBTheme {
                GameCardList(games.toImmutableList())
            }
        }
        activityRule.onAllNodesWithText(viewMore).assertCountEquals(games.size)
        activityRule.onAllNodesWithText(viewMore).onFirst().performClick()
        activityRule.onNodeWithText(viewLess).assertIsDisplayed()
        activityRule.onAllNodesWithText(viewLess).assertCountEquals(1)
        activityRule.onAllNodesWithText(viewMore).assertCountEquals(games.size - 1)
        activityRule.onNodeWithText(viewLess).performClick()
        activityRule.onAllNodesWithText(viewMore).assertCountEquals(games.size)
    }

    @Test
    fun gameCardComposableDisplaysAllInfo() {
        jsonToLoad = loadFileText(this, "/mockResponses/game.json")
        val viewMore = activityRule.activity.getString(R.string.view_more)
        val viewLess = activityRule.activity.getString(R.string.view_less)
        val parentPlatform = activityRule.activity.getString(R.string.tag_parent_platform)

        val game: Game = json.decodeFromString(jsonToLoad)

        activityRule.setContent {
            VideogameDBTheme {
                GameCard(game = game)
            }
        }
        activityRule.onNodeWithText(game.name).assertIsDisplayed()
        activityRule.onNodeWithText(viewMore).assertIsDisplayed()
        activityRule.onNodeWithText(viewMore).performClick()

        val platformNodes = activityRule
            .onAllNodesWithTag(parentPlatform)
            .assertCountEquals(game.parentPlatforms.size)

        game.parentPlatforms.forEach {
            platformNodes.filterToOne(hasContentDescription(it.name)).assertIsDisplayed()
        }

        activityRule.onNodeWithText(viewLess).assertIsDisplayed()
        activityRule.onNodeWithText(game.releaseDate.format(dateFormat))
            .assertIsDisplayed()

        game.genres.forEach {
            activityRule.onNodeWithText(it.name, substring = true).assertIsDisplayed()
        }
    }
}
