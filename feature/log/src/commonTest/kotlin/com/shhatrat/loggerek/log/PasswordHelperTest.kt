package com.shhatrat.loggerek.log

import kotlin.test.Test
import kotlin.test.assertContentEquals

class PasswordHelperTest {

    @Test
    fun checkPasswordAlternatives() {
        val d = PasswordHelper.getAlternatives("Jędrzej Źłotowski")
        val expected = listOf(
            "Jędrzej Źłotowski",
            "Jedrzej Zlotowski",
            "JędrzejŹłotowski",
            "JedrzejZlotowski",
            "JĘDRZEJ ŹŁOTOWSKI",
            "JEDRZEJ ZLOTOWSKI",
            "jędrzej źłotowski",
            "jedrzej zlotowski"
        )
        assertContentEquals(expected, d)
    }
}