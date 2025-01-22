package com.shhatrat.loggerek.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FullUser(
    val username: String,
    @SerialName(API_CACHES_FOUND)
    val cachesFound: Int,
    @SerialName(API_CACHES_NOT_FOUND)
    val cachesNotFound: Int,
    @SerialName(API_CACHES_HIDDEN)
    val cachesHidden: Int,
    @SerialName(API_RECOMMENDATIONS_GIVEN)
    val recommendationsGiven: Int,
    @SerialName(API_RECOMMENDATIONS_LEFT)
    val recommendationsLeft: Int
){
    companion object{
        fun mock() = FullUser("Mock", 100, 100, 100, 100, 100)
    }
}

const val API_USERNAME = "username"
const val API_CACHES_FOUND = "caches_found"
const val API_CACHES_NOT_FOUND = "caches_notfound"
const val API_CACHES_HIDDEN = "caches_hidden"
const val API_RECOMMENDATIONS_GIVEN = "rcmds_given"
const val API_RECOMMENDATIONS_LEFT = "rcmds_left"

enum class UserParam(val apiName: String) {
    USERNAME(API_USERNAME),
    CACHES_FOUND(API_CACHES_FOUND),
    CACHES_NOT_FOUND(API_CACHES_NOT_FOUND),
    CACHES_HIDDEN(API_CACHES_HIDDEN),
    RECOMMENDATIONS_GIVEN(API_RECOMMENDATIONS_GIVEN),
    RECOMMENDATIONS_LEFT(API_RECOMMENDATIONS_LEFT);
}
