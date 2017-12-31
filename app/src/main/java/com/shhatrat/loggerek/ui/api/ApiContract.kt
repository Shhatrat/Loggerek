package com.shhatrat.loggerek.ui.api

import com.shhatrat.loggerek.data.model.Person
import com.shhatrat.loggerek.di.baseUi.MvpView
import com.shhatrat.loggerek.di.baseUi.Presenter

/**
 * Created by szymon on 25/12/17.
 */
interface ApiContract{
    interface IView: MvpView{
        fun showData(person: Person?)
    }
    interface IPresenter<in T: MvpView>: Presenter<T> {
        fun getPerson()
    }
}