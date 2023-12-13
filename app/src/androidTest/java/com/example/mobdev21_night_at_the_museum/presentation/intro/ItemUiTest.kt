package com.example.mobdev21_night_at_the_museum.presentation.intro


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.mobdev21_night_at_the_museum.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ItemUiTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(CheckPrefActivity::class.java)

    @Test
    fun itemUiTest() {

        Thread.sleep(10_000)
        val appCompatImageView = onView(
            allOf(
                withId(R.id.ivCollectionItemsSub),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.rvCollectionsStories),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

//        val cardView = onView(
//            allOf(
//                withId(R.id.mcvItemAction),
//                withParent(withParent(withId(R.id.cvItem))),
//                isDisplayed()
//            )
//        )
//        cardView.check(matches(isDisplayed()))

        val cardView2 = onView(
            allOf(
                withId(R.id.mcvItemAction),
                isDisplayed()
            )
        )
        cardView2.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.tvItemName), withText("Ritratto di Antonio Canova"),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.tvItemCreator), withText("Gaetano Monti  Early 19th century"),
                isDisplayed()
            )
        )
        textView2.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(R.id.ivItemLike),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val imageView2 = onView(
            allOf(
                withId(R.id.ivItemCollectionThumb),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))

        val textView3 = onView(
            allOf(
                withId(R.id.tvItemCollection), withText("Van Gogh Museum"),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Van Gogh Museum")))

        val textView4 = onView(
            allOf(
                withId(R.id.tvItemCollection), withText("Van Gogh Museum"),
                isDisplayed()
            )
        )
        textView4.check(matches(isDisplayed()))

        val textView5 = onView(
            allOf(
                withId(R.id.tvItemCollection), withText("Van Gogh Museum"),
                isDisplayed()
            )
        )
        textView5.check(matches(isDisplayed()))

        val textView6 = onView(
            allOf(
                withText("Chi tiết"),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Chi tiết")))

        val textView7 = onView(
            allOf(
                withId(R.id.tvItemDescriptionReadMore), withText("Đọc thêm"),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("Đọc thêm")))

        val textView8 = onView(
            allOf(
                withId(R.id.tvItemDescriptionReadMore), withText("Đọc thêm"),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("Đọc thêm")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

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
