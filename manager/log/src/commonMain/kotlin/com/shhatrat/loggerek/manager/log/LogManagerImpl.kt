package com.shhatrat.loggerek.manager.log

import com.shhatrat.loggerek.api.Api
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.LogResponse
import com.shhatrat.loggerek.api.model.LogType
import com.shhatrat.loggerek.api.model.LogTypeResponse
import com.shhatrat.loggerek.api.model.SubmitLogData
import com.shhatrat.loggerek.repository.Repository

class LogManagerImpl(private val api: Api, private val repository: Repository) : LogManager {

    override suspend fun getCache(id: String): Geocache {
        val t = repository.safeTokenAndTokenSecret()
        return api.getFullCache(id, t.token, t.tokenSecret)
    }

    override suspend fun saveNote(id: String, note: String, oldValue: String) {
        val t = repository.safeTokenAndTokenSecret()
        api.saveNote(id, t.token, t.tokenSecret, note, oldValue)
    }

    override suspend fun logCapabilities(id: String): List<LogType> {
        val t = repository.safeTokenAndTokenSecret()
        val re: LogTypeResponse = api.logCapabilities(id, t.token, t.tokenSecret)
        return re.submittableLogTypes
    }

    override suspend fun submitLog(logData: SubmitLogData): LogResponse {
        val t = repository.safeTokenAndTokenSecret()
        return api.submitLog(logData, t.token, t.tokenSecret)
    }

    override suspend fun getCachesByCodes(codes: List<String>): List<Geocache> {
        val t = repository.safeTokenAndTokenSecret()
        return api.geocaches(codes, t.token, t.tokenSecret)
    }

    override suspend fun searchByName(name: String): List<Geocache> {
        val t = repository.safeTokenAndTokenSecret()
        val res = api.searchByName("*${name.replace(" ", "*")}*", t.token, t.tokenSecret)
        return api.geocaches(res.results, t.token, t.tokenSecret)
    }
}