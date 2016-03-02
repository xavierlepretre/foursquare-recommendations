package com.github.xavierlepretre.foursquarerecommendations;

import android.support.test.rule.ActivityTestRule;

import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.fest.assertions.api.Assertions.assertThat;

public class MainActivityTest
{
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testFabButton_launchesDialog() throws Exception
    {
        onView(withId(R.id.fab)).perform(click());

        onView(withText("Where do you want to explore?")).check(matches(isDisplayed()));
    }

    @Test
    public void testFabButtonTwice_launchesDialogTwice() throws Exception
    {
        onView(withId(R.id.fab)).perform(click());
        onView(withText(android.R.string.cancel)).perform(click());
        onView(withId(R.id.fab)).perform(click());

        onView(withText("Where do you want to explore?")).check(matches(isDisplayed()));
    }

    @Test
    public void testEnterPlace_takesIt() throws Exception
    {
        onView(withId(R.id.fab)).perform(click());
        onView(withClassName(new StringContains("EditText"))).perform(typeText("London"));
        onView(withText(android.R.string.ok)).perform(click());

        assertThat(activityRule.getActivity().desiredPlace).isEqualTo("London");
    }

    @Test(timeout = 10000)
    public void testEnterPlace_getsSomethingFromFoursquare() throws Exception
    {
        onView(withId(R.id.fab)).perform(click());
        onView(withClassName(new StringContains("EditText"))).perform(typeText("London"));
        onView(withText(android.R.string.ok)).perform(click());

        while (activityRule.getActivity().recommendedVenuesGroups == null)
        {
            Thread.sleep(500, 0);
        }
        assertThat(activityRule.getActivity().recommendedVenuesGroups.size()).isGreaterThanOrEqualTo(1);
        assertThat(activityRule.getActivity().recommendedVenuesGroups.get(0).getItems().size()).isGreaterThanOrEqualTo(10);
    }
}