package com.shhatrat.loggerek.log



import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import com.shhatrat.loggerek.base.Type
import com.shhatrat.loggerek.base.addPackagePrefix
import com.shhatrat.loggerek.search.SearchScreen
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test


@OptIn(ExperimentalTestApi::class)
class SearchIosScreenshotTest {

    private val screenshotTestSource = SearchScreenshotTestSource()

    @Test
    fun settingsScreen() {
        screenshotTestSource.searchWithItems().forEach {
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