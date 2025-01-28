package com.shhatrat.loggerek.di

import org.koin.core.module.Module

interface PlatformSpecific {
    fun getModules(): List<Module>
}