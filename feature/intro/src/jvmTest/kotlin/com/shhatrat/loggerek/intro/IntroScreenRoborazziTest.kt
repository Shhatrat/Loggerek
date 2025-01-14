@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.shhatrat.loggerek.intro

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.RoborazziOptions
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.color.LoggerekColor
import com.shhatrat.loggerek.intro.splash.IntroScreen
import com.shhatrat.loggerek.intro.splash.IntroUiState
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class IntroScreenRoborazziTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun introScreen() = runDesktopComposeUiTest {
        setContent {
            LoggerekTheme {
                IntroScreen(
                    calculateWindowSizeClass = { WindowSizeClass.calculateFromSize(DpSize(200.dp, 300.dp)) },
                    introUiState = IntroUiState(
                        loader = Loader(false),
                        buttonAction = { }
                    )
                )
            }
        }
        onRoot().captureRoboImage(roborazziOptions = RoborazziOptions())
    }
}
