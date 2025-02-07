package com.shhatrat.loggerek.watch

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.shhatrat.loggerek.base.WindowSizeCallback

@Composable
fun WatchScreen(calculateWindowSizeClass: WindowSizeCallback, watchUiState: WatchUiState) {

    Column {
        Button(onClick = {watchUiState.initButton()}){
            Text("init")
        }
        Button(onClick = {watchUiState.sendButton()}){
            Text("send")
        }
    }
}
