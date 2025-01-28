package com.shhatrat.loggerek.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.composable.CircularIndeterminateProgressBar
import com.shhatrat.loggerek.base.composable.Header
import com.shhatrat.loggerek.base.composable.SnackBarHelper
import com.shhatrat.loggerek.base.composable.SnackBarHelper.ProvideSnackBar
import com.shhatrat.loggerek.base.composable.Switch
import com.shhatrat.loggerek.base.get
import loggerek.feature.settings.generated.resources.Res
import loggerek.feature.settings.generated.resources.logout
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun SettinsScreen(calculateWindowSizeClass: WindowSizeCallback, settingsUiState: SettingsUiState) {

    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(settingsUiState.error) {
        SnackBarHelper.handle(snackBarHostState, settingsUiState.error)
    }

    Crossfade(targetState = (calculateWindowSizeClass.invoke().widthSizeClass)) { screenClass ->
        when (screenClass) {
            WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium ->
                CompactScreenLayout(modifier = Modifier.fillMaxSize(), settingsUiState)

            WindowWidthSizeClass.Expanded -> ExpandedScreenLayout(
                Modifier.fillMaxSize(),
                settingsUiState
            )
        }
    }
    ProvideSnackBar(snackBarHostState)
}

@Composable
fun ExpandedScreenLayout(modifier: Modifier, settingsUiState: SettingsUiState) {
    CompactScreenLayout(modifier, settingsUiState)
}

@Composable
fun CompactScreenLayout(modifier: Modifier, settingsUiState: SettingsUiState) {
    Box(modifier = modifier.background(MaterialTheme.colors.background).padding(16.dp)) {
        AnimatedVisibility(settingsUiState.loader.active) {
            CircularIndeterminateProgressBar(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colors.primary
            )
        }
        Column(
            Modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            settingsUiState.settings.forEach { handleMapper(it) }
        }
    }
}

@Composable
private fun handleMapper(settingsItem: SettingsItem) {
    when (settingsItem) {
        is SettingsItem.SettingsButton -> {
            SettingsButton(settingsItem)
        }

        is SettingsItem.SettingsSwitch -> {
            SwitchWithLabel(
                label = settingsItem.descriptionRes.get(),
                settingsItem.checked,
                settingsItem.onChecked
            )
        }

        is SettingsItem.SettingsTitle -> {
            Header(text = settingsItem.descriptionRes.get())
        }
    }
}

@Composable
fun SettingsButton(settingsItem: SettingsItem.SettingsButton) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
            .padding(8.dp)
            .clickable { settingsItem.action() }
    ) {
        Text(
            color = MaterialTheme.colors.background,
            modifier = Modifier,
            text = settingsItem.descriptionRes.get()
        )
        Spacer(Modifier.weight(1f))
        Image(
            colorFilter = ColorFilter.tint(MaterialTheme.colors.background),
            painter = painterResource(settingsItem.iconRes),
            contentDescription = null
        )
    }
}

@Composable
private fun SwitchWithLabel(label: String, state: Boolean, onStateChange: (Boolean) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
            .padding(8.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Switch,
                onClick = {
                    onStateChange(!state)
                }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(color = MaterialTheme.colors.background, text = label)
        Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
        Switch(checked = state)
    }
}
