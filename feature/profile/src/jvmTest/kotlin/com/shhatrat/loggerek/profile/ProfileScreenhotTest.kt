package com.shhatrat.loggerek.profile

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import com.github.takahirom.roborazzi.RoborazziOptions
import com.shhatrat.loggerek.api.model.FullUser
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.testing.DeviceScreen
import io.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class ProfileScreenhotTest {

    @Test
    fun authScreenWithError() {
        DeviceScreen.entries.forEach { deviceScreen ->
            runIntroScreenTest(deviceScreen, ProfileUiState(
                error = Error("Error"))
            )
        }
    }

    @Test
    fun authScreen() {
        DeviceScreen.entries.forEach { deviceScreen ->
            runIntroScreenTest(deviceScreen, ProfileUiState(
                user = FullUser.mock()))
        }
    }

    private fun runIntroScreenTest(deviceScreen: DeviceScreen, profileUiState: ProfileUiState) = runDesktopComposeUiTest(
        width = deviceScreen.width,
        height = deviceScreen.height
    ) {
        setContent {
            LoggerekTheme {
                ProfileScreen(
                    calculateWindowSizeClass = { deviceScreen.getWindowSizeClass() },
                    profileUiState = profileUiState)
            }
        }
        onRoot().captureRoboImage(roborazziOptions = RoborazziOptions())
    }
}