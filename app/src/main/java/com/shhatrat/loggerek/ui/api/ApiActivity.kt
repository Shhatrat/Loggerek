package com.shhatrat.loggerek.ui.api

import android.os.Bundle
import com.shhatrat.loggerek.R
import com.shhatrat.loggerek.data.model.Person
import com.shhatrat.loggerek.di.baseUi.android.BaseActivity
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class ApiActivity : BaseActivity(), ApiContract.IView {


    override fun showData(person: Person?) {
        toast("${person?.name} ${person?.idCard}")
    }

    private val presenter by inject<ApiContract.IPresenter<ApiContract.IView>>()

    override fun attachPresenter() {
        presenter.attachView(this)
    }

    override fun detachPresenter() {
        presenter.detachView()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        presenter.getPerson()
    }
}
