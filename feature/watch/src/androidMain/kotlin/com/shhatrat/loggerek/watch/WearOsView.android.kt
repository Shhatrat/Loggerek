package com.shhatrat.loggerek.watch

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.shhatrat.loggerek.base.composable.Header
import com.shhatrat.wearshared.CommunicationManager.getConnectedWearNames
import com.shhatrat.wearshared.CommunicationManager.isConnected

@Composable
actual fun WearOsView() {
    Column {
        Header(modifier = Modifier, text = "Wear OS")

        val context = LocalContext.current
        var wearNames by remember { mutableStateOf<List<String>>(emptyList()) }
        var isConnected by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            isConnected = context.isConnected()
            if (isConnected) {
                wearNames = context.getConnectedWearNames()
            }
        }
        if (isConnected) {
            wearNames.forEach {
                Text(text = it)
            }
        }
    }
}