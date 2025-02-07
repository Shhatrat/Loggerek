package com.shhatrat.loggerek.base.browser

import com.shhatrat.loggerek.di.PlatformSpecific
import org.koin.core.module.Module
import org.koin.dsl.module

actual class BrowserPlatformSpecificModule actual constructor() : PlatformSpecific {
    override fun getModules(): List<Module> {
        return listOf(module { single<IBrowserUtil> { BrowserUtil() } })
    }
}