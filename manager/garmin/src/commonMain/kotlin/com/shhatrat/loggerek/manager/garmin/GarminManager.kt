package com.shhatrat.loggerek.manager.garmin

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
sealed class WatchRetrieveKeys(val key: String) {
    @Serializable
    object GET_DATA : WatchRetrieveKeys("GET_DATA")

    @Serializable
    class SET_LOG(val cacheId: String, val logId: String) : WatchRetrieveKeys("SET_LOG")
}

@Serializable
sealed class WatchSendKeys(val key: String) {
    abstract fun mapString(): Any

    @Serializable
    class GET_DATA(val watchData: WatchData) : WatchSendKeys("GetData") {
        override fun mapString(): Any {
            return mapOf(
                "type" to "GET_DATA",
                "items" to watchData.items.map {
                    (mapOf(
                        "title" to it.title,
                        "cacheId" to it.cacheId,
                    ))
                }, "logs" to watchData.logs.map { mapOf("text" to it.text, "logId" to it.logId, "type" to it.type) })
        }
    }
}

@Serializable
data class WatchData(
    val items: List<WatchCache>,
    val logs: List<WatchLog>
)

@Serializable
data class WatchLog(
    val text: String,
    val logId: String,
    val type: String
)

@Serializable
data class WatchCache(
    val title: String,
    val cacheId: String,
)

expect class GarminManager {

    suspend fun init(): Boolean

    fun getData(): Flow<Any>

    suspend fun sendData(watchSendKeys: WatchSendKeys): Boolean
}