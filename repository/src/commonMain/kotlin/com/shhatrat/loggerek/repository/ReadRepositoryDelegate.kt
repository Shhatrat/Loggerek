package com.shhatrat.loggerek.repository

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ReadRepositoryDelegate<T>(private val repositoryItem: RepositoryItem<T>): ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return repositoryItem.get()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        repositoryItem.save(value)
    }

}
