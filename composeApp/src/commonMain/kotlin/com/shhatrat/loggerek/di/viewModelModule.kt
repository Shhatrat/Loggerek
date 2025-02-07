package com.shhatrat.loggerek.di

import com.shhatrat.loggerek.base.MoveToIntro
import com.shhatrat.loggerek.base.MoveToLogCache
import com.shhatrat.loggerek.base.MoveToWatch
import com.shhatrat.loggerek.base.di.LogScope
import com.shhatrat.loggerek.intro.authorizate.AuthViewModel
import com.shhatrat.loggerek.intro.splash.IntroViewModel
import com.shhatrat.loggerek.log.LogViewModel
import com.shhatrat.loggerek.main.MainViewModel
import com.shhatrat.loggerek.search.SearchViewModel
import com.shhatrat.loggerek.settings.ProfileViewModel
import com.shhatrat.loggerek.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (navigateToMain: () -> Unit,
                    navigateToAuth: () -> Unit) ->
        IntroViewModel(navigateToMain, navigateToAuth, get())
    }
    viewModel { (navigateToMain: () -> Unit) -> AuthViewModel(navigateToMain, get(), get()) }
    viewModel { (navigateToIntro: () -> Unit) -> MainViewModel(navigateToIntro) }
    viewModel { ProfileViewModel(get()) }
    viewModel { (moveToIntro: MoveToIntro, moveToWatch: MoveToWatch) -> SettingsViewModel(moveToIntro, moveToWatch, get(), getOrNull()) }
    scope(named(LogScope)) {
        scoped { (cacheId: String) -> LogViewModel(cacheId, get(), get(), get()) }
    }
    viewModel { (moveToLogCache: MoveToLogCache) -> SearchViewModel(moveToLogCache, get()) }
}