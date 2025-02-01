package com.shhatrat.loggerek.repository

import com.russhwolf.settings.Settings

class StringSettingsDelegate(
    private val settings: Settings,
    private val key: String
) : RepositoryItem<String?> {

    override fun save(obj: String?) {
        obj?.let { settings.putString(key, it) }
    }

    override fun get(): String? {
        return settings.getStringOrNull(key)
    }

    override fun remove() {
        settings.remove(key)
    }
}
