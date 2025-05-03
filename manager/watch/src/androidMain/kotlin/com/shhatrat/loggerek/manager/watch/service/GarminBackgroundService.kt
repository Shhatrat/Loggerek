package com.shhatrat.loggerek.manager.watch.service

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import com.shhatrat.loggerek.manager.watch.logic.WatchLogic
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import org.koin.java.KoinJavaComponent.inject

class GarminBackgroundService : BaseBackgroundService() {

    override fun getServiceClass(): Class<out BaseBackgroundService>  = GarminBackgroundService::class.java

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

    override val STOP_KEY: String = "GarminBackgroundServiceStop"

    override val NOTIFICATION_ID: Int = 2

    override fun actionOnStop() {
        val watchLogic by inject<WatchLogic>(qualifier = qualifier("garmin"), clazz = WatchLogic::class.java)// WatchLogic::class.java)
        watchLogic.stop()
    }

    override fun actionOnStart() {
        val watchLogic by inject<WatchLogic>(qualifier = qualifier("garmin"), clazz = WatchLogic::class.java)// WatchLogic::class.java)
        watchLogic.start()
    }
}