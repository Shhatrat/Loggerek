package com.shhatrat.loggerek.intro.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.WindowSizeCallback
import loggerek.feature.intro.generated.resources.Res
import loggerek.feature.intro.generated.resources.logoo
import org.jetbrains.compose.resources.painterResource

@Composable
fun IntroScreen(calculateWindowSizeClass: WindowSizeCallback, introUiState: IntroUiState) {
    when(calculateWindowSizeClass.invoke().widthSizeClass){
        WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> CompactScreenLayout(introUiState)
        WindowWidthSizeClass.Expanded -> ExpandedScreenLayout(introUiState)
    }
}

@Composable
fun CompactScreenLayout(introUiState: IntroUiState){
    val primaryColor = MaterialTheme.colors.primary

    Box() {
        Column(modifier = Modifier.fillMaxSize()) {
            Text("Loggerek")
            Image(painter = painterResource(Res.drawable.logoo), contentDescription = "")
            if (introUiState.buttonAction != null) {
                Button(onClick = {
                    introUiState.buttonAction.invoke()
                }, content = { Text("StartProcess...") })
            }
        }
        if (introUiState.loader.active)
            CircularIndeterminateProgressBar(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ExpandedScreenLayout(introUiState: IntroUiState){
    Row {
        if (introUiState.loader.active)
            CircularIndeterminateProgressBar()
        if (introUiState.buttonAction != null) {
            Button(onClick = {
                introUiState.buttonAction.invoke()
            }, content = { Text("StartProcess...") })
        }
        Image(painter = painterResource(Res.drawable.logoo), contentDescription = "")
    }
}

@Composable
fun CircularIndeterminateProgressBar(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.size(48.dp),
        strokeWidth = 4.dp
    )
}