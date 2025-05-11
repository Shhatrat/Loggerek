@file:OptIn(ExperimentalResourceApi::class, ExperimentalResourceApi::class)

package com.shhatrat.loggerek.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.api.model.GeocacheMock
import com.shhatrat.loggerek.api.model.GeocacheType
import com.shhatrat.loggerek.api.model.LogEntry
import com.shhatrat.loggerek.api.model.LogUser
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.cache.CacheItem
import com.shhatrat.loggerek.base.composable.CircularIndeterminateProgressBar
import com.shhatrat.loggerek.base.composable.SnackBarHelper
import com.shhatrat.loggerek.base.composable.SnackBarHelper.ProvideSnackBar
import com.shhatrat.loggerek.base.get
import loggerek.feature.log.generated.resources.Res
import loggerek.feature.log.generated.resources.searchPlaceholder
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun SearchScreen(calculateWindowSizeClass: WindowSizeCallback, searchUiState: SearchUiState) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {

        val snackBarHostState = remember { SnackbarHostState() }
        LaunchedEffect(searchUiState.error) {
            SnackBarHelper.handle(snackBarHostState, searchUiState.error)
        }

        Crossfade(
            modifier = Modifier.fillMaxSize(),
            targetState = (calculateWindowSizeClass.invoke().widthSizeClass)
        ) { screenClass ->
            when (screenClass) {
                WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium ->
                    CompactScreenLayout(modifier = Modifier, searchUiState)

                WindowWidthSizeClass.Expanded -> ExpandedScreenLayout(
                    Modifier.fillMaxSize(),
                    searchUiState
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = searchUiState.loader.active
        ) {
            CircularIndeterminateProgressBar(
                color = MaterialTheme.colors.primary
            )
        }
        ProvideSnackBar(snackBarHostState)
    }
}

@Composable
private fun ExpandedScreenLayout(modifier: Modifier, searchUiState: SearchUiState) {
    CompactScreenLayout(modifier, searchUiState)
}

@Composable
private fun CompactScreenLayout(modifier: Modifier, searchUiState: SearchUiState) {
    Column(modifier = modifier.fillMaxSize()) {
        TextField(
            placeholder = { Text(color = Color.Gray, text = Res.string.searchPlaceholder.get()) },
            modifier = Modifier.fillMaxWidth(),
            value = searchUiState.search.text,
            onValueChange = searchUiState.search.onChange
        )
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            searchUiState.caches.forEach {
                CacheItem(it, searchUiState.move)
            }
        }
    }
}




@Preview
@Composable
private fun CacheItemPreview() {
    LoggerekTheme {
        Box(Modifier.background(MaterialTheme.colors.background).padding(10.dp).scale(1f)) {
            Column {
                GeocacheType.entries.forEach {
                    CacheItem(GeocacheMock().getByType(it).copy(userLogOnly = listOf()), {})
                    CacheItem(
                        GeocacheMock().getByType(it)
                            .copy(userLogOnly = listOf(LogEntry("Found it", LogUser("", "", "")))),
                        {})
                }
            }
        }
    }
}