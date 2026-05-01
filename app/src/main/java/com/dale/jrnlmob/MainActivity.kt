package com.dale.jrnlmob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.dale.jrnlmob.ui.compose.ComposeViewModel
import com.dale.jrnlmob.ui.detail.DetailViewModel
import com.dale.jrnlmob.ui.navigation.NavGraph
import com.dale.jrnlmob.ui.settings.SettingsViewModel
import com.dale.jrnlmob.ui.theme.JrnlMobTheme
import com.dale.jrnlmob.ui.timeline.TimelineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JrnlMobTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val timelineViewModel: TimelineViewModel = hiltViewModel()
                    val composeViewModel: ComposeViewModel = hiltViewModel()
                    val detailViewModel: DetailViewModel = hiltViewModel()
                    val settingsViewModel: SettingsViewModel = hiltViewModel()

                    NavGraph(
                        navController = navController,
                        timelineViewModel = timelineViewModel,
                        composeViewModel = composeViewModel,
                        detailViewModel = detailViewModel,
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
    }
}
