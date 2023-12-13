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
        val materialCardView = onView(
            allOf(
                withId(R.id.mcvIntroductionSignIn),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.constIntroductionRoot),
                        3
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialCardView.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.etvLoginEmail),
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
        appCompatEditText.perform(replaceText("sonanhnguyen003@gmail.com"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.etvLoginPassword),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("12345678"), closeSoftKeyboard())

        val materialCardView2 = onView(
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
        materialCardView2.perform(click())

        val materialCardView3 = onView(
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
        materialCardView3.perform(click())

        val frameLayout = onView(
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
        frameLayout.perform(click())

        val frameLayout2 = onView(
            allOf(
                withId(R.id.flAzCollection),
                childAtPosition(
                    allOf(
                        withId(R.id.cvAzCollectionKey),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        frameLayout2.perform(click())

        val frameLayout3 = onView(
            allOf(
                withId(R.id.flAzCollection),
                childAtPosition(
                    allOf(
                        withId(R.id.cvAzCollectionKey),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        frameLayout3.perform(click())

        val constraintLayout = onView(
            allOf(
                withId(R.id.constCollectionsRoot),
                childAtPosition(
                    allOf(
                        withId(R.id.cvAzCollection),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        constraintLayout.perform(click())

        val materialCardView4 = onView(
            allOf(
                withId(R.id.mcvCollectionHeaderFollow),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvCollection),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialCardView4.perform(click())

        val materialCardView5 = onView(
            allOf(
                withId(R.id.mcvCollectionHeaderFollow),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvCollection),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialCardView5.perform(click())

        val materialTextView = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Đọc thêm"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvCollection),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val materialTextView2 = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Rút gọn"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvCollection),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView2.perform(click())

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

        val frameLayout4 = onView(
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
        frameLayout4.perform(click())

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

        val materialCardView6 = onView(
            allOf(
                withId(R.id.mcvCollectionHeaderFollow),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvCollection),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialCardView6.perform(click())

        val materialTextView3 = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Đọc thêm"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvCollection),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView3.perform(click())

        val materialTextView4 = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Rút gọn"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvCollection),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView4.perform(click())

        val appCompatImageView2 = onView(
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
        appCompatImageView2.perform(click())

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

        val textView = onView(
            allOf(
                withId(R.id.tvCollectionsTabAll), withText("Tất cả"),
                withParent(
                    allOf(
                        withId(R.id.flCollectionsTabAll),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Tất cả")))

        val textView2 = onView(
            allOf(
                withId(R.id.tvCollectionsTabAZ), withText("A-Z"),
                withParent(
                    allOf(
                        withId(R.id.flCollectionsTabAZ),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("A-Z")))

        val frameLayout5 = onView(
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
        frameLayout5.perform(click())

        val frameLayout6 = onView(
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
        frameLayout6.perform(click())

        val textView3 = onView(
            allOf(
                withId(R.id.tvAzCollection), withText("A"),
                withParent(
                    allOf(
                        withId(R.id.flAzCollection),
                        withParent(withId(R.id.cvAzCollectionKey))
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("A")))

        val viewGroup3 = onView(
            allOf(
                withId(R.id.constCollectionsRoot),
                withParent(
                    allOf(
                        withId(R.id.cvAzCollection),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup3.check(matches(isDisplayed()))

        val textView4 = onView(
            allOf(
                withId(R.id.tvAzCollection), withText("B"),
                withParent(
                    allOf(
                        withId(R.id.flAzCollection),
                        withParent(withId(R.id.cvAzCollectionKey))
                    )
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("B")))

        val textView5 = onView(
            allOf(
                withId(R.id.tvAzCollection), withText("C"),
                withParent(
                    allOf(
                        withId(R.id.flAzCollection),
                        withParent(withId(R.id.cvAzCollectionKey))
                    )
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("C")))

        val textView6 = onView(
            allOf(
                withId(R.id.tvAzCollection), withText("D"),
                withParent(
                    allOf(
                        withId(R.id.flAzCollection),
                        withParent(withId(R.id.cvAzCollectionKey))
                    )
                ),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("D")))

        val constraintLayout3 = onView(
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
        constraintLayout3.perform(click())

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

        val constraintLayout4 = onView(
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
        constraintLayout4.perform(click())

        val textView7 = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Đọc thêm"),
                withParent(withParent(withId(R.id.cvCollection))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("Đọc thêm")))

        val textView8 = onView(
            allOf(
                withText("Đang theo dõi"),
                withParent(
                    allOf(
                        withId(R.id.llCollectionHeaderFollowing),
                        withParent(withId(R.id.mcvCollectionHeaderFollow))
                    )
                ),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("Đang theo dõi")))

        val imageView = onView(
            allOf(
                withId(R.id.ivCollectionHeaderShare),
                withParent(withParent(withId(R.id.cvCollection))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val imageView2 = onView(
            allOf(
                withId(R.id.ivHomeCollection3),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))

        val imageView3 = onView(
            allOf(
                withId(R.id.ivCollectionHeaderIcon),
                withParent(withParent(withId(R.id.cvCollection))),
                isDisplayed()
            )
        )
        imageView3.check(matches(isDisplayed()))

        val textView9 = onView(
            allOf(
                withId(R.id.tvCollectionHeaderPlace), withText("Dublin 1, Ireland"),
                withParent(withParent(withId(R.id.cvCollection))),
                isDisplayed()
            )
        )
        textView9.check(matches(isDisplayed()))

        val textView10 = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Đọc thêm"),
                withParent(withParent(withId(R.id.cvCollection))),
                isDisplayed()
            )
        )
        textView10.check(matches(withText("Đọc thêm")))

        val materialCardView7 = onView(
            allOf(
                withId(R.id.mcvCollectionHeaderFollow),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvCollection),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialCardView7.perform(click())

        val materialCardView8 = onView(
            allOf(
                withId(R.id.mcvCollectionHeaderFollow),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvCollection),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialCardView8.perform(click())

        val linearLayout = onView(
            allOf(
                withId(R.id.llCollectionHeaderFollow),
                withParent(
                    allOf(
                        withId(R.id.mcvCollectionHeaderFollow),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))

        val linearLayout2 = onView(
            allOf(
                withId(R.id.llCollectionHeaderFollow),
                withParent(
                    allOf(
                        withId(R.id.mcvCollectionHeaderFollow),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout2.check(matches(isDisplayed()))

        val materialTextView5 = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Đọc thêm"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvCollection),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView5.perform(click())

        val materialTextView6 = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Rút gọn"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvCollection),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView6.perform(click())

        val materialTextView7 = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Đọc thêm"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvCollection),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView7.perform(click())

        val textView11 = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Rút gọn"),
                withParent(withParent(withId(R.id.cvCollection))),
                isDisplayed()
            )
        )
        textView11.check(matches(isDisplayed()))

        val textView12 = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Rút gọn"),
                withParent(withParent(withId(R.id.cvCollection))),
                isDisplayed()
            )
        )
        textView12.check(matches(isDisplayed()))

        val textView13 = onView(
            allOf(
                withId(R.id.tvCollectionDescriptionReadMore), withText("Rút gọn"),
                withParent(withParent(withId(R.id.cvCollection))),
                isDisplayed()
            )
        )
        textView13.check(matches(isDisplayed()))
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
