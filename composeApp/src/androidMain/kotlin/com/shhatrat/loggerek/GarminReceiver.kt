package com.shhatrat.loggerek

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.shhatrat.base.startValidService
import com.shhatrat.loggerek.manager.watch.NotificationHelper.createNotificationForStartAppWithService
import com.shhatrat.loggerek.manager.watch.service.GarminBackgroundService
import com.shhatrat.loggerek.manager.watch.model.WatchRetrieveKeys
import okio.internal.commonToUtf8String

class GarminReceiver : BroadcastReceiver() {

    val EXTRA_APPLICATION_ID: String = "com.garmin.android.connectiq.EXTRA_APPLICATION_ID"
    val EXTRA_PAYLOAD: String = "com.garmin.android.connectiq.EXTRA_PAYLOAD"

    val appId = "10B02FDA7FD64174A2920602F1EB9886"

    private fun checkIsGetDataRequest(intent: Intent): Boolean {
        if (intent.hasExtra(EXTRA_PAYLOAD))
            return intent.extras?.getByteArray(EXTRA_PAYLOAD)?.commonToUtf8String()
                ?.contains(WatchRetrieveKeys.GET_DATA.key) == true
        return false
    }

    private fun isAppIdCorrect(intent: Intent): Boolean {
        if (intent.hasExtra(EXTRA_APPLICATION_ID))
            return intent.getStringExtra(EXTRA_APPLICATION_ID) == appId
        return false
    }


    override fun onReceive(context: Context, intent: Intent) {
        if (isGarminGetDataRequest(intent)) {
            if (!GarminBackgroundService.isServiceRunning(context)) {
                if(AndroidApp.appState.value){
                    context.startValidService(Intent(context, GarminBackgroundService::class.java))
                }else{
                    showNotificationToStartService(context)
                }
            }
        }
    }

    private fun isGarminGetDataRequest(intent: Intent) =
        checkIsGetDataRequest(intent) && isAppIdCorrect(intent)

    private fun showNotificationToStartService(context: Context) {
        context.createNotificationForStartAppWithService(context)
    }
}
