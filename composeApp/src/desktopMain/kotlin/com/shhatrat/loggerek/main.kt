package com.shhatrat.loggerek

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.shhatrat.loggerek.base.get
import loggerek.composeapp.generated.resources.Res
import loggerek.composeapp.generated.resources.app_name


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = Res.string.app_name.get(),
        state = rememberWindowState(width = 800.dp, height = 800.dp)
    ) {
        App(calculateWindowSizeClass = { calculateWindowSizeClass() })
    }
}
