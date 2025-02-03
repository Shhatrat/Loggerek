@file:OptIn(ExperimentalResourceApi::class)

package com.shhatrat.loggerek.base.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.shhatrat.loggerek.base.LoggerekTheme
import loggerek.base.generated.resources.Res
import loggerek.base.generated.resources.star
import loggerek.base.generated.resources.starFull
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun StarSwitch(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClicked: () -> Unit
) {
    Image(
        modifier = modifier.size(60.dp).clickable { onClicked() },
        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
        painter = if (selected) rememberAsyncImagePainter(Res.getUri("drawable/starFull.svg")) else rememberAsyncImagePainter(
            Res.getUri("drawable/star.svg")
        ),
        contentDescription = "Star Switch"
    )
}

@Preview
@Composable
fun StarSwitchPreview() {
    LoggerekTheme {
        Box(Modifier.background(MaterialTheme.colors.background).padding(100.dp).scale(1f)) {
            Column {
                StarSwitch(selected = true) {}
                StarSwitch(selected = false) {}
            }
        }
    }

}