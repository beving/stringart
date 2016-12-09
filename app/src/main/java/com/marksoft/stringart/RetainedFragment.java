package com.marksoft.stringart;

import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Retains the point data during a onCreate.
 * This can occur when the device's orientation changes.
 * Created by e62032 on 4/15/2016.
 */
public class RetainedFragment extends Fragment {

    public static final String TAG = "RetainedFragment";

    // data objects we want to retain
    private List<Point> points = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();

    //Keys for Saving State in the Bundle
    private static final String POINTS = "points";
    private static final String LINES = "lines";
    private static final String CUT_POINT = "cutPoint";

    //Defaults
    private boolean cutPoint = false;
    private boolean permissibleToShare = false;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void clear() {
        points = new ArrayList<>();
        lines = new ArrayList<>();
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public boolean isCutPoint() {
        return cutPoint;
    }

    public void setCutPoint(boolean cutPoint) {
        this.cutPoint = cutPoint;
    }

    public boolean isPermissibleToShare() {
        return permissibleToShare;
    }

    public void setPermissableToShare(boolean share) {
        this.permissibleToShare = share;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(POINTS, (ArrayList<Point>)points);
        outState.putParcelableArrayList(LINES, (ArrayList<Line>)lines);
        outState.putBoolean(CUT_POINT, cutPoint);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            points = savedInstanceState.getParcelableArrayList(POINTS);
            lines = savedInstanceState.getParcelableArrayList(LINES);
            cutPoint = savedInstanceState.getBoolean(CUT_POINT);
        }
    }
}
