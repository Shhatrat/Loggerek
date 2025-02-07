package com.shhatrat.loggerek.manager.watch

import com.shhatrat.loggerek.manager.watch.model.WatchSendKeys
import kotlinx.coroutines.flow.Flow

interface Watch {

    suspend fun init(): Boolean

    fun getData(): Flow<Any>

    suspend fun sendData(watchSendKeys: WatchSendKeys): Boolean

}