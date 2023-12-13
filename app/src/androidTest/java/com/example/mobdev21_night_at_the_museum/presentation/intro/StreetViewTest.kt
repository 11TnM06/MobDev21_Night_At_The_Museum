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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class StreetViewTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(CheckPrefActivity::class.java)

    @Test
    fun streetViewTest() {
//        Thread.sleep(10000)
//        val materialCardView = onView(
//            allOf(
//                withId(R.id.mcvIntroductionSignIn),
//                childAtPosition(
//                    childAtPosition(
//                        withId(R.id.constIntroductionRoot),
//                        3
//                    ),
//                    0
//                ),
//                isDisplayed()
//            )
//        )
//        materialCardView.perform(click())
//        Thread.sleep(5000)
//        val appCompatEditText = onView(
//            allOf(
//                withId(R.id.etvLoginEmail),
//                childAtPosition(
//                    childAtPosition(
//                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
//                        2
//                    ),
//                    0
//                ),
//                isDisplayed()
//            )
//        )
//        appCompatEditText.perform(replaceText("sonanhnguyen003@gmail.com"), closeSoftKeyboard())
//        Thread.sleep(5000)
//        val appCompatEditText2 = onView(
//            allOf(
//                withId(R.id.etvLoginPassword),
//                childAtPosition(
//                    childAtPosition(
//                        withClassName(`is`("android.widget.LinearLayout")),
//                        1
//                    ),
//                    0
//                ),
//                isDisplayed()
//            )
//        )
//        appCompatEditText2.perform(replaceText("12345678"), closeSoftKeyboard())
//        Thread.sleep(5000)
//        val materialCardView2 = onView(
//            allOf(
//                withId(R.id.btnLogin),
//                childAtPosition(
//                    childAtPosition(
//                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
//                        2
//                    ),
//                    2
//                ),
//                isDisplayed()
//            )
//        )
//        materialCardView2.perform(click())
        Thread.sleep(5000)
        val materialCardView3 = onView(
            allOf(
                withId(R.id.mcvHomeStreetViewViewAll),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cvHome),
                        1
                    ),
                    4
                )
            )
        )
        materialCardView3.perform(scrollTo())
        Thread.sleep(5000);
        materialCardView3.perform(click())
        Thread.sleep(5000)
        val frameLayout = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.cvAllStreetView),
                        childAtPosition(
                            withId(R.id.flAllStreetViewContainer),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        frameLayout.perform(click())
        Thread.sleep(5000)
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
        Thread.sleep(5000)
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
        Thread.sleep(5000)
        val shapeableImageView = onView(
            allOf(
                withId(R.id.ivRealMainProfile),
                childAtPosition(
                    allOf(
                        withId(R.id.constRealMainActionBar),
                        childAtPosition(
                            withId(R.id.ablRealMain),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        shapeableImageView.perform(click())
        Thread.sleep(3000)
        val linearLayout = onView(
            allOf(
                withId(R.id.llProfileSettings),
                childAtPosition(
                    allOf(
                        withId(R.id.llProfileContent),
                        childAtPosition(
                            withId(R.id.flProfileBackground),
                            0
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())
        Thread.sleep(3000)
        val materialTextView = onView(
            allOf(
                withId(R.id.tvSettingLogOut), withText("Đăng xuất"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nsvSettingFragment),
                        0
                    ),
                    25
                ),

            )
        )
        materialTextView.perform(scrollTo())
        materialTextView.perform(click())
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
