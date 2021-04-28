package com.example.android.shoppingList


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Automate creating the shopping list
@Suppress("DEPRECATION")
@LargeTest
@RunWith(AndroidJUnit4::class)
class AddXlists {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun addXlists() {
        //Number of lists to create
        val number = 5

        for (i in 0..number) {
            addOneList("List no " + i)
        }
    }

    private fun addOneList(listName: String) {

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.dialog_list_name)).perform(replaceText(listName), closeSoftKeyboard())

        onView(withId(R.id.dialog_save_button)).perform(click())

    }

    private fun sleep() {
        Thread.sleep(10000)
    }

}
