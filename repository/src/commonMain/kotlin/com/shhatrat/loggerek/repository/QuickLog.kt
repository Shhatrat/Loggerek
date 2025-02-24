package com.shhatrat.loggerek.repository

import kotlinx.serialization.Serializable

@Serializable
data class QuickLog(val id: String, val comment: String, val type: String)