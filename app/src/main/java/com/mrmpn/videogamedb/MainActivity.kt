package com.mrmpn.videogamedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mrmpn.videogamedb.ui.screens.trendingList.GameListScreen
import com.mrmpn.videogamedb.ui.theme.VideogameDBTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.persistentListOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideogameDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameListScreen(games = persistentListOf())
                }
            }
        }
    }
}
