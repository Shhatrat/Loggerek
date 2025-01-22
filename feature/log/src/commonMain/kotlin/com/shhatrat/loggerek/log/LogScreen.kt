package com.shhatrat.loggerek.log

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.composable.CircularIndeterminateProgressBar
import com.shhatrat.loggerek.base.composable.Header
import com.shhatrat.loggerek.base.composable.SnackBarHelper
import com.shhatrat.loggerek.base.composable.SnackBarHelper.ProvideSnackBar
import kotlin.math.log

@Composable
fun LogScreen(calculateWindowSizeClass: WindowSizeCallback, logUiState: LogUiState) {

    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(logUiState.error) {
        SnackBarHelper.handle(snackBarHostState, logUiState.error)
    }

    Crossfade(targetState = (calculateWindowSizeClass.invoke().widthSizeClass)) { screenClass ->
        when (screenClass) {
            WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium ->
                CompactScreenLayout(modifier = Modifier.fillMaxSize(), logUiState)

            WindowWidthSizeClass.Expanded -> ExpandedScreenLayout(
                Modifier.fillMaxSize(),
                logUiState
            )
        }
    }
    ProvideSnackBar(snackBarHostState)
}

@Composable
fun ExpandedScreenLayout(modifier: Modifier, logUiState: LogUiState) {
    CompactScreenLayout(modifier, logUiState)
}

@Composable
fun CompactScreenLayout(modifier: Modifier, settingsUiState: LogUiState) {
    Box(modifier = modifier.background(MaterialTheme.colors.background).padding(16.dp)) {
        AnimatedVisibility(settingsUiState.loader.active) {
            CircularIndeterminateProgressBar(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colors.primary
            )
        }
        Column(
            Modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(Modifier, "LOG :) ")
        }
    }
}
