@file:OptIn(ExperimentalLayoutApi::class)

package com.shhatrat.loggerek.watch

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.SimpleButton
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.composable.Header
import com.shhatrat.loggerek.base.testing.TestingHelper.getWindowSizeExpanded
import com.shhatrat.loggerek.manager.watch.GarminWatch

@Composable
fun WatchScreen(calculateWindowSizeClass: WindowSizeCallback, watchUiState: WatchUiState) {

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(10.dp))
            .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LocalizationPermission()
            Header(modifier = Modifier, "Garmin")
            if(watchUiState.savedDevice!=null){
                Row {
                    Text("Saved device:")
                    SimpleButton("remove selected device", watchUiState.removeSavedDeviceButtonAction)
                }
                SingleGarminDevice(watchUiState.savedDevice)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                watchUiState.updateDevicesButton?.let { button ->
                    Button(onClick = { button.invoke() }) {
                        Text("Update devices")
                    }
                }
                watchUiState.selectButton?.let { button ->
                    Button(onClick = { button.invoke() }) {
                        Text("select")
                    }
                }
                watchUiState.removeSavedDeviceButtonAction?.let { button ->
                    Button(onClick = { button.invoke() }) {
                        Text("delete")
                    }
                }
            }

            watchUiState.devices.forEach { item ->
                Row {
                    if(item.selectable)
                    Checkbox(checked = item.selected, onCheckedChange = { item.onSelect() })
                    SingleGarminDevice(item)
                }
            }
        }
    }
}

@Composable
fun SingleGarminDevice(garminItem: GarminItem){
    Box(Modifier
        .fillMaxWidth()
        .requiredHeightIn(min = 60.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(MaterialTheme.colors.primary)) {
        FlowRow(modifier = Modifier.align(Center).padding(10.dp)) {
            Text(modifier = Modifier.padding(end = 20.dp), color = MaterialTheme.colors.background, text = "${garminItem.device.name} ${garminItem.device.connectedState}")
            Spacer(modifier = Modifier.weight(1f))
            Text(color = MaterialTheme.colors.background, text = "Application ${garminItem.installedAppState.name}")
        }

    }
}

@Preview
@Composable
fun WatchScreenPreview(){
    LoggerekTheme {
        val watchUiState = WatchUiState(
            devices = listOf(
                GarminItem(GarminWatch.GarminDevice("Fenix 3", 123, GarminWatch.GarminWatchState.CONNECTED),
                    installedAppState = GarminWatch.InstalledAppState.INSTALLED,
                    selected = false,
                    selectable = true,
                    onSelect = {})),
            removeSavedDeviceButtonAction = {},
            savedDevice =  GarminItem(GarminWatch.GarminDevice("Fenix 6 pro", 123, GarminWatch.GarminWatchState.CONNECTED),
                installedAppState = GarminWatch.InstalledAppState.INSTALLED,
                selected = false,
                selectable = true,
                onSelect = {})
        )
        WatchScreen({ getWindowSizeExpanded() }, watchUiState)
    }
}