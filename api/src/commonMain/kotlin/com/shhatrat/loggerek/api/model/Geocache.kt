package com.shhatrat.loggerek.api.model

import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheStatus.Companion.GEOCACHE_API_STATUS_ARCHIVED
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheStatus.Companion.GEOCACHE_API_STATUS_AVAILABLE
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheStatus.Companion.GEOCACHE_API_STATUS_TEMPORARILY_UNAVAILABLE
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheUser.Companion.GEOCACHE_API_USER_USERNAME
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheUser.Companion.GEOCACHE_API_USER_USER_PROFILE
import com.shhatrat.loggerek.api.model.OpencachingParam.GeocacheUser.Companion.GEOCACHE_API_USER_UUID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import loggerek.api.generated.resources.Cache_event
import loggerek.api.generated.resources.Cache_moving
import loggerek.api.generated.resources.Cache_multi
import loggerek.api.generated.resources.Cache_quiz
import loggerek.api.generated.resources.Cache_traditional
import loggerek.api.generated.resources.Cache_unknown
import loggerek.api.generated.resources.Cache_virtual
import loggerek.api.generated.resources.Cache_webcam
import loggerek.api.generated.resources.Owncache
import loggerek.api.generated.resources.Res
import loggerek.api.generated.resources.log_comment
import loggerek.api.generated.resources.log_found
import loggerek.api.generated.resources.log_not_found
import loggerek.api.generated.resources.log_participated_in_event
import loggerek.api.generated.resources.log_will_participated_in_event
import org.jetbrains.compose.resources.DrawableResource
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
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_RATINGS) val ratings: Int?,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_DESCRIPTION) val description: String,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_HINT) val hint: String,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_RECOMMENDATIONS) val recommendations: Int?,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_MY_NOTES) val myNotes: String?,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_REQ_PASSWORD) val requirePassword: Boolean,
    @SerialName(OpencachingParam.Geocache.GEOCACHE_API_LATEST_LOGS) val userLogOnly: List<LogEntry>?,
)

@Serializable
data class LogEntry(val type: String, val user: LogUser)

@Serializable
data class LogUser(
    val uuid: String,
    val username: String,
    @SerialName("profile_url")
    val profileUrl: String
)

enum class GeocacheType(val iconRes: DrawableResource, val logType: LogOptions) {
    Traditional(Res.drawable.Cache_traditional,
            LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT))),
    Multi(Res.drawable.Cache_multi, LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT))),
    Quiz(Res.drawable.Cache_quiz, LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT))),
    Moving(Res.drawable.Cache_moving, LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT))),
    Virtual(Res.drawable.Cache_virtual, LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT))),
    Other(Res.drawable.Cache_unknown, LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT))),
    Own(Res.drawable.Owncache, LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT))),
    Webcam(Res.drawable.Cache_webcam, LogOptions(listOf(LogType.FOUND, LogType.NOT_FOUND, LogType.COMMENT))),
    Event(Res.drawable.Cache_event,
        LogOptions(
            listOf(
                LogType.PARTICIPATED_IN_EVENT,
                LogType.WILL_PARTICIPATE_IN_EVENT,
                LogType.COMMENT
            )
        )
    )
}

@Serializable
enum class LogType(
    @SerialName("apiKey") val apiKey: String,
    val textRes: StringResource,
    val canHasPassword: Boolean,
    val canRate: Boolean,
    val canBeRecommended: Boolean
) {
    @SerialName("Comment")
    COMMENT("Comment", Res.string.log_comment, false, false, false),

    @SerialName("Attended")
    PARTICIPATED_IN_EVENT("Attended", Res.string.log_participated_in_event, false, true, false),

    @SerialName("Will attend")
    WILL_PARTICIPATE_IN_EVENT(
        "Will attend",
        Res.string.log_will_participated_in_event,
        false,
        false,
        false
    ),

    @SerialName("Found it")
    FOUND("Found it", Res.string.log_found, true, true, true),

    @SerialName("Didn't find it")
    NOT_FOUND("Didn't find it", Res.string.log_not_found, false, false, false);
}

data class LogOptions(
    val logTypes: List<LogType>,
)


@Serializable
enum class GeocacheStatus {
    @SerialName(GEOCACHE_API_STATUS_AVAILABLE)
    AVAILABLE(),
    @SerialName(GEOCACHE_API_STATUS_TEMPORARILY_UNAVAILABLE)
    TEMPORARILY_UNAVAILABLE(),
    @SerialName(GEOCACHE_API_STATUS_ARCHIVED)
    ARCHIVED()
}

@Serializable
data class GeocacheUser(
    @SerialName(GEOCACHE_API_USER_UUID) val uuid: String,
    @SerialName(GEOCACHE_API_USER_USERNAME) val username: String,
    @SerialName(GEOCACHE_API_USER_USER_PROFILE) val url: String,
)
