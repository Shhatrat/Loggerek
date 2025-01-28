package com.shhatrat.loggerek.di

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings
import org.koin.core.module.Module
import org.koin.dsl.module

actual class PlatformSpecificModule : PlatformSpecific {
    override fun getModules(): List<Module> {
        return listOf(module { single<Settings> { StorageSettings() } })
    }
}