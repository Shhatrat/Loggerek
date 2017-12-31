package com.shhatrat.loggerek.ui.wear

import com.shhatrat.boilerplate.shared_classess.model.Person
import com.shhatrat.loggerek.data.manager.wear.WearManager
import com.shhatrat.loggerek.di.baseUi.BasePresenter
import io.reactivex.rxkotlin.addTo

/**
 * Created by szymon on 27/12/17.
 */
class WearPresenter
constructor(private val wearManager: WearManager): BasePresenter<WearContract.IView>(), WearContract.IPresenter<WearContract.IView>{

    override fun sendMessage(person: Person) {
        wearManager
                .sendData(person)
                .subscribe({
                    it.dec()
                },{
                    it.message
                })
                .addTo(subscriptions)
    }

}