package com.shhatrat.loggerek.manager.watch

import com.shhatrat.loggerek.manager.watch.model.WatchSendKeys
import kotlinx.coroutines.flow.Flow

actual class GarminManager() : Watch{

    override suspend fun init(): Boolean {
        TODO()
    }

    override fun getData(): Flow<Any> {
        TODO()
    }

    override suspend fun sendData(watchSendKeys: WatchSendKeys): Boolean{
        TODO()
    }
}
