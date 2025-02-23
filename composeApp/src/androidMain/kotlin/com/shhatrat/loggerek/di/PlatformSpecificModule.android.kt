package com.shhatrat.loggerek.di

import com.shhatrat.base.BaseData
import com.shhatrat.loggerek.MainActivity
import org.koin.core.module.Module
import org.koin.dsl.module

actual class PlatformSpecificModule actual constructor() : PlatformSpecific {
    override fun getModules(): List<Module> = listOf(
        module {
            single<BaseData> {
                object : BaseData {
                    override fun getMainActivity() = MainActivity::class.java
                }
            }
        }
    )
}