package com.shhatrat.loggerek.watch

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.testing.TestingHelper.getWindowSizeExpanded

@Composable
fun WatchScreen(calculateWindowSizeClass: WindowSizeCallback, watchUiState: WatchUiState) {

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(10.dp))
            .padding(10.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            Text("Garmin")

            Row() {
                Button(onClick = { watchUiState.initButton() }) {
                    Text("init")
                }
                Button(onClick = { watchUiState.sendButton() }) {
                    Text("send")
                }
            }
            watchUiState.devices.forEach {
                Text("${it.name} --> ${it.connectedState}")
            }
        }
    }
}

@Preview
@Composable
fun dd(){
    LoggerekTheme {
        val watchUiState = WatchUiState()
        WatchScreen({ getWindowSizeExpanded() }, watchUiState)
    }
}