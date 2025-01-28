package com.shhatrat.loggerek.repository

import com.russhwolf.settings.Settings

class RepositoryImpl(settings: Settings) : Repository {
    override val token: RepositoryItem<String> = StringSettingsDelegate(settings, "Token")
    override val tokenSecret: RepositoryItem<String> =
        StringSettingsDelegate(settings, "TokenSecret")
}