package com.marksoft.stringart;

import android.app.FragmentManager;

/**
 * Handles the storing and retrieving of the data for this application.
 * Created by e62032 on 4/18/2016.
 */
public class DataHandler {

    private RetainedFragment dataFragment;

    public void handlePoints(DrawingView drawingView, FragmentManager fragmentManager) {

        // find the retained fragment on activity restarts
        dataFragment = (RetainedFragment) fragmentManager.findFragmentByTag("pointData");

        // For first time create the fragment and data
        if (dataFragment == null) {
            // add the fragment
            dataFragment = new RetainedFragment();
            fragmentManager.beginTransaction().add(dataFragment, "pointData").commit();
        }
    }

    public RetainedFragment getDataFragment() { return dataFragment; }

    public void setDataFragment(RetainedFragment dataFragment) {this.dataFragment = dataFragment; }

}
