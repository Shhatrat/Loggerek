package com.shhatrat.loggerek.repository.di

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import com.shhatrat.loggerek.di.PlatformSpecific
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.prefs.Preferences

actual class PlatformSpecificModule: PlatformSpecific {
    override fun getModules(): List<Module> {
        return listOf(module {
            single<Settings> { PreferencesSettings(delegate = Preferences.userRoot()) }
        })
    }
}
