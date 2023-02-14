package com.mrmpn.videogamedb

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mrmpn.sharedtestcode.loadFileText
import com.mrmpn.videogamedb.data.json
import com.mrmpn.videogamedb.data.models.GameDataModel
import com.mrmpn.videogamedb.data.models.RawgAPIResponse
import com.mrmpn.videogamedb.domain.Game
import com.mrmpn.videogamedb.ui.components.GameCard
import com.mrmpn.videogamedb.ui.components.GameCardList
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.decodeFromString
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ComposableTests {

    @get:Rule
    val activityRule = createAndroidComposeRule<ComponentActivity>()

    private val jsonToLoad = loadFileText(this, "/mockResponses/GetGames200.json")

    @Test
    fun gameCardListLoadsCorrectly() {
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
    fun gameCardComposableDisplaysAllInfo() {
        val viewMore = activityRule.activity.getString(R.string.view_more)
        val parentPlatform = activityRule.activity.getString(R.string.tag_parent_platform)
        val playstation = activityRule.activity.getString(R.string.playstation)
        val pc = activityRule.activity.getString(R.string.pc)
        val xbox = activityRule.activity.getString(R.string.xbox)

        val game = GameDataModel(
            id = 1,
            name = "Test Game",
            backgroundImage = "https://test.com",
            parentPlatforms = emptyList(),
            releaseDate = LocalDate.of(2020, 1, 1),
            genres = emptyList(),
        )

        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

        activityRule.setContent {
            VideogameDBTheme {
                GameCard(game = game)
            }
        }
        activityRule.onNodeWithText(game.name).assertIsDisplayed()
        activityRule.onAllNodesWithTag(parentPlatform)
            .assertAny(hasContentDescription(playstation))
            .assertAny(hasContentDescription(pc))
            .assertAny(hasContentDescription(xbox))
        activityRule.onNodeWithText(viewMore).assertIsDisplayed()

        activityRule.onNodeWithText(viewMore).performClick()

        activityRule.onNodeWithText(game.releaseDate.format(formatter))
            .assertIsDisplayed()

        game.genres.forEach {
            activityRule.onNode(hasText(it.name)).assertIsDisplayed()
        }
    }
}
