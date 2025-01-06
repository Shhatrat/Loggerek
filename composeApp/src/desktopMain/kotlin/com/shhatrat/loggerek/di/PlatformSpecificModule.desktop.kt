package com.shhatrat.loggerek.di

import org.koin.core.module.Module

actual class PlatformSpecificModule actual constructor() : PlatformSpecific {
    override fun getModules(): List<Module> = listOf()
}