package com.shhatrat.loggerek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.LoggerekColor
import com.shhatrat.loggerek.base.getTypography
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(calculateWindowSizeClass = { calculateWindowSizeClass(this) },
                additionalKoinConfig = { this.androidContext(this@MainActivity) })
        }
    }
}


@Composable
@Preview
fun dd() {
    MaterialTheme(
        colors = LoggerekColor.lightColorScheme,
        typography = getTypography()
    ) {

        Box(modifier = Modifier.fillMaxSize().background(Color.Red)) {
        var text = "123"
        }
    }
}