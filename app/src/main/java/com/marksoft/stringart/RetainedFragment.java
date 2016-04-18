package com.marksoft.stringart;

import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;

import java.util.Collection;
import java.util.Set;

/**
 * Retains the point data during a onCreate.
 * This can occur when the device's orientation changes.
 * Created by e62032 on 4/15/2016.
 */
public class RetainedFragment extends Fragment {

    // data object we want to retain
    private Set<Point> points;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public Set<Point> getPoints() {
        return points;
    }

    public void setPoints(Set<Point> points) {
        this.points = points;
    }
}
