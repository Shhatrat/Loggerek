package com.shhatrat.loggerek.di

import com.shhatrat.loggerek.di.modules.ManagerModule
import com.shhatrat.loggerek.di.modules.MvpModule
import com.shhatrat.loggerek.di.modules.WearModule
import org.koin.android.module.AndroidModule

/**
 * Created by szymon on 23/12/17.
 */
object Modules{
    fun get() = listOf<AndroidModule>(MvpModule(), ManagerModule(), WearModule())
}