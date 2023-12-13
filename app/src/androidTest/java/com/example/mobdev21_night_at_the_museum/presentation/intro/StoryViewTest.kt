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
class StoryViewTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(CheckPrefActivity::class.java)

    @Test
    fun storyViewTest() {
        val materialCardView = onView(
            allOf(
                withId(R.id.mcvHomeCollectionExploreAll),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvHome),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialCardView.perform(click())

        val constraintLayout = onView(
            allOf(
                withId(R.id.constCollectionsRoot),
                childAtPosition(
                    allOf(
                        withId(R.id.cvAllCollectionTabAll),
                        childAtPosition(
                            withClassName(`is`("android.widget.FrameLayout")),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        constraintLayout.perform(click())

        val materialTextView = onView(
            allOf(
                withId(R.id.tvCollectionStoriesViewAll), withText("Xem tất cả"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.llCollectionStoriesRoot),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val linearLayout = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.cvItemList),
                        childAtPosition(
                            withId(R.id.flItemListContainer),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.tvStoryTitle), withText("Vincent Van Gogh's Love Life"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Vincent Van Gogh's Love Life")))

        val textView2 = onView(
            allOf(
                withId(R.id.tvStory),
                withText("Vincent was never lucky in love. How come it never seemed to work out?"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Vincent was never lucky in love. How come it never seemed to work out?")))

        val cardView = onView(
            allOf(
                withId(R.id.flStoryNext),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        cardView.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(R.id.ivStoryNext),
                withParent(
                    allOf(
                        withId(R.id.flStoryNext),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val imageButton = onView(
            allOf(
                withId(R.id.fabRealMainCamera),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        imageButton.check(matches(isDisplayed()))
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
