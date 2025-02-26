package com.shhatrat.wearshared

import android.content.Intent
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import com.shhatrat.base.startValidService

class WearDataListenerService : WearableListenerService() {
    override fun onDataChanged(dataEvents: DataEventBuffer) {

        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED) {
                val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                val message = dataMap.getString("data")
                println(message)
                if(message == "GET_DATA"){
                    startValidService(Intent(this, TestService::class.java))
                }
            }
        }
    }
}