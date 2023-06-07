package com.htetarkarzaw.twitterlite.utils

import org.junit.Assert.*

import org.junit.Test

class InputCheckerUtilTest {

    @Test
    fun `validatePassword with 3 letters will return false`() {
        assertFalse(InputCheckerUtil.validatePassword("123"))
    }

    @Test
    fun `validatePassword with 10 letters will return true`() {
        assertTrue(InputCheckerUtil.validatePassword("12345678"))
    }

    @Test
    fun `validatePassword with 21 letters will return false`() {
        assertFalse(InputCheckerUtil.validatePassword("123456789098765432123"))
    }

    @Test
    fun `check with different password will return false`(){
        assertFalse(InputCheckerUtil.isSamePassword("12345678","87654321"))
    }

    @Test
    fun `check with same password will return false`(){
        assertTrue(InputCheckerUtil.isSamePassword("12345678","12345678"))
    }

    @Test
    fun `validateMail with wrong format will return false`(){
        assertFalse(InputCheckerUtil.validateEmailAddress("12345678.mail"))
    }

    @Test
    fun `validateMail with correct format will return false`(){
        assertFalse(InputCheckerUtil.validateEmailAddress("arkar@gmail.com"))
    }
}