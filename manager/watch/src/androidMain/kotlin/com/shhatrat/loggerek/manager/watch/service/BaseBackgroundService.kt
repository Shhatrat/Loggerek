package com.shhatrat.loggerek.manager.watch.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.shhatrat.loggerek.manager.watch.NotificationHelper.createGarminServiceNotification

abstract class BaseBackgroundService: Service() {

    abstract val STOP_KEY: String

    abstract val NOTIFICATION_ID: Int

    override fun onBind(p0: Intent?): IBinder? = null

    abstract fun actionOnStop()

    abstract fun actionOnStart()

    abstract fun getServiceClass(): Class<out BaseBackgroundService>

    private fun Intent.shouldStop() = extras?.getBoolean(STOP_KEY) == true

    override fun onDestroy() {
        super.onDestroy()
        actionOnStop()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.shouldStop() == true) {
            stopSelf()
        } else {
            addServiceNotification()
            actionOnStart()
        }
        return START_STICKY
    }

    private fun addServiceNotification() {
        val stopIntent = Intent(this, getServiceClass())
        stopIntent.putExtra(STOP_KEY, true)
        val stopPendingIntent = PendingIntent.getService(
            this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = createGarminServiceNotification(this, stopPendingIntent)
        startForeground(NOTIFICATION_ID, notification)
    }
}