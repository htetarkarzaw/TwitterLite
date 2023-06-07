package com.htetarkarzaw.twitterlite.utils

object InputCheckerUtil {
    private const val PASSWORD_PATTERN = "^.{8,20}$"
    private const val EMAIL_PATTERN = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    fun validatePassword(password: String): Boolean {
        return Regex(PASSWORD_PATTERN).matches(password)
    }

    fun isSamePassword(password1: String, password2: String): Boolean {
        return password1 == password2
    }

    fun validateEmailAddress(email: String): Boolean {
        return Regex(EMAIL_PATTERN).matches(email)
    }
}