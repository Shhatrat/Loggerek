package com.shhatrat.loggerek.base

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleButton(text: String, onClick: ButtonAction?, modifier: Modifier = Modifier) {
    if(onClick!=null)
    Button(onClick = { onClick() }){
        Text(color = MaterialTheme.colors.background, text = text)
    }
}

@Composable
@Preview
private fun SimpleButtonPreview(){
    LoggerekTheme {
        SimpleButton("test", {})
    }
}