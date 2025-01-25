package com.shhatrat.loggerek.base.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.LoggerekTheme
import loggerek.base.generated.resources.Res
import org.jetbrains.compose.resources.painterResource

@Composable
fun MultiTextField(modifier: Modifier = Modifier,
                   text: String = "",
                   onChange: (String) -> Unit = {},
                   placeholder: @Composable () -> Unit = {}){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            value = text,
            onValueChange = { onChange(it) },
            modifier = Modifier.fillMaxSize(),
            placeholder = { placeholder() },
            minLines = 5,
            maxLines = Int.MAX_VALUE,
            singleLine = false
        )
    }
}

@Preview
@Composable
fun MultiTextFieldPreview(){
    LoggerekTheme {
        Box(modifier = Modifier.background(MaterialTheme.colors.background)){
            MultiTextField(Modifier.padding(30.dp))
        }
    }
}