package com.shhatrat.loggerek.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import com.shhatrat.loggerek.base.Type
import com.shhatrat.loggerek.base.addPackagePrefix
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test


@OptIn(ExperimentalTestApi::class)
class SettingsIosScreenshotTest {

    private val screenshotTestSource = SettingsScreenshotTestSource()

    @Test
    fun settingsScreen() {
        screenshotTestSource.settingsScreen().forEach {
            runIntroScreenTest(it.content, it.description)
        }
    }

    @Test
    fun real() {
        screenshotTestSource.realOnStart().forEach {
            runIntroScreenTest(it.content, it.description)
        }
    }

    @OptIn(ExperimentalTestApi::class)
    private fun runIntroScreenTest(
        composable: @Composable () -> Unit,
        description: String
    ) {
        runComposeUiTest {
            setContent {
                composable()
            }
            onRoot().captureRoboImage(
                this,
                filePath = "${description.addPackagePrefix(Type.IOS, description)}.png"
            )
        }
    }
}