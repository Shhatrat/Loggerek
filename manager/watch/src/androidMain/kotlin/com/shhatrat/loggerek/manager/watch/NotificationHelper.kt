package com.shhatrat.loggerek.manager.watch

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.shhatrat.base.BaseData
import com.shhatrat.base.NotificationHelper.CHANNEL_ID
import org.koin.java.KoinJavaComponent.inject

object NotificationHelper {

    fun Context.createGarminServiceNotification(context: Context, stopPendingIntent: PendingIntent): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.serviceTitle))
            .setContentText(context.getString(R.string.serviceDescription))
            .setSmallIcon(R.drawable.walk)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setOngoing(true)
            .addAction(R.drawable.stop, context.getString(R.string.stopService), stopPendingIntent)
            .build()
    }

    fun Context.createNotificationForStartAppWithService(context: Context) {
        val baseData by inject<BaseData>(BaseData::class.java)
        val intent = Intent(this, baseData.getMainActivity()).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(baseData.notificationKey(), true)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.walk)
            .setContentTitle(context.getString(R.string.openAppTitle))
            .setContentText(context.getString(R.string.openAppDescription))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
            1,
            notification
        )
    }
}