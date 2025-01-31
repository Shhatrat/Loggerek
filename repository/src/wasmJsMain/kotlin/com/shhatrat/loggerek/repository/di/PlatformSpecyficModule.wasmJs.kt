package com.shhatrat.loggerek.repository.di

import com.shhatrat.loggerek.di.PlatformSpecific
import org.koin.core.module.Module
import org.koin.dsl.module

actual class PlatformSpecificModule : PlatformSpecific {
    override fun getModules(): List<Module> {
        return listOf(module {
//            single<Settings> { StorageSettings() }
        })
    }
}

