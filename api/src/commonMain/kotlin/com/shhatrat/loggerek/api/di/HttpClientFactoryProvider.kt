package com.shhatrat.loggerek.api.di

import io.ktor.client.engine.HttpClientEngineFactory

expect class HttpClientFactoryProvider() {
    fun provide(): HttpClientEngineFactory<*>?
}
