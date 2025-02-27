package com.shhatrat.loggerek.manager.watch.service

import android.app.ActivityManager
import android.content.Context
import com.shhatrat.loggerek.manager.watch.logic.WatchLogic
import org.koin.core.qualifier.qualifier
import org.koin.java.KoinJavaComponent.inject

class WearOsService : BaseBackgroundService() {

    companion object {
        fun isServiceRunning(context: Context): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                if (service.service.className == WearOsService::class.java.name) {
                    return true
                }
            }
            return false
        }
    }

    override val STOP_KEY: String = "TestServiceStop"

    override val NOTIFICATION_ID: Int = 3

    override fun actionOnStop() {
        val watchLogic by inject<WatchLogic>(qualifier = qualifier("wear"), clazz = WatchLogic::class.java)
        watchLogic.stop()
    }

    override fun actionOnStart() {
        val watchLogic by inject<WatchLogic>(qualifier = qualifier("wear"), clazz = WatchLogic::class.java)
        watchLogic.start()
    }

    override fun getServiceClass(): Class<out BaseBackgroundService>  = WearOsService::class.java
}