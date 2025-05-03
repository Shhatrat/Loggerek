package com.shhatrat.loggerek.api.di

import io.ktor.client.engine.HttpClientEngineFactory

actual class HttpClientFactoryProvider {
    actual fun provide(): HttpClientEngineFactory<*>? = null
}
