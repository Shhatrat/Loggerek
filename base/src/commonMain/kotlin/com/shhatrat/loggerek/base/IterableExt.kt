package com.shhatrat.loggerek.base

inline fun <T> Iterable<T>.mapIf(
    predicate: (T) -> Boolean,
    transform: (T) -> T
): List<T> = map { if (predicate(it)) transform(it) else it }