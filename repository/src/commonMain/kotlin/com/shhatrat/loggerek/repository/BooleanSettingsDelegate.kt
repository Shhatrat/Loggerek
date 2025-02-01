package com.shhatrat.loggerek.repository

import com.russhwolf.settings.Settings

class BooleanSettingsDelegate(
    private val settings: Settings,
    private val key: String,
    private val defaultValue: Boolean
) : RepositoryItem<Boolean> {

    override fun save(obj: Boolean) {
        settings.putBoolean(key, obj)
    }

    override fun get(): Boolean {
        return settings.getBoolean(key, defaultValue)
    }

    override fun remove() {
        settings.remove(key)
    }
}
