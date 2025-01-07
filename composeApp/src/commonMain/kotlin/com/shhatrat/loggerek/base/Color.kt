package com.shhatrat.loggerek.base

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object Color {
    val CustomYellow = Color(red = 239, green = 207, blue = 73)
    val CustomDarkYellow = Color(red = 180, green = 156, blue = 55)
    val CustomBackground = Color(0xFFF7F7F7)
    val CustomSurface = Color(0xFFFFFFFF)
    val CustomOnPrimary = Color(0xFF000000)
    val CustomOnSurface = Color(0xFF333333)

    val LightColorScheme = lightColors(
        primary = CustomYellow,
        onPrimary = CustomOnPrimary,
        secondary = CustomDarkYellow,
        onSecondary = CustomOnPrimary,
        background = CustomBackground,
        onBackground = CustomOnSurface,
        surface = CustomSurface,
        onSurface = CustomOnSurface,
        error = Color(0xFFB00020),
        onError = Color(0xFFFFFFFF)
    )
}

