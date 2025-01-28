package com.shhatrat.loggerek.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("message") val message: String,
    @SerialName("log_uuid") val logUuid: String? = null,
    @SerialName("log_uuids") val logUuids: List<String> = emptyList()
)