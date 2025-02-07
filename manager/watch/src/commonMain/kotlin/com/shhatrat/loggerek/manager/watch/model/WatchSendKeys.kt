package com.shhatrat.loggerek.manager.watch.model

import kotlinx.serialization.Serializable

@Serializable
sealed class WatchSendKeys(val key: String) {
    abstract fun mapString(): Map<String, Any>

    @Serializable
    class GET_DATA(val watchData: WatchData) : WatchSendKeys("GetData") {
        override fun mapString(): Map<String, Any> {
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