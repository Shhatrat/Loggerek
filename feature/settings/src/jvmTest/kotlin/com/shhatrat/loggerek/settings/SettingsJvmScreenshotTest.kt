package com.shhatrat.loggerek.settings

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import com.github.takahirom.roborazzi.DefaultFileNameGenerator
import com.github.takahirom.roborazzi.RoborazziOptions
import com.shhatrat.loggerek.base.Type
import com.shhatrat.loggerek.base.addPackagePrefix
import com.shhatrat.loggerek.base.testing.TestItem
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test


@OptIn(ExperimentalTestApi::class)
class SettingsJvmScreenshotTest {

    private val screenshotTestSource = SettingsScreenshotTestSource()

    @Test
    fun settingsScreen() {
        screenshotTestSource.settingsScreen().forEach {
            runIntroScreenTest(it)
        }

    }

    @Test
    fun real() {
        screenshotTestSource.realOnStart().forEach {
            runIntroScreenTest(it)
        }

    }

    @OptIn(ExperimentalTestApi::class)
    private fun runIntroScreenTest(item: TestItem) =
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
                    this@SettingsJvmScreenshotTest::class.java.packageName
                ), roborazziOptions = RoborazziOptions()
            )
        }
}