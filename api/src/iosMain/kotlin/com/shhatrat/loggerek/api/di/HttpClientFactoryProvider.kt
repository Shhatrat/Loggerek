package com.shhatrat.loggerek.api.di

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

actual class HttpClientFactoryProvider {
    actual fun provide(): HttpClientEngineFactory<*>? = Darwin
}