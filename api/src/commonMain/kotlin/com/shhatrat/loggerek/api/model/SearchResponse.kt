package com.shhatrat.loggerek.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("results") val results: List<String>,
    @SerialName("more") val more: Boolean
)