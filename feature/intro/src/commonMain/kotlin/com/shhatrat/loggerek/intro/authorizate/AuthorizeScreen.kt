package com.shhatrat.loggerek.intro.authorizate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.buildAnnotatedString
import com.shhatrat.loggerek.intro.splash.CircularIndeterminateProgressBar

@Composable
fun AuthorizeScreen(authUiState: AuthUiState) {
    Column {
        Text("Auth")
        if(authUiState.loader.active)
            CircularIndeterminateProgressBar()
        authUiState.browserLink?.let { link ->
            SelectionContainer {
                ClickableText(
                    text = buildAnnotatedString{ append(link) },
                    onClick = { }
                )
            }
        }


        authUiState.pastePinAction?.let {
            var text by remember { mutableStateOf("") }
            TextField(
                value = text,
                onValueChange = { text = it }
            )
            Button(onClick = {it.invoke(text)}, content = {Text("set PIN")})
        }
    }
}