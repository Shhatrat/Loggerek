package com.shhatrat.loggerek.repository

import com.russhwolf.settings.Settings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LogsSettingsDelegate(
    private val settings: Settings,
    private val key: String
) : RepositoryItem<List<QuickLog>> {

    override fun save(obj: List<QuickLog>) {
        val json = Json.encodeToString(obj)
        settings.putString(key, json)
    }

    override fun get(): List<QuickLog> {
        val json = settings.getStringOrNull(key)
        return if(json==null)
            listOf()
        else{
            Json.decodeFromString<List<QuickLog>>(json)
        }
    }

    override fun remove() {
        settings.remove(key)
    }
}
