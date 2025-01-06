package com.shhatrat.loggerek.api.di

import com.shhatrat.loggerek.api.Api
import com.shhatrat.loggerek.api.ApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import org.koin.dsl.module

val apiModule = module {

    single<HttpClient> {
        val engine: HttpClientEngineFactory<*>? = HttpClientFactoryProvider().provide()
        (engine?.let {
            HttpClient(it) {
                setupLogger()
            }
        } ?: HttpClient{ setupLogger() })
    }
    single<Api> { ApiImpl(get()) }
}

private fun HttpClientConfig<*>.setupLogger() {
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
        level = LogLevel.ALL
    }
}
