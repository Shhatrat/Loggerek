package com.shhatrat.loggerek.manager.watch

import android.content.Intent
import android.content.pm.PackageManager
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import com.shhatrat.base.startValidService
import com.shhatrat.loggerek.manager.watch.service.WearOsService

class WearDataListenerService : WearableListenerService() {
    override fun onDataChanged(dataEvents: DataEventBuffer) {

        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED) {
                val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                val message = dataMap.getString("data")
                println(message)
                if (message == "GET_DATA" && !isWearOS() && !WearOsService.isServiceRunning(this)) {
                    startValidService(Intent(this, WearOsService::class.java))
                }
            }
        }
    }

    private fun isWearOS(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_WATCH)
    }
}