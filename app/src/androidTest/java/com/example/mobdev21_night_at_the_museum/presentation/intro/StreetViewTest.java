package com.example.mobdev21_night_at_the_museum.presentation.intro;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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
public class StreetViewTest {

    @Rule
    public ActivityScenarioRule<CheckPrefActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(CheckPrefActivity.class);

    @Test
    public void streetViewTest() {
        ViewInteraction materialCardView = onView(
                allOf(withId(R.id.mcvHomeStreetViewViewAll),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cvHome),
                                        1),
                                4),
                        isDisplayed()));
        materialCardView.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withParent(withParent(withId(R.id.cvAllStreetView))),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.ivHomeStreetViewThumbnail),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(
                allOf(withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withText("KHÁM PHÁ"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("KHÁM PHÁ")));


        ViewInteraction frameLayout = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.cvAllStreetView),
                                        childAtPosition(
                                                withId(R.id.flAllStreetViewContainer),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction frameLayout2 = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.cvAllStreetView),
                                        childAtPosition(
                                                withId(R.id.flAllStreetViewContainer),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout2.perform(click());
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
