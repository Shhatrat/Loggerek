package com.shhatrat.loggerek.account.di

import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.account.AccountManagerImpl
import com.shhatrat.loggerek.account.FakeAccountManagerImpl
import org.koin.dsl.module

val accountModule = module {
    single<AccountManager> { AccountManagerImpl(get(), get()) }
}

val fakeAccountModule = module {
    single<AccountManager> { FakeAccountManagerImpl() }
}