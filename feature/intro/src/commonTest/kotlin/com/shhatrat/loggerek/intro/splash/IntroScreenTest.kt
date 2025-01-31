@file:OptIn(ExperimentalTestApi::class, ExperimentalMaterial3WindowSizeClassApi::class)

package com.shhatrat.loggerek.intro.splash

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.base.composable.loaderTestTag
import kotlin.test.Test

class IntroScreenTest {

    @Test
    fun checkIntroLoaderNotExist() = runComposeUiTest {
        val compactUiState = IntroUiState(
            loader = Loader(false),
            buttonAction = {}
        )
        val expandedUiState = IntroUiState(
            loader = Loader(false),
            buttonAction = {}
        )

        val windowSizeClass = mutableStateOf(WindowWidthSizeClass.Compact)

        setContent {
            IntroScreen(
                calculateWindowSizeClass = {
                    WindowSizeClass.calculateFromSize(
                        DpSize(
                            200.dp,
                            300.dp
                        )
                    )
                },
                introUiState = if (windowSizeClass.value == WindowWidthSizeClass.Compact) compactUiState else expandedUiState
            )
        }
        onNodeWithTag(loaderTestTag).assertDoesNotExist()
    }

    @Test
    fun checkIntroLoader() = runComposeUiTest {
        val compactUiState = IntroUiState(
            loader = Loader(true),
            buttonAction = { /* No-op */ }
        )
        val expandedUiState = IntroUiState(
            loader = Loader(true),
            buttonAction = { /* No-op */ }
        )

        val windowSizeClass = mutableStateOf(WindowWidthSizeClass.Compact)

        setContent {
            IntroScreen(
                calculateWindowSizeClass = {
                    WindowSizeClass.calculateFromSize(
                        DpSize(
                            200.dp,
                            300.dp
                        )
                    )
                },
                introUiState = if (windowSizeClass.value == WindowWidthSizeClass.Compact) compactUiState else expandedUiState
            )
        }
        onNodeWithTag(loaderTestTag).assertIsDisplayed()
    }
}