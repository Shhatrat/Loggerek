package com.shhatrat.loggerek

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.shhatrat.base.NotificationHelper.createChannel
import com.shhatrat.loggerek.di.KoinHelper.setupBaseModules
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class AndroidApp: Application(), DefaultLifecycleObserver {

    companion object{
        private val _appState = MutableStateFlow(false)
        val appState: StateFlow<Boolean> = _appState.asStateFlow()
    }

    override fun onCreate() {
        super<Application>.onCreate()
        startKoin {
            setupBaseModules()
            this.androidContext(applicationContext)
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        createChannel()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        _appState.value = true
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        _appState.value = false
    }
}