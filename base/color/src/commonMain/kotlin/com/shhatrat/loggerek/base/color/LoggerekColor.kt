package com.shhatrat.loggerek.base.color

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object LoggerekColor {
    val CustomYellow = Color(red = 239, green = 207, blue = 73)
    val CustomDarkYellow = Color(red = 180, green = 156, blue = 55)
    val CustomBackground = Color(0xFFF7F7F7)
    val CustomSurface = Color(0xFFFFFFFF)
    val CustomOnPrimary = Color(0xFF000000)
    val CustomOnSurface = Color(0xFF333333)
    val YellowBackground = Color(0xFFEFCF49)
    val BlackButton = Color(0xFF000000)
    val WhiteText = Color(0xFFFFFFFF)

    val lightColorScheme = lightColors(
        onError = Color(0xFFB71C1C),
        primary = BlackButton,
        onPrimary = WhiteText,
        background = YellowBackground,
        onBackground = Color.Black,
        surface = YellowBackground,
        onSurface = Color.Black
    )
}

