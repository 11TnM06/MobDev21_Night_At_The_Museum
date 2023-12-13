package com.example.mobdev21_night_at_the_museum.presentation.intro;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.mobdev21_night_at_the_museum.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StreetViewUITest {

    @Rule
    public ActivityScenarioRule<CheckPrefActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(CheckPrefActivity.class);

    @Test
    public void streetViewUITest() {
        ViewInteraction materialCardView = onView(
                allOf(withId(R.id.mcvIntroductionSignIn),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.constIntroductionRoot),
                                        3),
                                0),
                        isDisplayed()));
        materialCardView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.etvLoginEmail),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("s"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.etvLoginEmail), withText("s"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.etvLoginEmail), withText("s"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("sonanhnguyen003@gmail.com"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.etvLoginEmail), withText("sonanhnguyen003@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.etvLoginPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("12345678"), closeSoftKeyboard());

        ViewInteraction materialCardView2 = onView(
                allOf(withId(R.id.btnLogin),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        2),
                                2),
                        isDisplayed()));
        materialCardView2.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.cvHomeStreetView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.ivRealMainSearch),
                        childAtPosition(
                                allOf(withId(R.id.constRealMainActionBar),
                                        childAtPosition(
                                                withId(R.id.ablRealMain),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction materialCardView3 = onView(
                allOf(withId(R.id.mcvHomeStreetViewViewAll),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cvHome),
                                        0),
                                4),
                        isDisplayed()));
        materialCardView3.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withParent(allOf(withId(R.id.cvAllStreetView),
                                withParent(withId(R.id.flAllStreetViewContainer)))),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

        ViewInteraction frameLayout2 = onView(
                allOf(withParent(allOf(withId(R.id.cvAllStreetView),
                                withParent(withId(R.id.flAllStreetViewContainer)))),
                        isDisplayed()));
        frameLayout2.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withText("KHÁM PHÁ"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("KHÁM PHÁ")));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.ivHomeStreetViewThumbnail),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tvHomeStreetViewName), withText("Auditorio do Ibirapuera (Sao Paulo) - Brazil"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.tvHomeStreetViewName), withText("Auditorio do Ibirapuera (Sao Paulo) - Brazil"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction frameLayout3 = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.cvAllStreetView),
                                        childAtPosition(
                                                withId(R.id.flAllStreetViewContainer),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout3.perform(click());

        ViewInteraction frameLayout4 = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.cvAllStreetView),
                                        childAtPosition(
                                                withId(R.id.flAllStreetViewContainer),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout4.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withText("Từ hậu trường Nhà hát Opera Paris đến đỉnh Taj Mahal"),
                        withParent(withParent(withId(R.id.cvHome))),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withText("Từ hậu trường Nhà hát Opera Paris đến đỉnh Taj Mahal"),
                        withParent(withParent(withId(R.id.cvHome))),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
