@file:OptIn(ExperimentalHorologistApi::class)

package com.shhatrat.loggerek.presentation.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.shhatrat.loggerek.manager.watch.model.WatchLog
import com.shhatrat.loggerek.manager.watch.model.WatchRetrieveKeys
import com.shhatrat.loggerek.presentation.WearPreviewDevices
import com.shhatrat.wearshared.CommunicationManager
import kotlinx.coroutines.launch


@Composable
fun LogListScreen(cacheId: String, logs: List<WatchLog>, moveToIntro: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

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
                        Text(text = cacheId)
                    }
                }
                logs.forEach { log ->
                    item {
                        val context = LocalContext.current
                        val scope = rememberCoroutineScope()
                        TitleCard(title = { Text(log.text) }, onClick = {
                            scope.launch { sendData(context, cacheId, log, moveToIntro) }
                        }) {
                            Text(log.type)
                        }
                    }
                }
            }
        }
    }
}

suspend fun sendData(context: Context, cacheId: String, log: WatchLog, moveToIntro: () -> Unit) {
    CommunicationManager.sendDataToPhone(
        context,
        WatchRetrieveKeys.SET_LOG(cacheId, log.logId).toString()
    )
    moveToIntro()
}

@WearPreviewDevices
@Composable
fun LogListScreen() {
    LogListScreen("OP0001", listOf(
        WatchLog("OK", "1", "Found it"),
        WatchLog("Super skrzyneczka wszystko sie udalo", "2", "Found it"),
        WatchLog("Po trudach sie udalo", "2", "Found it"))
    ) { }
}
