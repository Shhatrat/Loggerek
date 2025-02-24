package com.shhatrat.loggerek.manager.watch.model

import kotlinx.serialization.Serializable

@Serializable
sealed class WatchRetrieveKeys(val key: String) {
    @Serializable
    object GET_DATA : WatchRetrieveKeys("GET_DATA")

    @Serializable
    class SET_LOG(val cacheId: String, val logId: String) : WatchRetrieveKeys("SET_LOG")

    fun Map<String,String>.parseWatchRetrieveKeys(): WatchRetrieveKeys{
        return when(this["type"]){
            "SET_LOG" -> SET_LOG(this["cacheId"]!!, this["logId"]!!)
            "GET_DATA" ->  GET_DATA
            else -> throw Exception("Bad input!")
        }
    }
}
