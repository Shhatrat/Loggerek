package com.shhatrat.loggerek.api.model

fun Geocache.isFound() = userLogOnly?.any { it.type == "Found it" } == true
