@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3WindowSizeClassApi::class
)

package com.shhatrat.loggerek.base.testing

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

object TestingHelper {

    fun getWindowSizeCompact() = WindowSizeClass.calculateFromSize(DpSize(0.dp, 0.dp))

    fun getWindowSizeExpanded() = WindowSizeClass.calculateFromSize(DpSize(1000.dp, 1000.dp))
}