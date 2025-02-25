/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

@file:OptIn(ExperimentalHorologistApi::class)

package com.shhatrat.loggerek.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.AppScaffold
import com.shhatrat.loggerek.presentation.screen.CacheListScreen
import com.shhatrat.loggerek.presentation.screen.IntroScreen
import com.shhatrat.loggerek.presentation.screen.LogListScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WearApp()
        }
    }


    @Composable
    fun WearApp() {
        val navController = rememberSwipeDismissableNavController()
        AppScaffold {
            SwipeDismissableNavHost(navController = navController, startDestination = NavigationScreen.INTRO.key) {
                composable(NavigationScreen.INTRO.key) {
                    IntroScreen()
                }
                composable(NavigationScreen.CACHE_LIST.key) {
                    CacheListScreen()
                }
                composable(NavigationScreen.LOG_LIST.key) {
                    LogListScreen()
                }
            }
        }
    }
}