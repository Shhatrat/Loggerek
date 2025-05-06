package com.shhatrat.loggerek.api.model

import kotlinx.serialization.SerialName

data class SubmitLogData(
    val cacheId: String,
    val logType: LogType,
    val comment: String,
    val password: String?,
    val date: String?,
    val rating: Int?,
    val reccomend: Boolean,
)
