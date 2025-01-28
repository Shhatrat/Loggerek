package com.shhatrat.loggerek.manager.log

import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.LogResponse
import com.shhatrat.loggerek.api.model.SubmitLogData


interface LogManager {

    suspend fun getCache(id: String): Geocache

    suspend fun saveNote(id: String, note: String, oldValue: String)

    suspend fun logCapabilities(id: String)

    suspend fun submitLog(logData: SubmitLogData): LogResponse
}