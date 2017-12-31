package com.shhatrat.loggerek.di.modules

import com.shhatrat.loggerek.ui.base.adapter.simple.SimpleListAdapter
import org.koin.android.module.AndroidModule

/**
 * Created by szymon on 25/12/17.
 */
class AdapterModule: AndroidModule() {
    override fun context() = applicationContext {
        provide { SimpleListAdapter() }
    }
}