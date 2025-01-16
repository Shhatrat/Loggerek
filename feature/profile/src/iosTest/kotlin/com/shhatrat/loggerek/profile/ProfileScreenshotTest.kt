package com.shhatrat.loggerek.profile

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import com.shhatrat.loggerek.api.model.FullUser
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.testing.DeviceScreen
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

class ProfileScreenshotTest {

    @Test
    fun authScreenWithError() {
        DeviceScreen.entries.forEach { deviceScreen ->
            runIntroScreenTest(
                deviceScreen, ProfileUiState(
                    error = Error("Error")
                ), "withError"
            )
        }
    }

    @Test
    fun authScreenWithUser() {
        DeviceScreen.entries.forEach { deviceScreen ->
            runIntroScreenTest(
                deviceScreen, ProfileUiState(
                    user = FullUser.mock()
                ), "user"
            )
        }
    }

    @OptIn(ExperimentalTestApi::class)
    private fun runIntroScreenTest(deviceScreen: DeviceScreen,
                                   profileUiState: ProfileUiState,
                                   description: String) {
        runComposeUiTest {
            setContent {
                LoggerekTheme {
                    ProfileScreen(
                        calculateWindowSizeClass = { deviceScreen.getWindowSizeClass() },
                        profileUiState = profileUiState
                    )
                }
            }
            onRoot().captureRoboImage(this, filePath = "${deviceScreen.name}-${description}.png")
        }
    }
}