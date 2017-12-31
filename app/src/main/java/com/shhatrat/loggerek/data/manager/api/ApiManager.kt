package com.shhatrat.loggerek.data.manager.api

import com.shhatrat.loggerek.data.model.Person
import io.reactivex.Observable

/**
 * Created by szymon on 25/12/17.
 */
interface ApiManager{
    fun getPerson(): Observable<Person?>
}