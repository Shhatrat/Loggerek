package com.shhatrat.loggerek.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.android.horologist.compose.layout.AppScaffold
import com.shhatrat.loggerek.api.model.GeocacheMock
import com.shhatrat.loggerek.api.model.GeocacheType
import com.shhatrat.loggerek.manager.watch.model.WatchData
import com.shhatrat.loggerek.manager.watch.model.WatchLog
import com.shhatrat.loggerek.manager.watch.model.toWatchCache
import com.shhatrat.loggerek.presentation.screen.CacheListScreen
import com.shhatrat.loggerek.presentation.screen.IntroScreen
import com.shhatrat.loggerek.presentation.screen.LogListScreen
import com.shhatrat.wearshared.CommunicationManager


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    val navController = rememberSwipeDismissableNavController()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        CommunicationManager.observeDataFromPhone(context).collect {
            if (it != null) {
                navController.navigate(NavigationScreen.CACHE_LIST.key)
            }
        }
    }

    AppScaffold {
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = NavigationScreen.INTRO.key
        ) {
            composable(NavigationScreen.INTRO.key) {
                IntroScreen()
            }
            composable(NavigationScreen.CACHE_LIST.key) {

                CacheListScreen(
                    WatchData(
                        items = GeocacheType.entries
                            .map { GeocacheMock().getByType(it) }
                            .map { it.toWatchCache() },
                        logs = listOf()
                    ),
                    { cacheId , logs -> navController.navigate(NavigationScreen.LOG_LIST.key) })
            }
            composable(NavigationScreen.LOG_LIST.key) {
                LogListScreen("OP0001", listOf(
                    WatchLog("OK", "1", "Found it"),
                    WatchLog("Super skrzyneczka wszystko sie udalo", "2", "Found it"),
                    WatchLog("Po trudach sie udalo", "2", "Found it"),

                ), { navController.navigate(NavigationScreen.INTRO.key) })
            }
        }
    }
}
