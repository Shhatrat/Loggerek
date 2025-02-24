package com.shhatrat.loggerek.di

import com.shhatrat.loggerek.account.di.accountModule
import com.shhatrat.loggerek.api.di.apiModule
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.browser.BrowserPlatformSpecificModule
import com.shhatrat.loggerek.manager.log.di.logManagerModule
import com.shhatrat.loggerek.manager.watch.WatchPlatformSpecificModule
import com.shhatrat.loggerek.repository.di.repositoryModule
import org.koin.core.KoinApplication
import org.koin.dsl.module

object KoinHelper {

    fun KoinApplication.setupBaseModules() {
        modules(
            repositoryModule,
            apiModule,
            accountModule,
            logManagerModule,
            viewModelModule,
        ).modules(
            PlatformSpecificModule().getModules().plus(
                BrowserPlatformSpecificModule().getModules()
            ).plus(
                WatchPlatformSpecificModule().getModules()
            )
        )
    }

    fun KoinApplication.setupWindowSizeModules(calculateWindowSizeClass: WindowSizeCallback) {
        modules(module {
            single<WindowSizeCallback> {
                { calculateWindowSizeClass() }
            }
        })
    }
}