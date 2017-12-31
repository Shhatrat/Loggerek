package com.shhatrat.loggerek.di.modules

import com.shhatrat.loggerek.data.repository.UserRepositiory
import com.shhatrat.loggerek.ui.api.ApiContract
import com.shhatrat.loggerek.ui.api.ApiPresenter
import com.shhatrat.loggerek.ui.ex.ExActivity
import com.shhatrat.loggerek.ui.ex.ExContract
import com.shhatrat.loggerek.ui.ex.ExPresenter
import com.shhatrat.loggerek.ui.wear.WearContract
import com.shhatrat.loggerek.ui.wear.WearPresenter
import org.koin.android.module.AndroidModule

/**
 * Created by szymon on 24/12/17.
 */
class MvpModule: AndroidModule(){
    override fun context() = applicationContext {
        provide { getRepo() }
        provide { ExPresenter(get(ExActivity.INJECT_NAME), get()) } bind ExContract.IPresenter::class
        provide { WearPresenter(get()) } bind WearContract.IPresenter::class
        provide { ApiPresenter(get()) } bind ApiContract.IPresenter::class

    }

    fun getRepo(): UserRepositiory {
        return UserRepositiory()
    }
}