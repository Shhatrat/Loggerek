package com.shhatrat.loggerek.base

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object LoggerekColor {
    val CustomYellow = Color(red = 239, green = 207, blue = 73)
    val CustomDarkYellow = Color(red = 180, green = 156, blue = 55)
    val CustomBackground = Color(0xFFF7F7F7)
    val CustomSurface = Color(0xFFFFFFFF)
    val CustomOnPrimary = Color(0xFF000000)
    val CustomOnSurface = Color(0xFF333333)
    val YellowBackground = Color(0xFFEFCF49) // Żółte tło (239, 207, 73)
    val BlackButton = Color(0xFF000000) // Czarne przyciski
    val WhiteText = Color(0xFFFFFFFF)   // Biały tekst

    val lightColorScheme = lightColors(
//        primary = Color.Black,
//        onPrimary = CustomOnPrimary,
//        secondary = CustomDarkYellow,
//        onSecondary = CustomOnPrimary,
//        background = CustomYellow,
//        onBackground = CustomOnSurface,
//        surface = CustomSurface,
//        onSurface = CustomOnSurface,
//        error = Color(0xFFB00020),
        onError = Color(0xFFB71C1C),
        primary = BlackButton,       // Przyciski
        onPrimary = WhiteText,       // Tekst na przyciskach
        background = YellowBackground, // Główne tło aplikacji
        onBackground = Color.Black,  // Tekst na tle
        surface = YellowBackground,  // Powierzchnie mniejszych elementów (opcjonalnie to samo co background)
        onSurface = Color.Black      // Tekst na powierzchniach
    )
}

