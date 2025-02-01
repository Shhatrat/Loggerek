@file:OptIn(ExperimentalResourceApi::class)

package com.shhatrat.loggerek.log

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.api.model.GeocacheMock
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.composable.CircularIndeterminateProgressBar
import com.shhatrat.loggerek.base.composable.Header
import com.shhatrat.loggerek.base.composable.MultiTextField
import com.shhatrat.loggerek.base.composable.MultiTextFieldModel
import com.shhatrat.loggerek.base.composable.SnackBarHelper
import com.shhatrat.loggerek.base.composable.SnackBarHelper.ProvideSnackBar
import com.shhatrat.loggerek.base.composable.StarFiller
import com.shhatrat.loggerek.base.composable.StarSwitch
import com.shhatrat.loggerek.base.composable.VerticalSegmentedButton
import com.shhatrat.loggerek.base.get
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import loggerek.feature.log.generated.resources.Res
import loggerek.feature.log.generated.resources.cleanButton
import loggerek.feature.log.generated.resources.logContent
import loggerek.feature.log.generated.resources.notes
import loggerek.feature.log.generated.resources.password
import loggerek.feature.log.generated.resources.rate1
import loggerek.feature.log.generated.resources.rate2
import loggerek.feature.log.generated.resources.rate3
import loggerek.feature.log.generated.resources.rate4
import loggerek.feature.log.generated.resources.rate5
import loggerek.feature.log.generated.resources.reccomend
import loggerek.feature.log.generated.resources.sendButton
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun LogScreen(calculateWindowSizeClass: WindowSizeCallback, logUiState: LogUiState) {
    Box(modifier = Modifier.fillMaxSize()) {

        val snackBarHostState = remember { SnackbarHostState() }
        LaunchedEffect(logUiState.error) {
            SnackBarHelper.handle(snackBarHostState, logUiState.error)
        }

        Crossfade(
            modifier = Modifier.fillMaxSize(),
            targetState = (calculateWindowSizeClass.invoke().widthSizeClass)
        ) { screenClass ->
            when (screenClass) {
                WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium ->
                    CompactScreenLayout(modifier = Modifier, logUiState)

                WindowWidthSizeClass.Expanded -> ExpandedScreenLayout(
                    Modifier.fillMaxSize(),
                    logUiState
                )
            }
        }
        ProvideSnackBar(snackBarHostState)
    }
}

@Composable
fun ExpandedScreenLayout(modifier: Modifier, logUiState: LogUiState) {
    CompactScreenLayout(modifier, logUiState)
}

@Composable
fun CompactScreenLayout(modifier: Modifier, settingsUiState: LogUiState) {
    Box(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colors.background).padding(16.dp)
    ) {
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = settingsUiState.loader.active
        ) {
            CircularIndeterminateProgressBar(
                modifier = Modifier,
                color = MaterialTheme.colors.primary
            )
        }
        Column(
            Modifier.fillMaxSize().align(Alignment.TopCenter).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            settingsUiState.geocacheData?.let {
                Header(Modifier, it.title)
                Header(Modifier, it.titleLink)
                VerticalSegmentedButton(
                    it.logTypeData.types.map { it.get() },
                    it.logTypeData.selectedIndex,
                    it.logTypeData.onChangedIndex
                )
                it.ratingData?.let { rating ->
                    var currentRating: Int? by remember { mutableStateOf(null) }
                    if (rating.showRating) {
                        StarFiller(currentSelectedIndex = currentRating, size = 5) {
                            currentRating = it
                            rating.starsOnChanged(it)
                        }
                        Text(
                            when (currentRating) {
                                0 -> Res.string.rate1
                                1 -> Res.string.rate2
                                2 -> Res.string.rate3
                                3 -> Res.string.rate4
                                null -> null
                                else -> Res.string.rate5
                            }?.get() ?: ""
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (rating.recommendationPossible && rating.showRating) {
                            Text(Res.string.reccomend.get())
                            StarSwitch(selected = rating.recommendation, onClicked = {
                                rating.recommendationChanged(!rating.recommendation)
                            })
                        }
                    }
                }
                Text(Res.string.logContent.get())
                MultiTextField(
                    multiTextFieldModel = it.description,
                    placeholder = { }
                )
                Text(Res.string.notes.get())
                MultiTextField(
                    multiTextFieldModel = it.myNotes,
                    placeholder = { }
                )

                it.password?.let { it1 ->
                    Text(Res.string.password.get())
                    MultiTextField(
                        multiTextFieldModel = it1,
                        lines = 2
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
                    Button(onClick = { it.sendAction() }) {
                        Text(Res.string.sendButton.get())
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
                    Button(onClick = { it.resetAction() }) {
                        Text(Res.string.cleanButton.get())
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
                }
            }
        }

        if (settingsUiState.success != null) {
            Box(Modifier.fillMaxSize()) {
                val composition by rememberLottieComposition {
                    LottieCompositionSpec.JsonString(
                        Res.readBytes("files/confettiAnimation.json").decodeToString()
                    )
                }
                val progress by animateLottieCompositionAsState(composition)
                if (progress == 1f) {
                    LaunchedEffect(Unit) {
                        settingsUiState.success.onFinished?.invoke()
                    }
                }
                Image(
                    modifier = Modifier.fillMaxSize().align(Alignment.Center),
                    painter = rememberLottiePainter(
                        composition = composition,
                        progress = { progress },
                    ),
                    contentDescription = "Lottie animation"
                )
            }
        }
    }
}

@Preview
@Composable
fun LogScreenPreview() {
    LoggerekTheme {
        val gd = GeocacheMock().let { cache ->
            GeocacheData(
                title = cache.name,
                titleLink = cache.url,
                ratingData = RatingData(true, 5, {}, true, true, {}),
                logTypeData = LogTypeData(
                    0,
                    cache.type.logType.logTypes.map { it.textRes },
                    onChangedIndex = {  }),
                description = MultiTextFieldModel(),
                myNotes = MultiTextFieldModel(),
                password = MultiTextFieldModel(),
                sendAction = {},
                resetAction = {},
            )
        }
        CompactScreenLayout(Modifier, LogUiState(geocacheData = gd))
    }
}