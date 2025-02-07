package com.shhatrat.loggerek.manager.watch

import com.shhatrat.loggerek.di.PlatformSpecific
import org.koin.core.module.Module
import org.koin.dsl.module

actual class WatchPlatformSpecificModule: PlatformSpecific {

    override fun getModules(): List<Module> {
        return listOf(module { single { GarminManager(get()) } })
    }
}