package com.dale.jrnlmob.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dale.jrnlmob.ui.compose.ComposeScreen
import com.dale.jrnlmob.ui.compose.ComposeViewModel
import com.dale.jrnlmob.ui.detail.DetailScreen
import com.dale.jrnlmob.ui.detail.DetailViewModel
import com.dale.jrnlmob.ui.settings.SettingsScreen
import com.dale.jrnlmob.ui.settings.SettingsViewModel
import com.dale.jrnlmob.ui.timeline.TimelineScreen
import com.dale.jrnlmob.ui.timeline.TimelineViewModel

object Routes {
    const val TIMELINE = "timeline"
    const val COMPOSE = "compose?entryId={entryId}"
    const val DETAIL = "detail/{entryId}"
    const val SETTINGS = "settings"

    fun composeRoute(entryId: Long? = null): String =
        if (entryId != null) "compose?entryId=$entryId" else "compose"

    fun detailRoute(entryId: Long): String = "detail/$entryId"
}

@Composable
fun NavGraph(
    navController: NavHostController,
    timelineViewModel: TimelineViewModel,
    composeViewModel: ComposeViewModel,
    detailViewModel: DetailViewModel,
    settingsViewModel: SettingsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.TIMELINE
    ) {
        composable(Routes.TIMELINE) {
            val state by timelineViewModel.state.collectAsStateWithLifecycle()

            TimelineScreen(
                state = state,
                onSearchQueryChanged = timelineViewModel::onSearchQueryChanged,
                onEntryClick = { navController.navigate(Routes.detailRoute(it)) },
                onComposeClick = {
                    composeViewModel.reset()
                    navController.navigate(Routes.composeRoute())
                },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) },
                onSyncClick = { timelineViewModel.syncFromWebdav() },
                onScrolledToTop = { timelineViewModel.onScrolledToTop() }
            )
        }

        composable(
            route = Routes.COMPOSE,
            arguments = listOf(navArgument("entryId") { type = NavType.LongType; defaultValue = -1L })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getLong("entryId") ?: -1L
            val state by composeViewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(entryId) {
                if (entryId > 0) composeViewModel.loadEntry(entryId)
            }
            LaunchedEffect(state.isSaved) {
                if (state.isSaved) { composeViewModel.reset(); navController.popBackStack() }
            }

            ComposeScreen(
                state = state,
                onBodyChanged = composeViewModel::onBodyChanged,
                onMoodSelected = composeViewModel::onMoodSelected,
                onFetchLocation = { composeViewModel.fetchLocation() },
                onSave = { composeViewModel.save() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("entryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getLong("entryId") ?: return@composable
            val state by detailViewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(entryId) { detailViewModel.loadEntry(entryId) }
            LaunchedEffect(state.isDeleted) { if (state.isDeleted) navController.popBackStack() }

            DetailScreen(
                state = state,
                onBack = { navController.popBackStack() },
                onEdit = { composeViewModel.reset(); navController.navigate(Routes.composeRoute(it)) },
                onDelete = { detailViewModel.deleteEntry() }
            )
        }

        composable(Routes.SETTINGS) {
            val state by settingsViewModel.state.collectAsStateWithLifecycle()

            SettingsScreen(
                state = state,
                onWebdavUrlChanged = settingsViewModel::updateWebdavUrl,
                onUsernameChanged = settingsViewModel::updateUsername,
                onPasswordChanged = settingsViewModel::updatePassword,
                onJournalPathChanged = settingsViewModel::updateJournalPath,
                onAutoSyncChanged = settingsViewModel::updateAutoSync,
                onTemperatureUnitChanged = settingsViewModel::updateTemperatureUnit,
                onSave = { settingsViewModel.save() },
                onTestConnection = { settingsViewModel.testConnection() },
                onBack = {
                    settingsViewModel.save()
                    navController.popBackStack()
                }
            )
        }
    }
}
