package com.shhatrat.loggerek.base.typeface

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import loggerek.base.typeface.generated.resources.Montserrat_Black
import loggerek.base.typeface.generated.resources.Montserrat_BlackItalic
import loggerek.base.typeface.generated.resources.Montserrat_Bold
import loggerek.base.typeface.generated.resources.Montserrat_BoldItalic
import loggerek.base.typeface.generated.resources.Montserrat_ExtraBold
import loggerek.base.typeface.generated.resources.Montserrat_ExtraBoldItalic
import loggerek.base.typeface.generated.resources.Montserrat_ExtraLight
import loggerek.base.typeface.generated.resources.Montserrat_ExtraLightItalic
import loggerek.base.typeface.generated.resources.Montserrat_Italic
import loggerek.base.typeface.generated.resources.Montserrat_Light
import loggerek.base.typeface.generated.resources.Montserrat_LightItalic
import loggerek.base.typeface.generated.resources.Montserrat_Medium
import loggerek.base.typeface.generated.resources.Montserrat_MediumItalic
import loggerek.base.typeface.generated.resources.Montserrat_Regular
import loggerek.base.typeface.generated.resources.Montserrat_SemiBold
import loggerek.base.typeface.generated.resources.Montserrat_SemiBoldItalic
import loggerek.base.typeface.generated.resources.Montserrat_Thin
import loggerek.base.typeface.generated.resources.Montserrat_ThinItalic
import loggerek.base.typeface.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun getAppFont() = FontFamily(
    Font(resource = Res.font.Montserrat_Black, weight = FontWeight.Black),
    Font(
        resource = Res.font.Montserrat_BlackItalic,
        weight = FontWeight.Black,
        style = FontStyle.Italic
    ),
    Font(resource = Res.font.Montserrat_Bold, weight = FontWeight.Bold),
    Font(
        resource = Res.font.Montserrat_BoldItalic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    ),
    Font(resource = Res.font.Montserrat_ExtraBold, weight = FontWeight.ExtraBold),
    Font(
        resource = Res.font.Montserrat_ExtraBoldItalic,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Italic
    ),
    Font(resource = Res.font.Montserrat_ExtraLight, weight = FontWeight.ExtraLight),
    Font(
        resource = Res.font.Montserrat_ExtraLightItalic,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Italic
    ),
    Font(resource = Res.font.Montserrat_Light, weight = FontWeight.Light),
    Font(
        resource = Res.font.Montserrat_LightItalic,
        weight = FontWeight.Light,
        style = FontStyle.Italic
    ),
    Font(resource = Res.font.Montserrat_Medium, weight = FontWeight.Medium),
    Font(
        resource = Res.font.Montserrat_MediumItalic,
        weight = FontWeight.Medium,
        style = FontStyle.Italic
    ),
    Font(resource = Res.font.Montserrat_Regular, weight = FontWeight.Normal),
    Font(
        resource = Res.font.Montserrat_Italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(resource = Res.font.Montserrat_SemiBold, weight = FontWeight.SemiBold),
    Font(
        resource = Res.font.Montserrat_SemiBoldItalic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    ),
    Font(resource = Res.font.Montserrat_Thin, weight = FontWeight.Thin),
    Font(
        resource = Res.font.Montserrat_ThinItalic,
        weight = FontWeight.Thin,
        style = FontStyle.Italic
    )
)

@Composable
fun getTypography() = getAppFont().let {
    Typography(
        h1 = Typography().h1.copy(fontFamily = it),
        h2 = Typography().h2.copy(fontFamily = it),
        h3 = Typography().h3.copy(fontFamily = it),
        h4 = Typography().h4.copy(fontFamily = it),
        h5 = Typography().h5.copy(fontFamily = it),
        h6 = Typography().h6.copy(fontFamily = it),
        subtitle1 = Typography().subtitle1.copy(fontFamily = it),
        subtitle2 = Typography().subtitle2.copy(fontFamily = it),
        body1 = Typography().body1.copy(fontFamily = it),
        body2 = Typography().body2.copy(fontFamily = it),
        button = Typography().button.copy(fontFamily = it),
        caption = Typography().caption.copy(fontFamily = it),
        overline = Typography().overline.copy(fontFamily = it)
    )
}