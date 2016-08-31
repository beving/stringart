package com.marksoft.stringart;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.Assert.*;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;  //TODO rm later, gator ;)
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by e62032 on 7/26/2016.
 * @See: http://blog.sqisland.com/2015/04/espresso-21-activitytestrule.html
 */
public class MainActivityTest { //} extends ActivityInstrumentationTestCase2<MainActivity> {
    /*@Rule public final ActivityTestRule<MainActivity> main = null;

    private MainActivity activity;

    public MainActivityTest() {
        super(MainActivity.class); //activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void toolBarExistsAnIsNotNull() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        assertNotNull(toolbar);
    }*/

    protected Intent intent;

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,    // initialTouchMode
            false);  // launchActivity. False to set intent per method
    //I then supply a launch intent in the test method:

    @Before
    public void setup() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);

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

    /*
    @Test
    public void clearShouldAskFirst() {

        onView(withId(R.id.action_clear)).perform(click());
    }

    @Test
    public void shouldCreatePoint() {

        MainActivity mainActivity = (MainActivity) activityRule.getActivity();

        assertEquals(1, mainActivity.getDrawingView().getDataHandler().getDataFragment().getPoints().size());

        onView(withText("String Art")).perform(click());






        //onView(withText("String Art")).check(matches(;))

        // Check that the number is displayed in the UI.
//        onView(withId(R.id.edit_text_caller_number))
//                .check(matches(withText(VALID_PHONE_NUMBER)));


    }


//        ...perform(click());

//        R.layout.activity_drawing
//
//        onView(withId(R.id.toolbar))
//                .perform(click());



//        onView(withId(R.id.date))
//                .check(matches(withText("2014-10-15")));

*/
}
