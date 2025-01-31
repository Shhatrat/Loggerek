package com.shhatrat.loggerek.intro.authorizate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.composable.CircularIndeterminateProgressBar
import com.shhatrat.loggerek.base.composable.SnackBarHelper
import com.shhatrat.loggerek.base.composable.SnackBarHelper.ProvideSnackBar
import com.shhatrat.loggerek.base.get
import loggerek.feature.intro.generated.resources.AuthDescription
import loggerek.feature.intro.generated.resources.AuthStep2Description
import loggerek.feature.intro.generated.resources.Res
import loggerek.feature.intro.generated.resources.introStartButton

@Composable
fun AuthorizeScreen(calculateWindowSizeClass: WindowSizeCallback, authUiState: AuthUiState) {

    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(authUiState.error) {
        SnackBarHelper.handle(snackBarHostState, authUiState.error)
    }

    Crossfade(targetState = (calculateWindowSizeClass.invoke().widthSizeClass)) { screenClass ->
        when (screenClass) {
            WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium ->
                CompactScreenLayout(modifier = Modifier, authUiState)

            WindowWidthSizeClass.Expanded -> ExpandedScreenLayout(Modifier, authUiState)
        }
    }
    ProvideSnackBar(snackBarHostState)
}

@Composable
private fun Logic(modifier: Modifier, authUiState: AuthUiState) {
    Column(
        modifier = modifier.background(MaterialTheme.colors.primary).padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            color = MaterialTheme.colors.background,
            textAlign = TextAlign.Center,
            text = Res.string.AuthDescription.get()
        )
        AnimatedVisibility(authUiState.loader.active) {
            CircularIndeterminateProgressBar(color = MaterialTheme.colors.background)
        }
        authUiState.browserLink?.let { link ->
            Text(
                color = MaterialTheme.colors.background,
                textAlign = TextAlign.Center,
                text = Res.string.AuthStep2Description.get()
            )
            SelectionContainer {
                ClickableText(
                    text = buildAnnotatedString { append(link) },
                    onClick = { }
                )
            }
        }
        authUiState.pastePinAction?.let {
            authUiState.browserLink?.let { link ->
                SelectionContainer {
                    val customTextSelectionColors = TextSelectionColors(
                        handleColor = MaterialTheme.colors.background,
                        backgroundColor = MaterialTheme.colors.background.copy(alpha = 0.4f)
                    )
                    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                        ClickableText(
                            style = TextStyle(color = MaterialTheme.colors.background),
                            text = buildAnnotatedString { append(link) },
                            onClick = { }
                        )
                    }
                }
            }

            var text by remember { mutableStateOf("") }
            TextField(
                modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.background,
                    textColor = MaterialTheme.colors.primary
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                value = text,
                onValueChange = { text = it }
            )
            Button(colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
            ), onClick = { it.invoke(text) }, content = { Text("set PIN") })
        }
        AnimatedVisibility(authUiState.startButton != null) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.primary,
                ),
                onClick = { authUiState.startButton?.invoke() },
                content = { Text(Res.string.introStartButton.get()) })

        }
    }
}

@Composable
private fun CompactScreenLayout(modifier: Modifier = Modifier, authUiState: AuthUiState) {
    Box(modifier = modifier.fillMaxSize().background(MaterialTheme.colors.primary)) {
        Logic(modifier.align(Alignment.Center), authUiState)
    }
}

@Composable
private fun ExpandedScreenLayout(modifier: Modifier = Modifier, authUiState: AuthUiState) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
        Logic(
            modifier.size(500.dp).align(Alignment.Center).clip(RoundedCornerShape(20.dp)),
            authUiState
        )
    }
}
