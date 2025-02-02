package com.shhatrat.loggerek.log

import com.shhatrat.loggerek.api.model.GeocacheMock
import com.shhatrat.loggerek.api.model.GeocacheType
import com.shhatrat.loggerek.base.testing.TestItem
import com.shhatrat.loggerek.base.testing.getTestItems
import com.shhatrat.loggerek.search.SearchScreen
import com.shhatrat.loggerek.search.SearchUiState

class SearchScreenshotTestSource {

    fun searchWithItems(): List<TestItem> {
        return getTestItems(
            { deviceScreen ->
                SearchScreen(
                    calculateWindowSizeClass = { deviceScreen.getWindowSizeClass() },
                    searchUiState = SearchUiState(
                        caches = GeocacheType.entries.map { GeocacheMock().getByType(it) }
                    )
                )
            }, "searchWithItems"
        )
    }
}