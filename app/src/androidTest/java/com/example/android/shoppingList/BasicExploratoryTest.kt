package com.example.android.shoppingList


import android.view.View
import android.view.ViewGroup
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.android.shoppingList.EspressoTestsMatchers.withDrawable
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Basic test:  create, delete, archive a shopping list
//              create and delete list item, test the check mark
@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class BasicExploratoryTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun basicTest() {

       // onView(withId(R.id.fab)).check(matches(isDisplayed()))

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.dialog_list_name)).perform(replaceText("List 1"), closeSoftKeyboard())

        onView(withId(R.id.dialog_save_button)).perform(click())

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.dialog_list_name)).perform(replaceText("List 2"), closeSoftKeyboard())

        onView(withId(R.id.dialog_save_button)).perform(click())

        onView(withId(R.id.home_recyclerview)).perform(actionOnItemAtPosition<ViewHolder>(1, click()))

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.dialog_list_name)).perform(replaceText("Bread"), closeSoftKeyboard())

        onView(withId(R.id.dialog_save_button)).perform(click())

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.dialog_list_name)).perform(replaceText("Milk"), closeSoftKeyboard())

        onView(withId(R.id.dialog_save_button)).perform(click())

        val checkImageItemOne = onView(
                allOf(withId(R.id.check_image),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.detail_recyclerview),
                                        0),
                                0),
                        isDisplayed()))
        checkImageItemOne.perform(click())

        checkImageItemOne.check(matches(withDrawable(R.drawable.check_circle_outline_on)))

        val binImageItemOne = onView(
                allOf(withId(R.id.bin_image),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.detail_recyclerview),
                                        0),
                                3),
                        isDisplayed()))

        binImageItemOne.perform(click())

        onView(withContentDescription("More options")).perform(click())

        onView(withText("Delete list")).perform(click())

        onView(withContentDescription("Archived lists")).perform(click())

        onView(withId(R.id.home_recyclerview)).perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        onView(withContentDescription("Navigate up")).perform(click())

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
