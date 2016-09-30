package com.marksoft.stringart;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

/**
 * Handles the storing and retrieving of the data for this application.
 * Created by e62032 on 4/18/2016.
 */
public class DataHandler {

    private RetainedFragment dataFragment;

    public void initDataFragment(Context context, FragmentManager fragmentManager, Bundle bundle) {

        // find the retained fragment on activity restarts
        if (bundle != null) {
            dataFragment = (RetainedFragment) fragmentManager.getFragment(bundle, RetainedFragment.TAG);
        } else {
            dataFragment = (RetainedFragment) fragmentManager.findFragmentByTag(RetainedFragment.TAG);
        }

        // For the first time create the fragment and data
        if (dataFragment == null) {
            // add the fragment
            dataFragment = new RetainedFragment();
            dataFragment.setLines(SharedPreferencesUtility.getLines(context));
            dataFragment.setPoints(SharedPreferencesUtility.getPoints(context));

            fragmentManager.beginTransaction().add(dataFragment, RetainedFragment.TAG).commit();
        }
    }

    public RetainedFragment getDataFragment() {
        return dataFragment;
    }

    /**
     * Persist line and point data to the SharedPreferences.
     * This is called when the application is paused.
     * @param context
     */
    public void persistData(Context context) {
        SharedPreferencesUtility.setLines(context, dataFragment.getLines());
        SharedPreferencesUtility.setPoints(context, dataFragment.getPoints());
    }
}
