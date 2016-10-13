package com.marksoft.stringart;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Common utilities used for testing.
 */
public class TestUtility {

    public static void rotateScreen(ActivityTestRule activityTestRule) {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = activityTestRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static void clickOnButton(String label) {

        UiObject allowPermissions = getUiObject(label);
        if (allowPermissions.exists()) {
            try {
                allowPermissions.click();
            } catch (UiObjectNotFoundException e) {
                throw new RuntimeException("There are no dialog with " + label +
                        " to interact with ");
            }
        }
    }

    public static boolean doesExist(String label) {
        UiObject uiObject = getUiObject(label);
        return uiObject.exists();
    }

    public static UiObject getUiObject(String label) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        return device.findObject(new UiSelector().text(label));
    }

    /**
     * When using a tablet the menus strech accross the screen more, so the tests have to adapt
     * to this changing behavior.
     * Not an ellegant solution, since we have to catch an exception, but the best that I could find.
     * See an explanation at:
     * http://stackoverflow.com/questions/20807131/espresso-return-boolean-if-view-exists
     * @param button
     * @param id
     * @return
     */
    public static void clickOnViaTextOrId(Context context, String button, final int id) {
        try {
            onView(withId(id)).perform(click());

        } catch (NoMatchingViewException e) {

            openActionBarOverflowOrOptionsMenu(context);
            onView(withText(button)).perform(click());
        }
    }
}