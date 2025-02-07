package com.shhatrat.loggerek.base

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable


typealias WindowSizeCallback = @Composable () -> WindowSizeClass

typealias MoveToIntro = () -> Unit

typealias MoveToWatch = () -> Unit

typealias OnBack = () -> Unit

typealias MoveToLogCache = @Composable (String) -> Unit

typealias ButtonAction = () -> Unit