package com.mrmpn.videogamedb

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import com.mrmpn.sharedtestcode.loadFileText
import com.mrmpn.videogamedb.data.json
import com.mrmpn.videogamedb.data.models.RawgAPIResponse
import com.mrmpn.videogamedb.domain.Game
import com.mrmpn.videogamedb.ui.components.GameCardList
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.decodeFromString
import org.junit.Rule
import org.junit.Test

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
}
