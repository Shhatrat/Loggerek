package com.shhatrat.loggerek.base.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


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
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}