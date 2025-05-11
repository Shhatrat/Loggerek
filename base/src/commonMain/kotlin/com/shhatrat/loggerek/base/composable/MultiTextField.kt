package com.shhatrat.loggerek.base.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.LoggerekTheme

@Composable
fun MultiTextField(
    modifier: Modifier = Modifier,
    multiTextFieldModel: MultiTextFieldModel,
    lines: Int = 5,
    placeholder: @Composable () -> Unit = {}
) {
    MultiTextField(
        modifier = modifier,
        text = multiTextFieldModel.text,
        onChange = multiTextFieldModel.onChange,
        lines = lines,
        placeholder = placeholder
    )
}

@Composable
fun MultiTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    onChange: (String) -> Unit = {},
    lines: Int = 5,
    maxLines: Int = Int.MAX_VALUE,
    placeholder: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = text,
            onValueChange = { onChange(it) },
            modifier = Modifier.fillMaxSize(),
            placeholder = { placeholder() },
            minLines = lines,
            singleLine = false
        )
    }
}

data class MultiTextFieldModel(
    val text: String = "",
    val onChange: (String) -> Unit = {}
)

@Preview
@Composable
fun MultiTextFieldPreview() {
    LoggerekTheme {
        Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
            MultiTextField(Modifier.padding(30.dp))
        }
    }
}