package com.shhatrat.loggerek.api.model

import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheStatus.Companion.GEOCACHE_API_STATUS_ARCHIVED
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheStatus.Companion.GEOCACHE_API_STATUS_AVAILABLE
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheStatus.Companion.GEOCACHE_API_STATUS_TEMPORARILY_UNAVAILABLE
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheUser.Companion.GEOCACHE_API_USER_USERNAME
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheUser.Companion.GEOCACHE_API_USER_USER_PROFILE
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheUser.Companion.GEOCACHE_API_USER_UUID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geocache(
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_CODE) val code: String,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_NAME) val name: String,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_LOCATION) val location: String,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_TYPE) val type: GeocacheType,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_STATUS) val status: GeocacheStatus,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_URL) val url: String,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_DIFFICULTY) val difficulty: Double,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_TERRAIN) val terrain: Double,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_SIZE) val size: String,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_OWNER) val owner: GeocacheUser,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_DATE_HIDDEN) val dateHidden: String,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_FOUNDS) val founds: Int,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_NOT_FOUNDS) val notFounds: Int,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_RATINGS) val ratings: Int,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_DESCRIPTION) val description: String,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_HINT) val hint: String,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_RECOMMENDATIONS) val recommendations: Int?,
)

enum class GeocacheType {
    Traditional, Multi, Quiz, Moving, Virtual, Other, Event
}

@Serializable
enum class GeocacheStatus{
    @SerialName(GEOCACHE_API_STATUS_AVAILABLE) AVAILABLE(),
    @SerialName(GEOCACHE_API_STATUS_TEMPORARILY_UNAVAILABLE) TEMPORARILY_UNAVAILABLE(),
    @SerialName(GEOCACHE_API_STATUS_ARCHIVED) ARCHIVED()
}

@Serializable
data class GeocacheUser(
    @SerialName(GEOCACHE_API_USER_UUID) val uuid: String,
    @SerialName(GEOCACHE_API_USER_USERNAME) val username: String,
    @SerialName(GEOCACHE_API_USER_USER_PROFILE) val url: String,
)
