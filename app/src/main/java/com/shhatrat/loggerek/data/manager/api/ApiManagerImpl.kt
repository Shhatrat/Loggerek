package com.shhatrat.loggerek.data.manager.api

import com.shhatrat.loggerek.data.api.ApiService

/**
 * Created by szymon on 25/12/17.
 */
class ApiManagerImpl(private val apiService: ApiService): ApiManager{

    override fun getPerson() = apiService.getPerson().map { it.body() }
}