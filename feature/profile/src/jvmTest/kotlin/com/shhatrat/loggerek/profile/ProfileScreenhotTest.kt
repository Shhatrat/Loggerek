package com.shhatrat.loggerek.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.runDesktopComposeUiTest
import com.github.takahirom.roborazzi.RoborazziOptions
import com.shhatrat.loggerek.base.LoggerekTheme
import io.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class ProfileScreenhotTest {

    private val screenshotTestSource = ProfileScreenshotTestSource()

    @Test
    fun authScreenWithError() {
        screenshotTestSource.provideProfileScreensWithError().forEach {
            runIntroScreenTest(it)
        }

    }

    @Test
    fun authScreenWithUser() {
        screenshotTestSource.provideProfileScreensWithUser().forEach {
            runIntroScreenTest(it)
        }

    }

    @OptIn(ExperimentalTestApi::class)
    private fun runIntroScreenTest(item: ProfileScreenshotTestSource.TestItem)= runDesktopComposeUiTest(
        width = item.width,
        height = item.height
    ) {
        setContent {
            item.content()
        }
        onRoot().captureRoboImage(roborazziOptions = RoborazziOptions())
    }
}