package com.mrmpn.videogamedb.trendingList

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class TrendingListTest {

    @get:Rule
    val trendingListRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun displayTrendingList() {
        launchTrendingList(trendingListRule) {
            startTrendingListScreen()
        } verify {
            trendingListContentIsDisplayed()
        }
    }
}
