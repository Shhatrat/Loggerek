package com.shhatrat.loggerek.base.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

const val loaderTestTag = "com.shhatrat.loggerek.base.composable.loaderTestTag"

@Composable
fun CircularIndeterminateProgressBar(modifier: Modifier = Modifier, color: Color? = null) {
    Box(
        modifier = modifier.size(48.dp).testTag(loaderTestTag),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colors.background,
            strokeWidth = 10.dp
        )

        CircularProgressIndicator(
            modifier = Modifier.size(42.dp),
            color = MaterialTheme.colors.primary,
            strokeWidth = 4.dp
        )
    }
}