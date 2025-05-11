package com.shhatrat.loggerek.base.cache

import com.shhatrat.loggerek.api.model.LogType

data class FieldNote(
    val code: String,
    val dateTime: String,
    val logType: LogType,
    val note: String
)
