package com.shhatrat.loggerek.di

import com.shhatrat.loggerek.intro.splash.IntroViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (navigateToMain: () -> Unit, navigateToAuth: () -> Unit) ->
        IntroViewModel(navigateToMain, navigateToAuth, get())
    }
}