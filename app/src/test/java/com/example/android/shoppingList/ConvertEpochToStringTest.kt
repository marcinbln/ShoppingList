package com.example.android.shoppingList.utils

import org.junit.Assert.*
import org.junit.Test


class ConvertEpochToStringTest{

    @Test
    fun testEpochTimeToStringConversion() {

        val fakeEpochTime = 1618170128826L

        val result = convertEpochtoString(1618170128826)

        val expected = "21:42 11/04/2021"  //  GMT+02:00 DST

        assertEquals(result, expected)




    }










}