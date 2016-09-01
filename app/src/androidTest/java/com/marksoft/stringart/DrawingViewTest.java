package com.marksoft.stringart;

import android.content.Intent;
import android.graphics.Color;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;




/**
 * Created by e62032 on 7/26/2016.
 *
 * See: http://blog.sqisland.com/2015/04/espresso-21-activitytestrule.html
 */
public class DrawingViewTest {

    private DrawingView drawingView;
    private RetainedFragment retainedFragment;
    private MainActivity mainActivity;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,
            false);

    @Before
    public void setup() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);

        mainActivity = activityRule.getActivity();
        drawingView = mainActivity.getDrawingView();
        retainedFragment = mainActivity.getDrawingView().getDataHandler().getDataFragment();
    }

    @Test
    public void createOnePoint() {
        onView(withId(R.id.drawingView)).perform(click());
        assertEquals(1, mainActivity.getDrawingView().getDataHandler().getDataFragment().getPoints().size());
    }

    @Test
    public void createPoints() {
        onView(withId(R.id.drawingView)).perform(swipeRight());

        assertTrue(retainedFragment.getPoints().size() > 1);
        assertTrue(retainedFragment.getLines().size() > 1);
    }

    @Test
    public void clearShouldRemoveAllLinesAndPoints() {

        onView(withId(R.id.drawingView)).perform(swipeRight());
        onView(withId(R.id.drawingView)).perform(swipeDown());
        onView(withId(R.id.drawingView)).perform(swipeLeft());

        assertTrue(retainedFragment.getPoints().size() > 1);
        assertTrue(retainedFragment.getLines().size() > 1);

        openActionBarOverflowOrOptionsMenu(mainActivity.getDrawingView().getContext());
        onView(withText(R.string.clear)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        assertEquals(0, retainedFragment.getPoints().size());
        assertEquals(0, retainedFragment.getLines().size());
    }

    @Test
    public void undoShouldRemoveLastPointAndLines() {

        onView(withId(R.id.drawingView)).perform(swipeRight());

        int numberOfLines = retainedFragment.getLines().size();
        int numberOfPoints = retainedFragment.getPoints().size();

        onView(withId(R.id.action_undo)).perform(click());

        assertEquals(numberOfPoints - 1, retainedFragment.getPoints().size());
        assertEquals(numberOfLines - (numberOfPoints * 2), retainedFragment.getLines().size());
    }

    @Test
    public void pressingUndoTwiceShouldRemoveLastTwoPointsAndLines() {

        onView(withId(R.id.drawingView)).perform(swipeRight());

        int numberOfLines = retainedFragment.getLines().size();
        int numberOfPoints = retainedFragment.getPoints().size();

        onView(withId(R.id.action_undo)).perform(click());

        assertEquals(numberOfPoints - 1, retainedFragment.getPoints().size());
        assertEquals(numberOfLines - (numberOfPoints * 2), retainedFragment.getLines().size());

        numberOfPoints = retainedFragment.getPoints().size();

        onView(withId(R.id.action_undo)).perform(click());

        assertEquals(numberOfPoints - 1, retainedFragment.getPoints().size());
    }

    @Test
    public void shouldStartDrawingLinesWithDefaultColor() {

        onView(withId(R.id.drawingView)).perform(swipeRight());

        for (Line line : retainedFragment.getLines()) {
            assertEquals(Color.RED, line.getColor());
        }
    }

    @Test
    public void shouldChangeColorOfLines() {
        onView(withId(R.id.drawingView)).perform(swipeRight());

        for (Line line : retainedFragment.getLines()) {
            assertEquals(Color.RED, line.getColor());
        }
        drawingView.getDataHandler().getDataFragment().setLastSelectedColor(Color.BLACK);

        onView(withId(R.id.drawingView)).perform(swipeDown());

        Line lastLine = retainedFragment.getLines().get(retainedFragment.getLines().size()-1);
        Line firstLine = retainedFragment.getLines().get(0);

        assertEquals(Color.RED, firstLine.getColor());
        assertEquals(Color.BLACK, lastLine.getColor());
    }

    @Test
    public void rotatingScreenShouldNotLooseData() {

        onView(withId(R.id.drawingView)).perform(swipeRight());
        onView(withId(R.id.drawingView)).perform(swipeDown());
        onView(withId(R.id.drawingView)).perform(swipeLeft());
        onView(withId(R.id.drawingView)).perform(swipeRight());

        int numberOfPointsBefore = retainedFragment.getPoints().size();

        TestUtility.rotateScreen(activityRule);
        onView(withId(R.id.drawingView)).perform(click());//Have to do this for some reason or it will not rotate.

        assertEquals(numberOfPointsBefore + 1, retainedFragment.getPoints().size());
    }

    @Test
    public void cutOnePoint() {
        //Create a point on the screen by touching it.
        onView(withId(R.id.drawingView)).perform(click());
        assertEquals(1, mainActivity.getDrawingView().getDataHandler().getDataFragment().getPoints().size());

        //Click cut button
        onView(withId(R.id.action_cut)).perform(click());

        //Touch existing point that we are cutting.
        onView(withId(R.id.drawingView)).perform(click());
        assertEquals(0, mainActivity.getDrawingView().getDataHandler().getDataFragment().getPoints().size());
    }

}
//Check for Toast
//onView(withText(R.string.clear_question)).inRoot(withDecorView(not(is(mainActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));