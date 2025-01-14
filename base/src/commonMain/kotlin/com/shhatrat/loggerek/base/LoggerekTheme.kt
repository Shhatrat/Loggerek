package com.shhatrat.loggerek.base

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.shhatrat.loggerek.base.color.LoggerekColor
import com.shhatrat.loggerek.base.typeface.getTypography

@Composable
fun LoggerekTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LoggerekColor.lightColorScheme,
        typography = getTypography()
    ) {
        content()
    }
}
