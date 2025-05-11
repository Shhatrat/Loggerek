package com.shhatrat.loggerek.base.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color

@Composable
fun Disable(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { }
            .alpha(0.4f)
            .background(Color.Gray)
    ) {
        content()
    }
}