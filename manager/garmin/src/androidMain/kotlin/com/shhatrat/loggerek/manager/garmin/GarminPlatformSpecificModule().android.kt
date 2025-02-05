package com.shhatrat.loggerek.manager.garmin

import com.shhatrat.loggerek.di.PlatformSpecific
import org.koin.core.module.Module
import org.koin.dsl.module

actual class GarminPlatformSpecificModule: PlatformSpecific {

    override fun getModules(): List<Module> {
        return listOf(module { single { GarminManager(get()) } })
    }

}