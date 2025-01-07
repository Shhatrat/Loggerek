package com.shhatrat.loggerek.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun MainScreen(mainUiState: MainUiState) {
    Column {
        Text(mainUiState.nickName)
        Button(onClick = { mainUiState.removeData() }, content = { Text("remove data") })
    }
}
