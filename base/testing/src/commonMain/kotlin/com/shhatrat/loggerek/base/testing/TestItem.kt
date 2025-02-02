package com.shhatrat.loggerek.base.testing

import androidx.compose.runtime.Composable

data class TestItem(
    val content: @Composable () -> Unit,
    val description: String,
    val width: Int,
    val height: Int,
)