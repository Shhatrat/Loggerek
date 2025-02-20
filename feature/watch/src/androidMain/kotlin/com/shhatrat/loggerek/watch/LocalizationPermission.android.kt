@file:OptIn(ExperimentalPermissionsApi::class)

package com.shhatrat.loggerek.watch

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.composable.Header

@Composable
actual fun LocalizationPermission() {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )
    Column {
        Header(modifier = Modifier, "Permissions")
        if (permissionsState.allPermissionsGranted) {
            Text("All permissions granted")
        } else {
            Button({
                permissionsState.launchMultiplePermissionRequest()
            }) {
                Text("Need permission")
            }
        }
    }
}

@Composable
@Preview
private fun LocalizationPermissionPreview(){
    LoggerekTheme {
        LocalizationPermission()
    }
}