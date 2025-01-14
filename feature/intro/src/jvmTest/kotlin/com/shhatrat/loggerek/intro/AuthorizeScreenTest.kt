package com.shhatrat.loggerek.intro

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import com.github.takahirom.roborazzi.RoborazziOptions
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.testing.DeviceScreen
import com.shhatrat.loggerek.intro.authorizate.AuthUiState
import com.shhatrat.loggerek.intro.authorizate.AuthorizeScreen
import io.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class AuthorizeScreenTest {

    @Test
    fun authScreenWithError() {
        DeviceScreen.entries.forEach { deviceScreen ->
            runIntroScreenTest(deviceScreen, AuthUiState(
                error = Error("Error"))
            )
        }
    }

    @Test
    fun authScreenDefault() {
        DeviceScreen.entries.forEach { deviceScreen ->
            runIntroScreenTest(deviceScreen, AuthUiState())
        }
    }

    @Test
    fun authScreenWithLink() {
        DeviceScreen.entries.forEach { deviceScreen ->
            runIntroScreenTest(deviceScreen, AuthUiState(
                pastePinAction = {},
                browserLink = "https://broser.link"
            ))
        }
    }


    private fun runIntroScreenTest(deviceScreen: DeviceScreen, authUiState: AuthUiState) = runDesktopComposeUiTest(
        width = deviceScreen.width,
        height = deviceScreen.height
    ) {
        setContent {
            LoggerekTheme {
                AuthorizeScreen(
                    calculateWindowSizeClass = { deviceScreen.getWindowSizeClass() },
                    authUiState = authUiState)
            }
        }
        onRoot().captureRoboImage(roborazziOptions = RoborazziOptions())
    }

}