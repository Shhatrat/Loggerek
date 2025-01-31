package com.shhatrat.loggerek.base.testing

data class SuspendFunctionHelper(
    var delay: Long = 0,
    var doAfterDelayBeforeLogic: suspend () -> Unit = {},
    var isInvoked: Boolean = false
)

data class FunctionHelper(
    var doAfterDelayBeforeLogic: () -> Unit = {},
    var isInvoked: Boolean = false
)