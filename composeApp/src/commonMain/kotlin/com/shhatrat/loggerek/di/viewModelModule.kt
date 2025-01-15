package com.shhatrat.loggerek.di

import com.shhatrat.loggerek.intro.authorizate.AuthViewModel
import com.shhatrat.loggerek.intro.splash.IntroViewModel
import com.shhatrat.loggerek.main.MainViewModel
import com.shhatrat.loggerek.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (navigateToMain: () -> Unit,
                    navigateToAuth: () -> Unit) ->
        IntroViewModel(navigateToMain, navigateToAuth, get())
    }
    viewModel { (navigateToMain: () -> Unit) -> AuthViewModel(navigateToMain, get()) }
    viewModel { (navigateToIntro: () -> Unit) -> MainViewModel(navigateToIntro, get(), get()) }
    viewModel { ProfileViewModel(get()) }
}