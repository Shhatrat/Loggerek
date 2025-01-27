package com.shhatrat.loggerek.base.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.LoggerekTheme

const val segmentedButtonTag = "com.shhatrat.loggerek.base.composable.segmentedButtonTag"


@Composable
fun SegmentedButton(
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag(segmentedButtonTag)
    ) {
        options.forEachIndexed { index, option ->

            val isSelected = selectedIndex == index
            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.background,
                animationSpec = tween(durationMillis = 300)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        1.dp, MaterialTheme.colors.primary, if (index == 0) RoundedCornerShape(
                            topStart = 8.dp, bottomStart = 8.dp
                        ) else if (index == options.size - 1) RoundedCornerShape(
                            topEnd = 8.dp, bottomEnd = 8.dp
                        ) else RoundedCornerShape(0.dp)
                    )
                    .background(
                        backgroundColor,
                        shape = if (index == 0) RoundedCornerShape(
                            topStart = 8.dp, bottomStart = 8.dp
                        ) else if (index == options.size - 1) RoundedCornerShape(
                            topEnd = 8.dp, bottomEnd = 8.dp
                        ) else RoundedCornerShape(0.dp)
                    )
                    .clickable { onOptionSelected(index) }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    color = if (selectedIndex == index) MaterialTheme.colors.background else MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body1,
                    )
            }
        }
    }
}

@Composable
fun VerticalSegmentedButton(
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag(segmentedButtonTag)
    ) {
        options.forEachIndexed { index, option ->

            val isSelected = selectedIndex == index
            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.background,
                animationSpec = tween(durationMillis = 300)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp, MaterialTheme.colors.primary, if (index == 0) RoundedCornerShape(
                            topStart = 8.dp, topEnd = 8.dp
                        ) else if (index == options.size - 1) RoundedCornerShape(
                            bottomStart = 8.dp, bottomEnd = 8.dp
                        ) else RoundedCornerShape(0.dp)
                    )
                    .background(
                        backgroundColor,
                        shape = if (index == 0) RoundedCornerShape(
                            topStart = 8.dp, topEnd = 8.dp
                        ) else if (index == options.size - 1) RoundedCornerShape(
                            bottomStart = 8.dp, bottomEnd = 8.dp
                        ) else RoundedCornerShape(0.dp)
                    )
                    .clickable { onOptionSelected(index) }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    color = if (selectedIndex == index) MaterialTheme.colors.background else MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SegmentedButton3Elements(){
    Column {
        LoggerekTheme {
            var selectedIndex by remember { mutableStateOf(0) }
            val options = listOf("Option 1", "option2", "Option 3")

            SegmentedButton(
                options = options,
                selectedIndex = selectedIndex,
                onOptionSelected = { selectedIndex = it }
            )
        }
        LoggerekTheme {
            var selectedIndex by remember { mutableStateOf(1) }
            val options = listOf("Option 1", "option2", "else",  "Option 3")

            SegmentedButton(
                options = options,
                selectedIndex = selectedIndex,
                onOptionSelected = { selectedIndex = it }
            )
        }
    }
}

@Preview
@Composable
private fun VerticalSegmentedButton3Elements(){
    Column {
        LoggerekTheme {
            var selectedIndex by remember { mutableStateOf(0) }
            val options = listOf("Option 1", "option2", "Option 3")

            VerticalSegmentedButton(
                options = options,
                selectedIndex = selectedIndex,
                onOptionSelected = { selectedIndex = it }
            )
        }
        LoggerekTheme {
            var selectedIndex by remember { mutableStateOf(1) }
            val options = listOf("Option 1", "option2", "else",  "Option 3")

            VerticalSegmentedButton(
                options = options,
                selectedIndex = selectedIndex,
                onOptionSelected = { selectedIndex = it }
            )
        }
    }
}