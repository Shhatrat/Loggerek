package com.shhatrat.wearshared

import android.content.Context
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

object CommunicationManager {

    private enum class Type(val path: String) {
        MOBILE("/mobile"), WATCH("/watch")
    }

    private enum class Key{
        data, timestamp
    }

    suspend fun sendDataToWatch(context: Context, data: String) {
        send(context, Type.WATCH, data)
    }

    suspend fun sendDataToPhone(context: Context, data: String) {
        send(context, Type.MOBILE, data)
    }

    fun observeDataFromWatch(context: Context): Flow<String?> = observe(context, Type.MOBILE)

    fun observeDataFromPhone(context: Context): Flow<String?> = observe(context, Type.WATCH)

    private suspend fun send(context: Context, type: Type, data: String){
        val putDataMapRequest = PutDataMapRequest.create(type.path)
        putDataMapRequest.dataMap.putString(Key.data.name, data)
        putDataMapRequest.dataMap.putString(Key.timestamp.name, System.currentTimeMillis().toString())

        val putDataRequest = putDataMapRequest.asPutDataRequest().setUrgent()

        val dataClient = Wearable.getDataClient(context)
        dataClient.putDataItem(putDataRequest).await()
    }

    private fun observe(context: Context, type: Type): Flow<String?> = callbackFlow {
        val listener = DataClient.OnDataChangedListener { dataEvents ->
            dataEvents.forEach { event ->
                if (event.dataItem.uri.path == type.path) {
                    val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                    val message = dataMap.getString(Key.data.name)
                    trySend(message).isSuccess
                }
            }
        }
        val dataClient = Wearable.getDataClient(context)
        dataClient.addListener(listener)
        awaitClose { dataClient.removeListener(listener) }
    }

    suspend fun Context.isConnected(): Boolean{
        val nodes = Wearable.getNodeClient(this).connectedNodes.await()
        return nodes.any { it.isNearby }
    }
}