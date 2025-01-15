package com.shhatrat.loggerek.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FullUser(
    val username: String,
    @SerialName("caches_found")
    val cachesFound: Int,
    @SerialName("caches_notfound")
    val cachesNotFound: Int,
    @SerialName("caches_hidden")
    val cachesHidden: Int,
    @SerialName("rcmds_given")
    val recommendationsGiven: Int,
    @SerialName("rcmds_left")
    val recommendationsLeft: Int
){
    companion object{
        fun mock() = FullUser("Mock", 100, 100, 100, 100, 100)
    }
}