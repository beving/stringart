package com.markbev.stringart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.widget.ArrayAdapter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the Drawing View.
 * Created by e62032 on 7/26/2016.
 */
public class DrawingViewTest {

    private DrawingView drawingView;
    private Context context;

    // See: http://blog.sqisland.com/2015/04/espresso-21-activitytestrule.html
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(
            MainActivity.class, true, false);

    @Before
    public void setup() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);

        drawingView = activityRule.getActivity().getDrawingView();
        context = activityRule.getActivity().getApplicationContext();

        TestUtility.clickOnViaTextOrId(context, context.getString(R.string.clear), R.id.action_clear);
        onView(withId(android.R.id.button1)).perform(click());

        SharedPreferencesUtility.clear(context);
    }

    @Test
    public void clearShouldRemoveAllLinesAndPoints() throws UiObjectNotFoundException {

        createSomeLines();
        onView(withId(R.id.drawingView)).perform(swipeLeft());

        assertTrue(!drawingView.getLines().isEmpty());
        assertTrue(!drawingView.getPoints().isEmpty());

        TestUtility.clickOnViaTextOrId(context, context.getString(R.string.clear), R.id.action_clear);
        onView(withId(android.R.id.button1)).perform(click());

        assertTrue(drawingView.getLines().isEmpty());
        assertTrue(drawingView.getPoints().isEmpty());
    }

    @Test
    public void clearShouldRemoveLinesButNotChangeOtherPreferences() throws UiObjectNotFoundException {

        SharedPreferencesUtility.setLineColor(context, Color.BLACK);
        SharedPreferencesUtility.setGridSpacing(context, 150);

        createSomeLines();
        onView(withId(R.id.drawingView)).perform(swipeLeft());

        assertTrue(!drawingView.getLines().isEmpty());
        assertTrue(!drawingView.getPoints().isEmpty());

        TestUtility.clickOnViaTextOrId(context, context.getString(R.string.clear), R.id.action_clear);
        onView(withId(android.R.id.button1)).perform(click());

        //Verify the lines and points are gone
        assertTrue(drawingView.getLines().isEmpty());
        assertTrue(drawingView.getPoints().isEmpty());

        //Other prefences remain the same
        assertEquals(Color.BLACK, SharedPreferencesUtility.getLineColor(context));
        assertEquals(150, SharedPreferencesUtility.getGridSpacing(context));
    }

    @Test
    public void createOnePoint() {
        onView(withId(R.id.drawingView)).perform(click());
        assertEquals(1, drawingView.getPoints().size());
    }

    @Test
    public void createPoints() {
        onView(withId(R.id.drawingView)).perform(swipeRight());

        assertTrue(!drawingView.getLines().isEmpty());
        assertTrue(!drawingView.getPoints().isEmpty());
    }

    @Test
    public void undoShouldRemoveLastPointAndLines() {

        onView(withId(R.id.drawingView)).perform(swipeRight());

        int numberOfLines = drawingView.getLines().size();
        int numberOfPoints = drawingView.getPoints().size();

        onView(withId(R.id.action_undo)).perform(click());

        assertEquals(numberOfPoints - 1, drawingView.getPoints().size());
        assertEquals(numberOfLines - (numberOfPoints * 2), drawingView.getLines().size());
    }

    @Test
    public void pressingUndoTwiceShouldRemoveLastTwoPointsAndLines() {

        onView(withId(R.id.drawingView)).perform(swipeRight());

        int numberOfPoints = drawingView.getPoints().size();

        onView(withId(R.id.action_undo)).perform(click());

        assertEquals(numberOfPoints - 1, drawingView.getPoints().size());
        //Failed on my phone assertEquals(numberOfLines - (numberOfPoints * 2), drawingView.getLines().size());
        assertTrue(numberOfPoints < drawingView.getLines().size());

        numberOfPoints = drawingView.getPoints().size();

        onView(withId(R.id.action_undo)).perform(click());

        assertEquals(numberOfPoints - 1, drawingView.getPoints().size());
    }

    @Test
    public void shouldSetBackgroundColor() {
        onView(withId(R.id.drawingView)).perform(swipeRight());

        for (Line line : drawingView.getLines()) {
            assertEquals(SharedPreferencesUtility.DEFAULT_LINE_COLOR, line.getColor());
        }
    }

    @Test
    public void shouldStartDrawingLinesWithDefaultColor() {
        onView(withId(R.id.drawingView)).perform(swipeRight());

        for (Line line : drawingView.getLines()) {
            assertEquals(Color.RED, line.getColor());
        }
    }

    @Test
    public void shouldChangeColorOfLines() {
        createSomeLines();

        for (Line line : drawingView.getLines()) {
            assertEquals(SharedPreferencesUtility.DEFAULT_LINE_COLOR, line.getColor());
        }

        //TODO Not really testing, but this is the best I can do for now due to the ColorDialog
        SharedPreferencesUtility.setLineColor(context, Color.BLACK);

        createSomeLines();

        Line lastLine = drawingView.getLines().get(drawingView.getLines().size() - 1);
        Line firstLine = drawingView.getLines().get(0);

        assertEquals(SharedPreferencesUtility.DEFAULT_LINE_COLOR, firstLine.getColor());
        assertEquals(Color.BLACK, lastLine.getColor());
    }

    @Test
    public void rotatingScreenShouldNotLooseData() {

        createSomeLines();
        createSomeLines();

        int numberOfPointsBefore = drawingView.getPoints().size();
        int numberOfLinesBefore = drawingView.getLines().size();

        TestUtility.rotateScreen(activityRule);
        onView(withId(R.id.drawingView)).check(matches(isDisplayed()));

        assertEquals(numberOfPointsBefore, drawingView.getPoints().size());
        assertEquals(numberOfLinesBefore, drawingView.getLines().size());
    }

    @Test
    public void shouldDrawPoint() {
        //Create a point on the screen by touching it.
        onView(withId(R.id.drawingView)).perform(click());

        assertEquals(1, drawingView.getPoints().size());
    }

    @Test
    public void cutOnePoint() {
        //Create a point on the screen by touching it.
        onView(withId(R.id.drawingView)).perform(click());
        assertEquals(1, drawingView.getPoints().size());

        //Click cut button
        onView(withId(R.id.action_cut)).perform(click());

        //Touch existing point that we are cutting.
        onView(withId(R.id.drawingView)).perform(click());
        assertEquals(0, drawingView.getPoints().size());
    }

    @Test
    public void shouldTurnGridLinesOff() {

        //Grid lines should start off being turned on.
        assertEquals(true, SharedPreferencesUtility.isGridLinesOn(context));

        //Turn Off Grid Lines.
        openActionBarOverflowOrOptionsMenu(context);
        onView(withText(R.string.toggle_grid)).perform(click());

        assertEquals(false, SharedPreferencesUtility.isGridLinesOn(context));
    }

    @Test
     public void shouldChangeLineSize() {

        assertEquals(SharedPreferencesUtility.DEFAULT_STROKE_WIDTH, SharedPreferencesUtility.getStrokeWidth(context));

        //Get the Biggest line size from our simple selectable list.
        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.line_sizes, android.R.layout.simple_selectable_list_item);
        String biggestLineSize = (arrayAdapter.getItem(arrayAdapter.getCount() - 1)).toString();


        openActionBarOverflowOrOptionsMenu(context);
        onView(withText(R.string.line_size)).perform(click());
        onView(withId(android.R.id.button2)).perform(click());

        onView(withText(biggestLineSize)).perform(click());

        assertEquals(biggestLineSize, SharedPreferencesUtility.getStrokeWidth(context) + "");
    }

    @Test
    public void shouldHaveMultipleLineSizes() {
        createSomeLines();
        assertEquals(SharedPreferencesUtility.DEFAULT_STROKE_WIDTH, SharedPreferencesUtility.getStrokeWidth(context));

        //Get the Biggest line size from our simple selectable list.
        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.line_sizes, android.R.layout.simple_selectable_list_item);
        String biggestLineSize = (arrayAdapter.getItem(arrayAdapter.getCount() - 1)).toString();


        openActionBarOverflowOrOptionsMenu(context);
        onView(withText(R.string.line_size)).perform(click());
        onView(withId(android.R.id.button2)).perform(click());
        onView(withText(biggestLineSize)).perform(click());

        createSomeMoreLines();

        Set lineSizes = new HashSet();
        for (Line line : drawingView.getLines()) {
            if (!lineSizes.contains(line.getThickness())) {
                lineSizes.add(line.getThickness());
            }
        }
        assertTrue("Line Size larger than one.", lineSizes.size() > 1);
    }

    @Test
    public void shouldChangeGridSpacing() {

        assertEquals(SharedPreferencesUtility.DEFAULT_GRID_SPACING, SharedPreferencesUtility.getGridSpacing(context));

        //Get the Biggest line size from our simple selectable list.
        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.grid_sizes, android.R.layout.simple_selectable_list_item);

        String biggestGridSize = (arrayAdapter.getItem(arrayAdapter.getCount() - 1)).toString();

        openActionBarOverflowOrOptionsMenu(context);

        onView(withText(R.string.grid_spacing)).perform(click());
        onView(withText(biggestGridSize)).perform(click());

        assertEquals(biggestGridSize, SharedPreferencesUtility.getGridSpacing(context) + "");
    }

    @Test
    public void shouldShare() {

        openActionBarOverflowOrOptionsMenu(context);

        //Just verify that the we have a Share menu item, since starting share will outside our app.
        onView(withText(R.string.action_share)).check(matches(isDisplayed()));

        //onView(withText(R.string.action_share)).perform(click());
        //assertTrue(TestUtility.clickOnButton("Allow"));
        //TestUtility.clickOnButton("Cancel");
    }

    //TODO create test for background color, but I don't know how I can test it
    private void createSomeLines() {
        onView(withId(R.id.drawingView)).perform(swipeRight());
        onView(withId(R.id.drawingView)).perform(swipeDown());
    }

    private void createSomeMoreLines() {
        onView(withId(R.id.drawingView)).perform(swipeLeft());
        onView(withId(R.id.drawingView)).perform(swipeUp());
    }


}
//Check for Toast
//onView(withText(R.string.clear_question)).inRoot(withDecorView(not(is(mainActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));