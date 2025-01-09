package com.shhatrat.loggerek.base.composable

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

val loaderTestTag = "com.shhatrat.loggerek.base.composable.loaderTestTag"

@Composable
fun CircularIndeterminateProgressBar(modifier: Modifier = Modifier, color: Color? = null) {
    CircularProgressIndicator(
        modifier = modifier.size(48.dp).testTag(loaderTestTag),
        color = color?: MaterialTheme.colors.primary,
        strokeWidth = 4.dp
    )
}