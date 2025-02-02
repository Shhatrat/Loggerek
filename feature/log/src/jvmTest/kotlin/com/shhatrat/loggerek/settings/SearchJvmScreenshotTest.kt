package com.shhatrat.loggerek.settings

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import com.github.takahirom.roborazzi.DefaultFileNameGenerator
import com.github.takahirom.roborazzi.RoborazziOptions
import com.shhatrat.loggerek.base.Type
import com.shhatrat.loggerek.base.addPackagePrefix
import com.shhatrat.loggerek.base.testing.TestItem
import com.shhatrat.loggerek.log.SearchScreenshotTestSource
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test


@OptIn(ExperimentalTestApi::class)
class SearchJvmScreenshotTest {

    private val screenshotTestSource = SearchScreenshotTestSource()

    @Test
    fun searchWithItems() {
        screenshotTestSource.searchWithItems().forEach {
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
                    Type.JVM,
                    this@SearchJvmScreenshotTest::class.java.packageName
                ), roborazziOptions = RoborazziOptions()
            )
        }
}