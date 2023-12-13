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
class CheckPrefActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(CheckPrefActivity::class.java)

    @Test
    fun checkPrefActivityTest() {
        val linearLayout = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.cvHomeStreetView),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.ivRealMainSearch),
                childAtPosition(
                    allOf(
                        withId(R.id.constRealMainActionBar),
                        childAtPosition(
                            withId(R.id.ablRealMain),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

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

        val frameLayout = onView(
            allOf(
                withId(R.id.flCollectionsTabAll),
                withParent(withParent(withId(R.id.llCollectionsHeader))),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))

        val frameLayout2 = onView(
            allOf(
                withId(R.id.flCollectionsTabAZ),
                withParent(withParent(withId(R.id.llCollectionsHeader))),
                isDisplayed()
            )
        )
        frameLayout2.check(matches(isDisplayed()))

        val viewGroup = onView(
            allOf(
                withId(R.id.constCollectionsRoot),
                withParent(
                    allOf(
                        withId(R.id.cvAllCollectionTabAll),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val viewGroup2 = onView(
            allOf(
                withId(R.id.constCollectionsRoot),
                withParent(
                    allOf(
                        withId(R.id.cvAllCollectionTabAll),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup2.check(matches(isDisplayed()))

        val constraintLayout = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.flRealMainContainer),
                        childAtPosition(
                            withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        constraintLayout.perform(click())

        val frameLayout3 = onView(
            allOf(
                withId(R.id.flCollectionsTabAZ),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.llCollectionsHeader),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        frameLayout3.perform(click())

        val frameLayout4 = onView(
            allOf(
                withId(R.id.flAzCollection),
                withParent(
                    allOf(
                        withId(R.id.cvAzCollectionKey),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        frameLayout4.check(matches(isDisplayed()))

        val frameLayout5 = onView(
            allOf(
                withId(R.id.flAzCollection),
                withParent(
                    allOf(
                        withId(R.id.cvAzCollectionKey),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        frameLayout5.check(matches(isDisplayed()))

        val frameLayout6 = onView(
            allOf(
                withId(R.id.flCollectionsTabAll),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.llCollectionsHeader),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        frameLayout6.perform(click())

        val frameLayout7 = onView(
            allOf(
                withId(R.id.flCollectionsTabAll),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.llCollectionsHeader),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        frameLayout7.perform(click())

        val constraintLayout2 = onView(
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
                    0
                ),
                isDisplayed()
            )
        )
        constraintLayout2.perform(click())

        val imageView = onView(
            allOf(
                withId(R.id.ivCollectionHeaderThumbnail),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val imageView2 = onView(
            allOf(
                withId(R.id.ivCollectionHeaderIcon),
                withParent(withParent(withId(R.id.cvCollection))),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))

        val imageView3 = onView(
            allOf(
                withId(R.id.ivCollectionHeaderShare),
                withParent(withParent(withId(R.id.cvCollection))),
                isDisplayed()
            )
        )
        imageView3.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.tvCollectionDescription),
                withText("The Abbey Theatre was founded as Ireland’s national theatre, by W.B. Yeats and Lady Gregory in 1904 'to bring upon the stage the deeper emotions of Ireland'. Although written more than a hundred years ago, this is still the kernel of what constitutes the artistic imperative for the Abbey Theatre today."),
                withParent(withParent(withId(R.id.cvCollection))),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Đọc thêm"),
                withParent(withParent(withId(R.id.cvCollection))),
                isDisplayed()
            )
        )
        textView2.check(matches(isDisplayed()))
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
