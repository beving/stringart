package com.marksoft.stringart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.stephentuso.welcome.ui.WelcomeActivity;

/**
 * This is a copy of WelcomeScreenHelper, that had to be done since there is a bug in that code that
 * did not allow the selectLineColor(Bundle) to work properly.  The Welcome Screen would not come up.
 * @see com.stephentuso.welcome.WelcomeScreenHelper
 * Created by e62032 on 7/20/2016.
 */
class MyWelcomeScreenHelper {

    private static final int DEFAULT_WELCOME_SCREEN_REQUEST = 1;

    private static final String KEY_WELCOME_SCREEN_STARTED = "com.stephentuso.welcome.welcome_screen_started";

    private final Activity mActivity;
    private final Class<? extends WelcomeActivity> mActivityClass;
    private boolean welcomeScreenStarted = false;

    public MyWelcomeScreenHelper(Activity activity, Class<? extends WelcomeActivity> activityClass) {
        mActivity = activity;
        mActivityClass = activityClass;
    }

    private boolean getWelcomeScreenStarted(Bundle savedInstanceState) {
        if (!welcomeScreenStarted) {
            welcomeScreenStarted = savedInstanceState != null && savedInstanceState.getBoolean(KEY_WELCOME_SCREEN_STARTED, false);
        }
        return welcomeScreenStarted;
    }

    private boolean shouldShow(Bundle savedInstanceState) {
        return !getWelcomeScreenStarted(savedInstanceState);
                // !SharedPreferencesHelper.welcomeScreenCompleted(mActivity, WelcomeUtils.getKey(mActivityClass));//This was the bug that caused the problem!
    }

//    public boolean show(Bundle savedInstanceState) {
//        return show(savedInstanceState);
//    }

    /**
     * Shows the welcome screen if it hasn't already been started or completed yet
     * @param savedInstanceState Saved instance state Bundle
     * @return true if the welcome screen was shown, false if it wasn't
     */
    private boolean show(Bundle savedInstanceState) {
        boolean shouldShow = shouldShow(savedInstanceState);
        if (shouldShow) {
            welcomeScreenStarted = true;
            startActivity(MyWelcomeScreenHelper.DEFAULT_WELCOME_SCREEN_REQUEST);
        }
        return shouldShow;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_WELCOME_SCREEN_STARTED, welcomeScreenStarted);
    }

    private void startActivity(int requestCode) {
        Intent intent = new Intent(mActivity, mActivityClass);
        mActivity.startActivityForResult(intent, requestCode);
    }

}
