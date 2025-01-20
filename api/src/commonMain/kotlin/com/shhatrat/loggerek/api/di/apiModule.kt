package com.shhatrat.loggerek.api.di

import com.shhatrat.loggerek.api.Api
import com.shhatrat.loggerek.api.ApiImpl
import com.shhatrat.loggerek.api.FakeApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
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

val fakeApiModule = module {
    single<Api> { FakeApiImpl() }
}

private fun HttpClientConfig<*>.setupLogger() {
    install(ContentNegotiation) {
        json()
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
        level = LogLevel.ALL
    }
}
