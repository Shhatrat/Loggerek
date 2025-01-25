package com.shhatrat.loggerek.manager.log.di

import com.shhatrat.loggerek.manager.log.LogManager
import com.shhatrat.loggerek.manager.log.LogManagerImpl
import org.koin.dsl.module

val logManagerModule = module {
    single<LogManager> { LogManagerImpl(get(), get()) }
}

val fakeLogManagerModule = module {
    single<LogManager> { LogManagerImpl(get(), get()) }
}