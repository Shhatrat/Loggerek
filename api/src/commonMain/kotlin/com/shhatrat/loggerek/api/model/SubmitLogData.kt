package com.shhatrat.loggerek.api.model

data class SubmitLogData(
    val cacheId: String,
    val logType: LogType,
    val comment: String,
    val password: String?,
    val date: String?,
    val rating: Int?,
    val reccomend: Boolean,
)
