package com.example.mobdev21_night_at_the_museum.presentation.intro


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.mobdev21_night_at_the_museum.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class NewfeedTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(CheckPrefActivity::class.java)

    @Test
    fun newfeed() {
        val materialCardView = onView(
            allOf(
                withId(R.id.mcvIntroductionSignUp),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.constIntroductionRoot),
                        3
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialCardView.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.etvSignUpEmail),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("ten"), closeSoftKeyboard())

        val materialCardView2 = onView(
            allOf(
                withId(R.id.btnSignUp),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        2
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialCardView2.perform(click())

        val materialTextView = onView(
            allOf(
                withId(R.id.tvSignUpSignIn), withText("Đăng nhập"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        6
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val materialCardView3 = onView(
            allOf(
                withId(R.id.btnLogin),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        2
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialCardView3.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.iBottomNavExplore), withContentDescription("Khám phá"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bnvRealMain),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val imageView = onView(
            allOf(
                withId(R.id.ivExploreItemThumbnail),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.recyclerview.widget.RecyclerView::class.java))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.tvExploreItemName),
                withText("The Great Wave off the Coast of Kanagawa"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.recyclerview.widget.RecyclerView::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("The Great Wave off the Coast of Kanagawa")))

        val textView2 = onView(
            allOf(
                withId(R.id.tvExploreItemCreator), withText("Hokusai"),
                withParent(
                    allOf(
                        withId(R.id.llExploreItemCreator),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Hokusai")))

        val imageView2 = onView(
            allOf(
                withId(R.id.ivExploreItemZoom),
                withParent(
                    allOf(
                        withId(R.id.flExploreItemZoom),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))

        val textView3 = onView(
            allOf(
                withId(R.id.tvExploreItemCollectionName), withText("Reading Public Museum"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.recyclerview.widget.RecyclerView::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Reading Public Museum")))

        val imageView3 = onView(
            allOf(
                withId(R.id.ivExploreItemCollectionIcon),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.recyclerview.widget.RecyclerView::class.java))),
                isDisplayed()
            )
        )
        imageView3.check(matches(isDisplayed()))

        val imageView4 = onView(
            allOf(
                withId(R.id.ivExploreItemCollectionIcon),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.recyclerview.widget.RecyclerView::class.java))),
                isDisplayed()
            )
        )
        imageView4.check(matches(isDisplayed()))
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
