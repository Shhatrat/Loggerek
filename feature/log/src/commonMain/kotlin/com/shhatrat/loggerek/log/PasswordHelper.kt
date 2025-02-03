package com.shhatrat.loggerek.log

object PasswordHelper {

    fun getAlternatives(password: String): List<String> {
        return listOf(password,
            password.replace(" ", ""),
            password.uppercase(),
            password.lowercase(),
            password.capitalize(),
            password.split(" ")
                .joinToString(separator = " ", prefix = "", postfix = "") { it.capitalize() }
        ).map { listOf(it, removeDiacritics(it)) }.flatten().distinct()
    }

    private fun removeDiacritics(input: String): String {
        val replacements = mapOf(
            'ą' to 'a', 'ć' to 'c', 'ę' to 'e', 'ł' to 'l', 'ń' to 'n',
            'ó' to 'o', 'ś' to 's', 'ź' to 'z', 'ż' to 'z',
            'Ą' to 'A', 'Ć' to 'C', 'Ę' to 'E', 'Ł' to 'L', 'Ń' to 'N',
            'Ó' to 'O', 'Ś' to 'S', 'Ź' to 'Z', 'Ż' to 'Z'
        )
        return input.map { replacements[it] ?: it }.joinToString("")
    }
}