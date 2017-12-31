package com.shhatrat.loggerek

import android.app.Application
import com.shhatrat.loggerek.di.Modules
import com.shhatrat.loggerek.util.base.Preferences
import org.koin.android.ext.android.startKoin

/**
 * Created by szymon on 31/12/17.
 */
class WearApp: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(this, Modules.get())
        Preferences.init(this)
    }
}