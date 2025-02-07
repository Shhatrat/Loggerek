package com.shhatrat.loggerek.manager.watch.model

import kotlinx.serialization.Serializable

@Serializable
sealed class WatchRetrieveKeys(val key: String) {
    @Serializable
    object GET_DATA : WatchRetrieveKeys("GET_DATA")

    @Serializable
    class SET_LOG(val cacheId: String, val logId: String) : WatchRetrieveKeys("SET_LOG")
}
