package com.shhatrat.loggerek.manager.watch

import android.app.ActivityManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.shhatrat.loggerek.manager.watch.NotificationHelper.createGarminServiceNotification
import com.shhatrat.loggerek.manager.watch.logic.WatchLogic
import org.koin.java.KoinJavaComponent.inject

class GarminBackgroundService : Service() {

    private val STOP_KEY = "STOP_KEY_GarminBackgroundService"
    private val NOTIFICATION_ID = 1

    companion object {

        fun isServiceRunning(context: Context): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                if (service.service.className == GarminBackgroundService::class.java.name) {
                    return true
                }
            }
            return false
        }
    }

    private fun Intent.shouldStop() = extras?.getBoolean(STOP_KEY) == true

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.shouldStop() == true) {
            stopSelf()
        } else {
            addServiceNotification()
            startObservingGarminActions()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        val watchLogic by inject<WatchLogic>(WatchLogic::class.java)
        watchLogic.stop()
    }

    private fun startObservingGarminActions() {
        val watchLogic by inject<WatchLogic>(WatchLogic::class.java)
        watchLogic.start()
    }

    private fun addServiceNotification() {
        val stopIntent = Intent(this, GarminBackgroundService::class.java)
        stopIntent.putExtra(STOP_KEY, true)
        val stopPendingIntent = PendingIntent.getService(
            this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = createGarminServiceNotification(this, stopPendingIntent)
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}