package com.shhatrat.loggerek.api.di

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO

actual class HttpClientFactoryProvider {
    actual fun provide(): HttpClientEngineFactory<*>? = CIO
}