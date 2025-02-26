@file:OptIn(ExperimentalHorologistApi::class)

package com.shhatrat.loggerek.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TitleCard
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults.ItemType
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.ListHeaderDefaults.firstItemPadding
import com.google.android.horologist.compose.material.ResponsiveListHeader
import com.shhatrat.loggerek.api.model.GeocacheMock
import com.shhatrat.loggerek.api.model.GeocacheType
import com.shhatrat.loggerek.manager.watch.model.WatchData
import com.shhatrat.loggerek.manager.watch.model.WatchLog
import com.shhatrat.loggerek.manager.watch.model.toWatchCache
import com.shhatrat.loggerek.presentation.WearPreviewDevices

@Composable
fun CacheListScreen(watchData: WatchData, moveToLog: (String, List<WatchLog>) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ListScreen(watchData, moveToLog)
    }
}


@Composable
fun ListScreen(watchData: WatchData, moveToLog: (String, List<WatchLog>) -> Unit) {

    val columnState = rememberResponsiveColumnState(
        contentPadding = ScalingLazyColumnDefaults.padding(
            first = ItemType.Text,
            last = ItemType.SingleButton
        )
    )

    ScreenScaffold(scrollState = columnState) {
        ScalingLazyColumn(
            columnState = columnState
        ) {
            item {
                ResponsiveListHeader(contentPadding = firstItemPadding()) {
                    Text(text = "Caches")
                }
            }
            watchData.items.forEach { cache ->
                item {
                    TitleCard(
                        title = { Text(cache.title) },
                        onClick = { moveToLog(cache.cacheId, watchData.logs) }) {
                        Text(cache.cacheId)
                    }
                }
            }
        }
    }
}

@WearPreviewDevices
@Composable
fun ListScreenPreview() {
    CacheListScreen(
        WatchData(
            items = GeocacheType.entries
                .map { GeocacheMock().getByType(it) }
                .map { it.toWatchCache() },
            logs = listOf()
        )
    ) { _, _ -> }
}