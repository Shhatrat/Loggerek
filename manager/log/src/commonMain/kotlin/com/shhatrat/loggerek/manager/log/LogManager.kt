package com.shhatrat.loggerek.manager.log

import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.LogResponse
import com.shhatrat.loggerek.api.model.LogType
import com.shhatrat.loggerek.api.model.SubmitLogData


interface LogManager {

    suspend fun getCache(id: String): Geocache

    suspend fun saveNote(id: String, note: String, oldValue: String)

    suspend fun logCapabilities(id: String): List<LogType>

    suspend fun submitLog(logData: SubmitLogData): LogResponse

    suspend fun searchByName(name: String): List<Geocache>
}