@file:OptIn(ExperimentalTestApi::class, InternalRoborazziApi::class)

package com.shhatrat.loggerek.settings

import android.app.Application
import android.content.ComponentName
import android.content.ContentProvider
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import androidx.test.core.app.ApplicationProvider
import com.github.takahirom.roborazzi.DefaultFileNameGenerator
import com.github.takahirom.roborazzi.Dump
import com.github.takahirom.roborazzi.InternalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.shhatrat.loggerek.base.Type
import com.shhatrat.loggerek.base.addPackagePrefix
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.util.Logger
import kotlin.test.Test

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class ProfileScreenshotAndroidTest {

    private val screenshotTestSource = ProfileScreenshotTestSource()


    @Test
    fun authScreenWithError() {
        screenshotTestSource.provideProfileScreensWithError().forEach { item ->
            runIntroScreenTest(item.content, item.description)
        }
    }

    @Test
    fun authScreenWithUser() {
        screenshotTestSource.provideProfileScreensWithUser().forEach { item ->
            runIntroScreenTest(item.content, item.description)
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
                DefaultFileNameGenerator.generateFilePath().addPackagePrefix(
                    Type.ANDROID,
                    this@ProfileScreenshotAndroidTest::class.java.packageName
                ), RoborazziOptions(captureType = RoborazziOptions.CaptureType.Dump())
            )
        }
    }

    private fun setupAndroidContextProvider() {
        val type = findAndroidContextProvider() ?: return
        Robolectric.setupContentProvider(type)
    }

    private fun findAndroidContextProvider(): Class<ContentProvider>? {
        val providerClassName = "org.jetbrains.compose.resources.AndroidContextProvider"
        return try {
            @Suppress("UNCHECKED_CAST")
            Class.forName(providerClassName) as Class<ContentProvider>
        } catch (_: ClassNotFoundException) {
            Logger.debug("Class not found: $providerClassName")
            null
        }
    }

    @get:Rule(order = 1)
    val addActivityToRobolectricRule = object : TestWatcher() {
        override fun starting(description: Description?) {
            super.starting(description)
            val appContext: Application = ApplicationProvider.getApplicationContext()
            Shadows.shadowOf(appContext.packageManager).addActivityIfNotPresent(
                ComponentName(
                    appContext.packageName,
                    ComponentActivity::class.java.name,
                )
            )
        }
    }

    @get:Rule(order = 3)
    val setupContextProvider = object : TestWatcher() {
        override fun starting(description: Description?) {
            setupAndroidContextProvider()
        }
    }
}