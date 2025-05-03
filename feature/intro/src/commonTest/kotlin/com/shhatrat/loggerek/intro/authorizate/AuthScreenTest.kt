@file:OptIn(ExperimentalTestApi::class, ExperimentalMaterial3WindowSizeClassApi::class)

package com.shhatrat.loggerek.intro.authorizate

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.Loader
import kotlin.test.Test

class AuthScreenTest {

    @Test
    fun checkIntroLoaderNotExist() = runComposeUiTest {
        val compactUiState = AuthUiState(loader = Loader(false))

        setContent {
            AuthorizeScreen(
                calculateWindowSizeClass = {
                    WindowSizeClass.calculateFromSize(DpSize(200.dp, 300.dp))
                },
                authUiState = compactUiState
            )
        }
        onNodeWithTag("loader").assertDoesNotExist()
    }

}