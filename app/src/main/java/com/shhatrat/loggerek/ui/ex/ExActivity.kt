package com.shhatrat.loggerek.ui.ex

import android.os.Bundle
import com.shhatrat.loggerek.R
import com.shhatrat.loggerek.di.baseUi.android.BaseActivity
import com.shhatrat.loggerek.di.modules.ManagerModule
import kotlinx.android.synthetic.main.activity_ex.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class ExActivity : BaseActivity(), ExContract.IView {

    companion object {
        var INJECT_NAME = ManagerModule.FAKE
    }

    private val presenter by inject<ExContract.IPresenter<ExContract.IView>>()

    override fun attachPresenter() {
        presenter.attachView(this)
    }

    override fun detachPresenter() {
        presenter.detachView()
    }

    override fun showTime(time: String) {
        toast(time)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex)
        button2.setOnClickListener { presenter.getTime() }
    }
}