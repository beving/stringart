package com.marksoft.stringart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by e62032 on 7/26/2016.
 *
 * @See: http://blog.sqisland.com/2015/04/espresso-21-activitytestrule.html
 * @See: https://www.youtube.com/watch?v=kL3MCQV2M2s
 */
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<MainActivity>(
            MainActivity.class,
            true,    // initialTouchMode
            false);  // launchActivity. False to set intent per method
    //I then supply a launch intent in the test method:

    @Before
    public void setup() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);

        SharedPreferencesUtility.clear(activityRule.getActivity().getApplicationContext());
    }

    @Test
    public void titleShouldExist() {
        onView(withText("String Art")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void drawingViewShouldExist() {
        onView(withId(R.id.drawingView)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void containsUndoAction() {
        onView(withId(R.id.action_undo)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void containsCutAction() {
        onView(withId(R.id.action_cut)).check(ViewAssertions.matches(isDisplayed()));
    }

}
