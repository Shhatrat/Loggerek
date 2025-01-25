package com.shhatrat.loggerek.manager.log

import com.shhatrat.loggerek.api.model.Geocache


interface LogManager {

    suspend fun getCache(id: String): Geocache
}