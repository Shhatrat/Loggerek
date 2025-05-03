package com.shhatrat.loggerek.api.model

import com.shhatrat.loggerek.api.model.OpencachingParam.User.Companion.API_CACHES_FOUND
import com.shhatrat.loggerek.api.model.OpencachingParam.User.Companion.API_CACHES_HIDDEN
import com.shhatrat.loggerek.api.model.OpencachingParam.User.Companion.API_CACHES_NOT_FOUND
import com.shhatrat.loggerek.api.model.OpencachingParam.User.Companion.API_RECOMMENDATIONS_GIVEN
import com.shhatrat.loggerek.api.model.OpencachingParam.User.Companion.API_RECOMMENDATIONS_LEFT
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
    val recommendationsLeft: Int,
) {
    companion object {
        fun mock() = FullUser("Mock", 100, 100, 100, 100, 100)
    }
}
