package com.shhatrat.loggerek.repository

import com.russhwolf.settings.Settings

class RepositoryImpl(settings: Settings) : Repository {
    override val token: RepositoryItem<String?> = StringSettingsDelegate(settings, "Token")
    override val tokenSecret: RepositoryItem<String?> =
        StringSettingsDelegate(settings, "TokenSecret")
    override val savePassword: RepositoryItem<Boolean> =
        BooleanSettingsDelegate(settings, "savePassword", true)
    override val tryMixedPassword: RepositoryItem<Boolean> =
        BooleanSettingsDelegate(settings, "tryMixedPassword", true)
    override val garminIdentifier: RepositoryItem<Long?> =
        LongSettingsDelegate(settings, "garminIdentifier", null)
}