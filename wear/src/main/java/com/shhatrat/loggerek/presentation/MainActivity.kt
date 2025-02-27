package com.shhatrat.loggerek.presentation

import android.net.Uri
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
import kotlinx.serialization.json.Json


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

                val parsedData = Json.decodeFromString<WatchData>(Uri.decode(it))
                navController.navigate(NavigationScreen.CACHE_LIST.createRoute(parsedData))
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
            composable(NavigationScreen.CACHE_LIST.key) { args ->
                CacheListScreen(
                    NavigationScreen.CACHE_LIST.encodeWatchData(args),
                    { cacheId, logs ->
                        navController.navigate(
                            NavigationScreen.LOG_LIST.createRoute(
                                cacheId,
                                logs
                            )
                        )
                    })
            }
            composable(NavigationScreen.LOG_LIST.key) {
                val data = NavigationScreen.LOG_LIST.encodeWatchData(it)
                LogListScreen(data.first, data.second) {
                    navController.navigate(NavigationScreen.INTRO.key) {
                        popUpTo(0)
                    }
                }
            }
        }
    }
}
