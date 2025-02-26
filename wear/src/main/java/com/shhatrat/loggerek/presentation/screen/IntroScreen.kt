@file:OptIn(ExperimentalHorologistApi::class)

package com.shhatrat.loggerek.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.foundation.rotary.RotaryScrollableDefaults.behavior
import androidx.wear.compose.foundation.rotary.rotaryScrollable
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults.ItemType
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.material.Chip
import com.google.android.horologist.compose.material.ListHeaderDefaults.firstItemPadding
import com.google.android.horologist.compose.material.ResponsiveListHeader
import com.shhatrat.loggerek.R
import com.shhatrat.loggerek.base.color.LoggerekColor.YellowBackground
import com.shhatrat.loggerek.manager.watch.model.WatchRetrieveKeys
import com.shhatrat.loggerek.presentation.WearPreviewDevices
import com.shhatrat.wearshared.CommunicationManager
import kotlinx.coroutines.launch


@Composable
fun IntroScreen() {
    val scrollState = rememberScrollState()
    ScreenScaffold(scrollState = scrollState) {
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        var loader by remember { mutableStateOf(false) }

        val padding = ScalingLazyColumnDefaults.padding(
            first = ItemType.Text,
            last = ItemType.Chip
        )()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .rotaryScrollable(
                    behavior(scrollableState = scrollState),
                    focusRequester = rememberActiveFocusRequester()
                )
                .padding(padding),
            verticalArrangement = Arrangement.Center
        ) {
            ResponsiveListHeader(contentPadding = firstItemPadding()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = YellowBackground,
                    text = stringResource(R.string.introTitle)
                )
            }
            Chip(
                colors = ChipDefaults.chipColors(
                    backgroundColor = YellowBackground,
                    contentColor = Color.Black,
                ),
                label = stringResource(R.string.showCaches), onClick = {
                loader = true
                scope.launch {
                    CommunicationManager.sendDataToPhone(context, WatchRetrieveKeys.GET_DATA.key)
                }
            })
            AnimatedVisibility(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), visible = loader) {
                CircularProgressIndicator(
                    indicatorColor = YellowBackground
                )
            }
        }
    }
}


@WearPreviewDevices
@Composable
fun IntroPreview() {
    IntroScreen()
}
