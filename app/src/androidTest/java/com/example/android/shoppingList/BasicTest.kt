@file:Suppress("DEPRECATION")

package com.example.android.shoppingList


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Basic test:  create, delete, archive a shopping list
//              create and delete list item, test the check mark

@LargeTest
@RunWith(AndroidJUnit4::class)
class BasicTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun basicTest() {
        val floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.FrameLayout")),
                                        0),
                                1),
                        isDisplayed()))
        floatingActionButton.perform(click())

        val appCompatEditText = onView(
                allOf(withId(R.id.dialog_list_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()))
        appCompatEditText.perform(replaceText("List"), closeSoftKeyboard())

        val appCompatButton = onView(
                allOf(withId(R.id.dialog_save_button), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()))
        appCompatButton.perform(click())

        val floatingActionButton2 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.FrameLayout")),
                                        0),
                                1),
                        isDisplayed()))
        floatingActionButton2.perform(click())

        val appCompatEditText2 = onView(
                allOf(withId(R.id.dialog_list_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()))
        appCompatEditText2.perform(replaceText("List 2"), closeSoftKeyboard())

        val appCompatButton2 = onView(
                allOf(withId(R.id.dialog_save_button), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()))
        appCompatButton2.perform(click())

        val recyclerView = onView(
                allOf(withId(R.id.home_recyclerview),
                        childAtPosition(
                                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(1, click()))

        val floatingActionButton3 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()))
        floatingActionButton3.perform(click())

        val appCompatEditText3 = onView(
                allOf(withId(R.id.dialog_list_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()))
        appCompatEditText3.perform(replaceText("Bread"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
                allOf(withId(R.id.dialog_list_name), withText("Bread"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()))
        appCompatEditText4.perform(click())

        val appCompatEditText5 = onView(
                allOf(withId(R.id.dialog_list_name), withText("Bread"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()))
        appCompatEditText5.perform(replaceText("Bread "))

        val appCompatEditText6 = onView(
                allOf(withId(R.id.dialog_list_name), withText("Bread "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()))
        appCompatEditText6.perform(closeSoftKeyboard())

        val appCompatButton3 = onView(
                allOf(withId(R.id.dialog_save_button), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton3.perform(click())

        val appCompatImageView = onView(
                allOf(withId(R.id.check_image),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.detail_recyclerview),
                                        0),
                                0),
                        isDisplayed()))
        appCompatImageView.perform(click())

        val floatingActionButton4 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()))
        floatingActionButton4.perform(click())

        val appCompatEditText7 = onView(
                allOf(withId(R.id.dialog_list_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()))
        appCompatEditText7.perform(replaceText("Milk"), closeSoftKeyboard())

        val appCompatButton4 = onView(
                allOf(withId(R.id.dialog_save_button), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton4.perform(click())

        val appCompatImageView2 = onView(
                allOf(withId(R.id.bin_image),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.detail_recyclerview),
                                        0),
                                3),
                        isDisplayed()))
        appCompatImageView2.perform(click())

        val overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()))
        overflowMenuButton.perform(click())

        val appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Archive list"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatTextView.perform(click())

        val recyclerView2 = onView(
                allOf(withId(R.id.home_recyclerview),
                        childAtPosition(
                                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val overflowMenuButton2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()))
        overflowMenuButton2.perform(click())

        val appCompatTextView2 = onView(
                allOf(withId(R.id.title), withText("Delete list"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatTextView2.perform(click())

        val bottomNavigationItemView = onView(
                allOf(withId(R.id.archive), withContentDescription("Archived lists"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_view),
                                        0),
                                1),
                        isDisplayed()))
        bottomNavigationItemView.perform(click())

        val recyclerView3 = onView(
                allOf(withId(R.id.home_recyclerview),
                        childAtPosition(
                                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
        recyclerView3.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val appCompatImageView3 = onView(
                allOf(withId(R.id.check_image),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.detail_recyclerview),
                                        0),
                                0),
                        isDisplayed()))
        appCompatImageView3.perform(click())

        val appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                2),
                        isDisplayed()))
        appCompatImageButton.perform(click())

        val bottomNavigationItemView2 = onView(
                allOf(withId(R.id.home_fragment), withContentDescription("Active lists"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_view),
                                        0),
                                0),
                        isDisplayed()))
        bottomNavigationItemView2.perform(click())
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
