package com.shhatrat.loggerek.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalRoborazziApi::class)
class ProfileScreenshotTest {

    private val screenshotTestSource = ProfileScreenshotTestSource()

    @Test
    fun authScreenWithError() {
        screenshotTestSource.provideProfileScreensWithError().forEach { item ->
            runIntroScreenTest( item.content, item.description)
        }

    }

    @Test
    fun authScreenWithUser() {
        screenshotTestSource.provideProfileScreensWithUser().forEach { item ->
            runIntroScreenTest( item.content, item.description)
        }

    }

    @OptIn(ExperimentalTestApi::class)
    private fun runIntroScreenTest(composable: @Composable () -> Unit,
                                   description: String) {
        runComposeUiTest {
            setContent {
                composable()
            }
            onRoot().captureRoboImage(this, filePath = "ios-${description}.png")
        }
    }
}