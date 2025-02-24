package com.shhatrat.loggerek.manager.watch

import com.shhatrat.loggerek.di.PlatformSpecific
import com.shhatrat.loggerek.manager.watch.config.ConfigManager
import com.shhatrat.loggerek.manager.watch.logic.WatchLogic
import com.shhatrat.loggerek.manager.watch.logic.WatchLogicImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual class WatchPlatformSpecificModule: PlatformSpecific {

    override fun getModules(): List<Module> {
        return listOf(module {
            single<GarminWatch> { GarminManager(get(), GarminInitType.REAL) }
            single<ConfigManager<GarminWatch.GarminDevice, GarminWatch.InstalledAppState>> { GarminConfigManager(get(), get()) }
            single<WatchLogic> { WatchLogicImpl(get(), get(), get(), get(), get()) }
        })
    }
}