package com.shhatrat.wearshared

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class TestService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        addServiceNotification()
        return START_STICKY
    }

    private fun addServiceNotification() {
        val notification = createServiceNotification(this)
        startForeground(3, notification)
    }

    fun Context.createServiceNotification(context: Context): Notification {
        return NotificationCompat.Builder(this, "GARMIN_CHANNEL")
            .setContentTitle("Test")
            .setContentText("test2")
            .setSmallIcon(R.drawable.walk)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setOngoing(true)
            .build()
    }
}