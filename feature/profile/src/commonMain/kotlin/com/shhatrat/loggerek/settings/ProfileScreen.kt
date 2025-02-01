package com.shhatrat.loggerek.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.composable.CircularIndeterminateProgressBar
import com.shhatrat.loggerek.base.composable.Header
import com.shhatrat.loggerek.base.composable.SnackBarHelper
import com.shhatrat.loggerek.base.composable.SnackBarHelper.ProvideSnackBar
import com.shhatrat.loggerek.base.get
import loggerek.feature.profile.generated.resources.Res
import loggerek.feature.profile.generated.resources.cachesFound
import loggerek.feature.profile.generated.resources.cachesHidden
import loggerek.feature.profile.generated.resources.cachesNotFound
import loggerek.feature.profile.generated.resources.recommendationsGiven
import loggerek.feature.profile.generated.resources.recommendationsLeft

@Composable
fun ProfileScreen(calculateWindowSizeClass: WindowSizeCallback, profileUiState: ProfileUiState) {

    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(profileUiState.error) {
        SnackBarHelper.handle(snackBarHostState, profileUiState.error)
    }

    Crossfade(targetState = (calculateWindowSizeClass.invoke().widthSizeClass)) { screenClass ->
        when (screenClass) {
            WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium ->
                CompactScreenLayout(modifier = Modifier.fillMaxSize(), profileUiState)

            WindowWidthSizeClass.Expanded -> ExpandedScreenLayout(
                Modifier.fillMaxSize(),
                profileUiState
            )
        }
    }
    ProvideSnackBar(snackBarHostState)
}

@Composable
fun ExpandedScreenLayout(modifier: Modifier, profileUiState: ProfileUiState) {
    CompactScreenLayout(modifier, profileUiState)
}

@Composable
fun CompactScreenLayout(modifier: Modifier, profileUiState: ProfileUiState) {
    Box(modifier = modifier.background(MaterialTheme.colors.background).padding(16.dp)) {
        profileUiState.user?.let { user ->
            Column(
                Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header(Modifier, user.username)
                listOf(
                    Pair(Res.string.cachesFound, user.cachesFound),
                    Pair(Res.string.cachesNotFound, user.cachesNotFound),
                    Pair(Res.string.cachesHidden, user.cachesHidden),
                    Pair(Res.string.recommendationsGiven, user.recommendationsGiven),
                    Pair(Res.string.recommendationsLeft, user.recommendationsLeft),
                ).forEach {
                    SingleItem(it.first.get(), it.second)
                }
            }
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = profileUiState.loader.active
        ) {
            CircularIndeterminateProgressBar(
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun SingleItem(description: String, value: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
            .padding(8.dp)
    ) {
        Text(color = MaterialTheme.colors.background, modifier = Modifier, text = description)
        Spacer(Modifier.weight(1f))
        Text(color = MaterialTheme.colors.background, modifier = Modifier, text = value.toString())
    }
}
