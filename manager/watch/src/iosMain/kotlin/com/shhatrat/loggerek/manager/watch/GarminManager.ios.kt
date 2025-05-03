package com.shhatrat.loggerek.manager.watch

import com.shhatrat.loggerek.manager.watch.model.WatchSendKeys
import kotlinx.coroutines.flow.Flow

actual class GarminManager : GarminWatch {
    override fun getListOfDevices(): List<GarminWatch.GarminDevice> {
        TODO("Not yet implemented")
    }

    override suspend fun isAppInstalled(garminDevice: GarminWatch.GarminDevice): GarminWatch.InstalledAppState {
        TODO("Not yet implemented")
    }

    override fun selectDevice(garminDevice: GarminWatch.GarminDevice) {
        TODO("Not yet implemented")
    }

    override suspend fun loadApp(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun init(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getData(): Flow<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun sendData(watchSendKeys: WatchSendKeys): Boolean {
        TODO("Not yet implemented")
    }
}