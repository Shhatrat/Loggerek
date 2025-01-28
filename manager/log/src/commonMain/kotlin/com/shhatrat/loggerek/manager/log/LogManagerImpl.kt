package com.shhatrat.loggerek.manager.log

import com.shhatrat.loggerek.api.Api
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.repository.Repository

class LogManagerImpl(private val api: Api, private val repository: Repository): LogManager {

    override suspend fun getCache(id: String): Geocache {
        val t = repository.safeTokenAndTokenSecret()
        return api.getFullCache(id, t.token, t.tokenSecret)
     }

    override suspend fun saveNote(id: String, note: String, oldValue: String) {
        val t = repository.safeTokenAndTokenSecret()
        api.saveNote(id, t.token, t.tokenSecret, note, oldValue)
    }
}