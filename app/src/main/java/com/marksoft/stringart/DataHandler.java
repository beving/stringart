package com.marksoft.stringart;

import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Handles the storing and retrieving of the data for this application.
 * //TODO consider removing this class
 * Created by e62032 on 4/18/2016.
 */
public class DataHandler {

    private RetainedFragment dataFragment;

    public void initDataFragment(FragmentManager fragmentManager, Bundle bundle) {

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
            fragmentManager.beginTransaction().add(dataFragment, RetainedFragment.TAG).commit();
        }
    }

    public RetainedFragment getDataFragment() {
        return dataFragment;
    }

}
