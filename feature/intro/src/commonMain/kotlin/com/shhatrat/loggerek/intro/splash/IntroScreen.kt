package com.shhatrat.loggerek.intro.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IntroScreen(introUiState: IntroUiState) {
    Column {
        if (introUiState.loader.active)
            CircularIndeterminateProgressBar()
        if (introUiState.buttonAction != null) {
            Button(onClick = {
                introUiState.buttonAction.invoke()
            }, content = { Text("StartProcess...") })
        }
    }
}


@Composable
fun CircularIndeterminateProgressBar() {
    CircularProgressIndicator(
        modifier = Modifier.size(48.dp),
        strokeWidth = 4.dp
    )
}