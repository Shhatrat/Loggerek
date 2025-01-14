package com.shhatrat.loggerek.intro

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import com.github.takahirom.roborazzi.RoborazziOptions
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.testing.DeviceScreen
import com.shhatrat.loggerek.intro.splash.IntroScreen
import com.shhatrat.loggerek.intro.splash.IntroUiState
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class IntroScreenTest {

    @Test
    fun introScreensForAllDevicesWithLoader() {
        DeviceScreen.entries.forEach { deviceScreen ->
            runIntroScreenTest(deviceScreen, true)
        }
    }

    @Test
    fun introScreensForAllDevicesWithoutLoader() {
        DeviceScreen.entries.forEach { deviceScreen ->
            runIntroScreenTest(deviceScreen, false)
        }
    }

    private fun runIntroScreenTest(deviceScreen: DeviceScreen, loader: Boolean) = runDesktopComposeUiTest(
        width = deviceScreen.width,
        height = deviceScreen.height
    ) {
        setContent {
            LoggerekTheme {
                IntroScreen(
                    calculateWindowSizeClass = { deviceScreen.getWindowSizeClass() },
                    introUiState = IntroUiState(
                        loader = Loader(loader),
                        buttonAction = { }
                    )
                )
            }
        }
        onRoot().captureRoboImage(roborazziOptions = RoborazziOptions())
    }
}