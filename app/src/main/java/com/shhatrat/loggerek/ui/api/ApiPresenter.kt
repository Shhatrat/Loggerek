package com.shhatrat.loggerek.ui.api

import com.shhatrat.loggerek.data.manager.api.ApiManager
import com.shhatrat.loggerek.di.baseUi.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

/**
 * Created by szymon on 25/12/17.
 */
class ApiPresenter
constructor(private val apiManager: ApiManager): BasePresenter<ApiContract.IView>(), ApiContract.IPresenter<ApiContract.IView>{

    override fun getPerson() {
        apiManager
                .getPerson()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({view.showData(it)}
                , {}).addTo(subscriptions)
    }

}