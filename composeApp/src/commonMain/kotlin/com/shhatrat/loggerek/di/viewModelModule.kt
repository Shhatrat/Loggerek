package com.shhatrat.loggerek.di

import com.shhatrat.loggerek.base.MoveToLogCache
import com.shhatrat.loggerek.base.di.LogScope
import com.shhatrat.loggerek.intro.authorizate.AuthViewModel
import com.shhatrat.loggerek.intro.splash.IntroViewModel
import com.shhatrat.loggerek.log.LogViewModel
import com.shhatrat.loggerek.main.MainViewModel
import com.shhatrat.loggerek.settings.ProfileViewModel
import com.shhatrat.loggerek.search.SearchViewModel
import com.shhatrat.loggerek.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (navigateToMain: () -> Unit,
                    navigateToAuth: () -> Unit) ->
        IntroViewModel(navigateToMain, navigateToAuth, get())
    }
    viewModel { (navigateToMain: () -> Unit) -> AuthViewModel(navigateToMain, get()) }
    viewModel { (navigateToIntro: () -> Unit) -> MainViewModel(navigateToIntro) }
    viewModel { ProfileViewModel(get()) }
    viewModel { (navigateToMain: () -> Unit) -> SettingsViewModel(navigateToMain, get()) }
    scope(named(LogScope)) {
        scoped { (cacheId: String) -> LogViewModel(cacheId, get()) }
    }
    viewModel { (moveToLogCache: MoveToLogCache) -> SearchViewModel(moveToLogCache, get()) }
}