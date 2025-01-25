package com.shhatrat.loggerek.base.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.LoggerekTheme
import loggerek.base.generated.resources.Res
import loggerek.base.generated.resources.falseSwitch
import loggerek.base.generated.resources.trueSwitch
import org.jetbrains.compose.resources.painterResource

@Composable
fun Switch(modifier: Modifier = Modifier, checked: Boolean = false){
        Box(
            modifier = modifier
                .requiredWidth(64.dp).requiredHeight(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.background)
        ) {
            Box(
                modifier = Modifier
                    .align(if(checked) Alignment.CenterEnd else Alignment.CenterStart)
                    .size(40.dp)
                    .clip(RoundedCornerShape(100))
                    .background(Color.Black)
                    .border(2.dp, MaterialTheme.colors.background, RoundedCornerShape(100))
            ){
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.background),
                    painter = painterResource(if(checked) Res.drawable.trueSwitch else Res.drawable.falseSwitch))
            }
        }
}

@Preview
@Composable
fun SwitchPreview(){
    LoggerekTheme {
        Box(Modifier.background(Color.Black).padding(100.dp).scale(1f)) {
            Column {
                Switch(checked = true)
                Switch(checked = false)
            }
        }
    }
}