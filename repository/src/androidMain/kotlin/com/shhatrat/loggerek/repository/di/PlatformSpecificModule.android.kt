package com.shhatrat.loggerek.repository.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import com.shhatrat.loggerek.di.PlatformSpecific
import org.koin.core.module.Module
import org.koin.dsl.module

actual class PlatformSpecificModule : PlatformSpecific {
    override fun getModules(): List<Module> {
        return listOf(
            module {
                single<Settings> {
                    SharedPreferencesSettings(
                        get<Context>().getSharedPreferences(
                            "PowerTrailAppPreferences",
                            Context.MODE_PRIVATE
                        )
                    )
                }

            }
        )
    }
}