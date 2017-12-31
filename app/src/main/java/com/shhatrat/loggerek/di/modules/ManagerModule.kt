package com.shhatrat.loggerek.di.modules

import com.shhatrat.loggerek.data.manager.api.ApiManager
import com.shhatrat.loggerek.data.manager.api.ApiManagerImpl
import com.shhatrat.loggerek.data.manager.data.DataManager
import com.shhatrat.loggerek.data.manager.data.DataManagerFake
import com.shhatrat.loggerek.data.manager.data.DataManagerImpl
import org.koin.android.module.AndroidModule

/**
 * Created by szymon on 24/12/17.
 */
class ManagerModule: AndroidModule(){
    override fun context() = applicationContext {
            provide(REAL) { DataManagerImpl() } bind DataManager::class
            provide(FAKE) { DataManagerFake() } bind DataManager::class

            provide { ApiManagerImpl(get()) } bind ApiManager::class
    }

    companion object {
        val REAL = "REAL"
        val FAKE = "FAKE"
    }
}