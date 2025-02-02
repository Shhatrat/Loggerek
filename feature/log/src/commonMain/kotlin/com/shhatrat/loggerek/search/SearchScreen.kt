@file:OptIn(ExperimentalResourceApi::class, ExperimentalResourceApi::class)

package com.shhatrat.loggerek.search

import androidx.compose.animation.Crossfade
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.GeocacheMock
import com.shhatrat.loggerek.api.model.GeocacheType
import com.shhatrat.loggerek.api.model.LogEntry
import com.shhatrat.loggerek.api.model.LogUser
import com.shhatrat.loggerek.api.model.isFound
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.MoveToLogCache
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.composable.SnackBarHelper
import com.shhatrat.loggerek.base.composable.SnackBarHelper.ProvideSnackBar
import com.shhatrat.loggerek.base.get
import loggerek.feature.log.generated.resources.Res
import loggerek.feature.log.generated.resources.searchPlaceholder
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

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

@Composable
private fun CacheItem(geocache: Geocache, move: MoveToLogCache?) {
    var selectedCache by remember { mutableStateOf<String?>(null) }

    if (selectedCache != null) {
        move?.invoke(geocache.code)
    }

    Box(modifier = Modifier
        .clickable { selectedCache = geocache.code }
        .padding(8.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .background(MaterialTheme.colors.primary))
    {
        Row(modifier = Modifier.padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(100))
                    .background(MaterialTheme.colors.background).padding(4.dp).size(45.dp)
            ) {
                Image(
//                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.align(Alignment.Center).padding(4.dp).size(50.dp),
                    contentDescription = "cache type",
                    painter = painterResource(geocache.type.iconRes)
                )
                if (geocache.isFound()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(25.dp)
                            .clip(RoundedCornerShape(100))
                            .background(MaterialTheme.colors.background),
                    )
                    Image(
                        modifier = Modifier.align(Alignment.BottomEnd).size(25.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                        painter = rememberAsyncImagePainter(Res.getUri("drawable/checkDone.svg")),
                        contentDescription = "cache found",
                    )
                }
            }
            Text(
                modifier = Modifier.padding(4.dp),
                color = MaterialTheme.colors.background, text = geocache.code
            )
            Text(
                modifier = Modifier.padding(4.dp),
                color = MaterialTheme.colors.background, text = geocache.name
            )
        }
    }
}


@Preview
@Composable
private fun p() {
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