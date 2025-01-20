package com.shhatrat.loggerek.repository.di

import com.shhatrat.loggerek.repository.FakeRepositoryImpl
import com.shhatrat.loggerek.repository.Repository
import com.shhatrat.loggerek.repository.RepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    includes(PlatformSpecificModule().getModules())
    single<Repository> { RepositoryImpl(get()) }
}

val fakeRepositoryModule: Module = module {
    single<Repository> { FakeRepositoryImpl() }
}