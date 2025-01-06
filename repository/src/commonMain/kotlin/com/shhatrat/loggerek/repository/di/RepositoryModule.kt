package com.shhatrat.loggerek.repository.di

import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    includes(PlatformSpecificModule().getModules())
}