package com.shhatrat.loggerek.repository

class FakeRepository : Repository {

    private val fakeStorage = mutableMapOf<String, String?>()

    override val token: RepositoryItem<String> = FakeRepositoryItem(fakeStorage, "Token")
    override val tokenSecret: RepositoryItem<String> = FakeRepositoryItem(fakeStorage, "TokenSecret")

    private class FakeRepositoryItem(
        private val storage: MutableMap<String, String?>,
        private val key: String
    ) : RepositoryItem<String> {

        override fun save(obj: String) {
            storage[key] = obj
        }

        override fun get(): String? {
            return storage[key]
        }

        override fun remove() {
            storage.remove(key)
        }
    }
}