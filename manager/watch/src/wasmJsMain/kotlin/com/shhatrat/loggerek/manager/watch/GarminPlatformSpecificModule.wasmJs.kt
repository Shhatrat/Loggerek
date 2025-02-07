package com.shhatrat.loggerek.manager.watch

import com.shhatrat.loggerek.di.PlatformSpecific
import org.koin.core.module.Module

actual class WatchPlatformSpecificModule : PlatformSpecific{

    override fun getModules(): List<Module> = listOf()
}