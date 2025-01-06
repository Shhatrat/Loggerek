package com.shhatrat.loggerek.repository.di

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import com.shhatrat.loggerek.di.PlatformSpecific
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual class PlatformSpecificModule: PlatformSpecific {
    override fun getModules(): List<Module> {
        return listOf(module {
            single<Settings> { NSUserDefaultsSettings(delegate = NSUserDefaults()) }
        })
    }
}
