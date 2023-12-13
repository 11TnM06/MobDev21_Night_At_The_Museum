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
class MuseumTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(CheckPrefActivity::class.java)

    @Test
    fun checkPrefActivityTest2() {
       Thread.sleep(5000)
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
        Thread.sleep(5000)
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
        appCompatEditText.perform(replaceText("sonanhguyen003@gmail.com"), closeSoftKeyboard())
        Thread.sleep(5000)
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
        Thread.sleep(5000)
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
        Thread.sleep(5000)
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
        Thread.sleep(5000)
        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.etvLoginPassword), withText("12345678"),
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
        appCompatEditText3.perform(click())
        Thread.sleep(5000)
        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.etvLoginPassword), withText("12345678"),
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
        appCompatEditText4.perform(click())
        Thread.sleep(5000)
        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.etvLoginPassword), withText("12345678"),
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
        appCompatEditText5.perform(replaceText("12345678"))
        Thread.sleep(5000)
        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.etvLoginPassword), withText("12345678"),
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
        appCompatEditText6.perform(closeSoftKeyboard())
        Thread.sleep(5000)
        val materialCardView4 = onView(
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
        materialCardView4.perform(click())
        Thread.sleep(5000)
        val appCompatEditText7 = onView(
            allOf(
                withId(R.id.etvLoginEmail), withText("sonanhguyen003@gmail.com"),
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
        appCompatEditText7.perform(click())
        Thread.sleep(5000)
        val appCompatEditText8 = onView(
            allOf(
                withId(R.id.etvLoginEmail), withText("sonanhguyen003@gmail.com"),
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
        appCompatEditText8.perform(replaceText("sonanhnguyen003@gmail.com"))
        Thread.sleep(5000)
        val appCompatEditText9 = onView(
            allOf(
                withId(R.id.etvLoginEmail), withText("sonanhnguyen003@gmail.com"),
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
        appCompatEditText9.perform(closeSoftKeyboard())
        Thread.sleep(5000)
        val materialCardView5 = onView(
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
        materialCardView5.perform(click())
        Thread.sleep(5000)
        val materialCardView6 = onView(
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
        materialCardView6.perform(click())
        Thread.sleep(5000)
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
        Thread.sleep(5000)
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
                    0
                ),
                isDisplayed()
            )
        )
        frameLayout2.perform(click())
        Thread.sleep(5000)
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
        Thread.sleep(5000)
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
        Thread.sleep(5000)
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
        Thread.sleep(5000)
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
