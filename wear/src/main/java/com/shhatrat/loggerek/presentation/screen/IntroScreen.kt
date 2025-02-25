package com.shhatrat.loggerek.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import com.shhatrat.loggerek.presentation.WearPreviewDevices

@Composable
fun IntroScreen() {
    Box(modifier = Modifier.fillMaxSize()){
        var loader by remember { mutableStateOf(false) }
        Column(modifier = Modifier.align(Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                loader = true
            }){
                Text(text = "Intro")
            }
            AnimatedVisibility(loader) {
                CircularProgressIndicator()
            }
        }
    }
}

@WearPreviewDevices
@Composable
fun dd(){
    IntroScreen()
}
