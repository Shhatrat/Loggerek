package com.shhatrat.loggerek.presentation

import android.content.Context
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object CommunicationUtil {

    fun dataChangesFlow(context: Context): Flow<String?> = callbackFlow {
        val listener = DataClient.OnDataChangedListener { dataEvents ->
            dataEvents.forEach { event ->
                val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                val message = dataMap.getString("message")
                trySend(message).isSuccess
            }
        }
        val dataClient = Wearable.getDataClient(context)
        dataClient.addListener(listener)
        awaitClose { dataClient.removeListener(listener) }
    }
}