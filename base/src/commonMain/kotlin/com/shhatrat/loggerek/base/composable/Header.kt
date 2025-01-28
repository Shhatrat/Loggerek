package com.shhatrat.loggerek.base.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.shhatrat.loggerek.base.LoggerekTheme

const val headerTestTag = "com.shhatrat.loggerek.base.composable.headerTestTag"


private val headerTextStyle = TextStyle(
    fontSize = 28.sp,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    shadow = Shadow(
        color = Color(0xFF000000).copy(alpha = 0.3f),
        offset = Offset(2f, 2f),
        blurRadius = 4f
    )
)


@Composable
fun Header(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier.testTag(headerTestTag),
        text = text,
        style = headerTextStyle,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun HeaderPreview() {
    Column {
        LoggerekTheme {
            Header(text = "Hello header")
        }
    }
}