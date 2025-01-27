package com.shhatrat.loggerek.api.model

import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheStatus.Companion.GEOCACHE_API_STATUS_ARCHIVED
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheStatus.Companion.GEOCACHE_API_STATUS_AVAILABLE
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheStatus.Companion.GEOCACHE_API_STATUS_TEMPORARILY_UNAVAILABLE
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheUser.Companion.GEOCACHE_API_USER_USERNAME
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheUser.Companion.GEOCACHE_API_USER_USER_PROFILE
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheUser.Companion.GEOCACHE_API_USER_UUID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import loggerek.api.generated.resources.Res
import loggerek.api.generated.resources.aaaa
import loggerek.api.generated.resources.log_comment
import loggerek.api.generated.resources.log_found
import loggerek.api.generated.resources.log_need_service
import loggerek.api.generated.resources.log_not_found
import loggerek.api.generated.resources.log_participated_in_event
import loggerek.api.generated.resources.log_service_done
import loggerek.api.generated.resources.log_will_participated_in_event
import org.jetbrains.compose.resources.StringResource

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
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_MY_NOTES) val myNotes: String?,
)

enum class GeocacheType(val logType: LogOptions) {
    Traditional(LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT , LogType.NEED_SERVICE, LogType.SERVICE_DONE))),
    Multi(LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT , LogType.NEED_SERVICE, LogType.SERVICE_DONE))),
    Quiz(LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT , LogType.NEED_SERVICE, LogType.SERVICE_DONE))),
    Moving(LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT , LogType.NEED_SERVICE, LogType.SERVICE_DONE))),
    Virtual(LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT , LogType.NEED_SERVICE, LogType.SERVICE_DONE))),
    Other(LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT , LogType.NEED_SERVICE, LogType.SERVICE_DONE))),
    Event(LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT , LogType.NEED_SERVICE, LogType.SERVICE_DONE)))
}

enum class LogType(
    val textRes: StringResource,
    val canHasPassword: Boolean,
    val canRate: Boolean,
    val canBeRecommended: Boolean) {
    COMMENT(Res.string.log_comment, false, false, false),
    SERVICE_DONE(Res.string.log_service_done, false, false, false),
    NEED_SERVICE(Res.string.log_need_service, false, false, false),
    PARTICIPATED_IN_EVENT(Res.string.log_participated_in_event, false, true, false),
    WILL_PARTICIPATE_IN_EVENT(Res.string.log_will_participated_in_event, false, false, false),
    FOUND(Res.string.log_found, true, true, true),
    NOT_FOUND(Res.string.log_not_found, false, false, false);
}

data class LogOptions(
    val logTypes: List<LogType>,
)


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
