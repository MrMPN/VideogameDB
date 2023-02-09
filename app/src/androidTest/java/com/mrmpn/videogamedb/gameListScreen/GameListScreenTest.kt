package com.mrmpn.videogamedb.gameListScreen

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mrmpn.videogamedb.R
import com.mrmpn.videogamedb.di.HiltComponentActivity
import com.mrmpn.videogamedb.ui.providers.GamePreviewParameterProvider
import com.mrmpn.videogamedb.ui.screens.trendingList.GameListScreen
import com.mrmpn.videogamedb.ui.screens.trendingList.TrendingListViewModel
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import com.mrmpn.videogamedb.utils.waitUntilNodeCount
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.collections.immutable.toImmutableList
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class GameListScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = createAndroidComposeRule<HiltComponentActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun gameListScreenDisplaysList() {
        val tag = activityRule.activity.getString(R.string.item_game_title)
        val games = GamePreviewParameterProvider().values.toImmutableList()

        activityRule.setContent {
            VideogameDBTheme {
                GameListScreen(TrendingListViewModel.UiState.Success(games))
            }
        }
        activityRule
            .waitUntilNodeCount(hasTestTag(tag), games.size)
    }
}
