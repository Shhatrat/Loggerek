package com.shhatrat.loggerek.intro.splash

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IntroScreen(introUiState: IntroUiState) {
    Text("Hello")
    if(introUiState.loader.active)
        CircularIndeterminateProgressBar()
}


@Composable
fun CircularIndeterminateProgressBar() {
    CircularProgressIndicator(
        modifier = Modifier.size(48.dp),
        strokeWidth = 4.dp
    )
}