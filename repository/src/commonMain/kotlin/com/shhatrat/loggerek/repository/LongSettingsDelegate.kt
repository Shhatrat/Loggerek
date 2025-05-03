package com.shhatrat.loggerek.repository

import com.russhwolf.settings.Settings

class LongSettingsDelegate(
private val settings: Settings,
private val key: String,
private val defaultValue: Long?
) : RepositoryItem<Long?> {

    override fun save(obj: Long?) {
        obj?.let { settings.putLong(key, it) }
    }

    override fun get(): Long? {
        return settings.getLongOrNull(key)?:defaultValue
    }

    override fun remove() {
        settings.remove(key)
    }
}