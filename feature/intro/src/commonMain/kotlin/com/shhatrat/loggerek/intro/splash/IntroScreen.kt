package com.shhatrat.loggerek.intro.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.composable.CircularIndeterminateProgressBar
import com.shhatrat.loggerek.base.composable.Header
import com.shhatrat.loggerek.base.get
import loggerek.feature.intro.generated.resources.Res
import loggerek.feature.intro.generated.resources.appName
import loggerek.feature.intro.generated.resources.introDescription
import loggerek.feature.intro.generated.resources.introLogo
import loggerek.feature.intro.generated.resources.introStartButton
import org.jetbrains.compose.resources.painterResource

object IntroScreenTestTag{
    val button = "com.shhatrat.loggerek.intro.splash.IntroScreenTestTag.button"
}

@Composable
fun IntroScreen(calculateWindowSizeClass: WindowSizeCallback, introUiState: IntroUiState) {

    Crossfade(targetState = (calculateWindowSizeClass.invoke().widthSizeClass)) { screenClass ->
        when (screenClass) {
            WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> CompactScreenLayout(introUiState)
            WindowWidthSizeClass.Expanded -> ExpandedScreenLayout(introUiState)
        }
    }
}

@Composable
private fun CompactScreenLayout(introUiState: IntroUiState){
    Box(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Header(Modifier.fillMaxWidth().padding(16.dp), text = Res.string.appName.get())
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    modifier = Modifier.size(400.dp).align(Alignment.Center),
                    painter = painterResource(Res.drawable.introLogo),
                    contentDescription = ""
                )
            }
            if (introUiState.buttonAction != null) {
                Box(modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = Res.string.introDescription.get())
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier.testTag(IntroScreenTestTag.button).align(Alignment.Center).padding(bottom = 16.dp),
                        onClick = { introUiState.buttonAction.invoke() },
                        content = { Text(Res.string.introStartButton.get()) })
                }
            }
        }
        if (introUiState.loader.active)
            CircularIndeterminateProgressBar(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun ExpandedScreenLayout(introUiState: IntroUiState){
    Row {
        Box(modifier = Modifier.fillMaxSize().weight(1f).background(MaterialTheme.colors.background)){
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.size(500.dp).align(Alignment.Center),
                    painter = painterResource(Res.drawable.introLogo),
                    contentDescription = ""
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize().weight(1f).background(Color.White)){
            Column(modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Header(Modifier.padding(16.dp), text = Res.string.appName.get())
                AnimatedVisibility(introUiState.loader.active) {
                    CircularIndeterminateProgressBar()
                }
                Text(modifier = Modifier.padding(16.dp), text = Res.string.introDescription.get())
                if (introUiState.buttonAction != null) {
                    Button(
                        modifier = Modifier.testTag(IntroScreenTestTag.button),
                        onClick = {
                        introUiState.buttonAction.invoke()
                    }, content = { Text(Res.string.introStartButton.get()) })
            }
        }
        }
    }

}
