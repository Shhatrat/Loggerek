package com.shhatrat.loggerek.repository

class FakeRepositoryImpl : Repository {

    private val fakeStorage = mutableMapOf<String, Any?>()

    override val token: RepositoryItem<String?> = FakeRepositoryItem(fakeStorage, "Token")

    override val tokenSecret: RepositoryItem<String?> =
        FakeRepositoryItem(fakeStorage, "TokenSecret")

    override val savePassword: RepositoryItem<Boolean> =
        FakeRepositoryItem(fakeStorage, "savePassword")

    override val tryMixedPassword: RepositoryItem<Boolean> =
        FakeRepositoryItem(fakeStorage, "tryMixedPassword")

    override val garminIdentifier: RepositoryItem<Long?> =
        FakeRepositoryItem(fakeStorage, "garminIdentifier")

    private class FakeRepositoryItem<T>(
        private val storage: MutableMap<String, Any?>,
        private val key: String
    ) : RepositoryItem<T> {

        override fun save(obj: T) {
            storage[key] = obj
        }

        override fun get(): T {
            return storage[key] as T
        }

        override fun remove() {
            storage.remove(key)
        }
    }
}