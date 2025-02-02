package com.shhatrat.loggerek.base.testing

import androidx.compose.runtime.Composable
import com.shhatrat.loggerek.base.LoggerekTheme

data class TestItem(
    val content: @Composable () -> Unit,
    val description: String,
    val width: Int,
    val height: Int,
)

fun getTestItems(
    content: @Composable (DeviceScreen) -> Unit,
    description: String,
): List<TestItem>{
    return DeviceScreen.entries.map { deviceScreen ->
        TestItem(
            content = {
                LoggerekTheme {
                    content(deviceScreen)
                }
            },
            description = "${deviceScreen.name}-$description",
            width = deviceScreen.width,
            height = deviceScreen.height,
        )
    }

}