package com.shhatrat.loggerek.profile

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import com.github.takahirom.roborazzi.DefaultFileNameGenerator
import com.github.takahirom.roborazzi.RoborazziOptions
import com.shhatrat.loggerek.base.Type
import com.shhatrat.loggerek.base.addPackagePrefix
import io.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class ProfileScreenshotJvmTest {

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
    private fun runIntroScreenTest(item: ProfileScreenshotTestSource.TestItem) =
        runDesktopComposeUiTest(
            width = item.width,
            height = item.height
        ) {
            setContent {
                item.content()
            }
            onRoot().captureRoboImage(
                DefaultFileNameGenerator.generateFilePath().addPackagePrefix(
                    Type.ANDROID,
                    this@ProfileScreenshotJvmTest::class.java.packageName
                ), roborazziOptions = RoborazziOptions()
            )
        }
}