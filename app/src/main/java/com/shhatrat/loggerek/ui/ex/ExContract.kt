package com.shhatrat.loggerek.ui.ex

import com.shhatrat.loggerek.di.baseUi.MvpView
import com.shhatrat.loggerek.di.baseUi.Presenter

/**
 * Created by szymon on 23/12/17.
 */
interface ExContract{
    interface IView: MvpView{
        fun showTime(time: String)
    }
    interface IPresenter<in T:MvpView>: Presenter<T>{
        fun getTime()
    }
}