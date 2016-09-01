package com.marksoft.stringart;

import android.app.Fragment;
import android.graphics.Color;
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

    public static String TAG = "RetainedFragment";

    // data objects we want to retain
    private List<Point> points = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();

    //Keys for Saving State in the Bundle
    private static final String POINTS = "points";
    private static final String LINES = "lines";
    private static final String LAST_SELECTED_COLOR = "lastSelectedColor";
    private static final String STROKE_WIDTH = "strokeWidth";
    private static final String ROUND_TO_THE_NEAREST = "roundToTheNearest";
    private static final String DRAW_DOTTED_LINES = "drawGridLines";
    private static final String CUT_POINT = "cutPoint";

    //Defaults
    private int lastSelectedColor = Color.RED;
    private int strokeWidth = 2;
    private int roundToTheNearest = 100;
    private boolean drawGridLines = true;
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

    public int getLastSelectedColor() {
        return lastSelectedColor;
    }

    public void setLastSelectedColor(int lastSelectedColor) {
        this.lastSelectedColor = lastSelectedColor;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getRoundToTheNearest() {
        return roundToTheNearest;
    }

    public void setRoundToTheNearest(int roundToTheNearest) {
        this.roundToTheNearest = roundToTheNearest;
    }

    public void setDrawGridLines(boolean drawGridLines) {
        this.drawGridLines = drawGridLines;
    }

    public boolean isDrawGridLines() {
        return drawGridLines;
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

    public void setPermissableToShare(boolean permissableToShare) {
        this.permissibleToShare = permissableToShare;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(POINTS, (ArrayList<Point>)points); //TODO make constants for all of these
        outState.putParcelableArrayList(LINES, (ArrayList<Line>)lines);

        outState.putInt(LAST_SELECTED_COLOR, lastSelectedColor);
        outState.putInt(STROKE_WIDTH, strokeWidth);
        outState.putInt(ROUND_TO_THE_NEAREST, roundToTheNearest);
        outState.putBoolean(DRAW_DOTTED_LINES, drawGridLines);
        outState.putBoolean(CUT_POINT, cutPoint);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            points = savedInstanceState.getParcelableArrayList(POINTS);
            lines = savedInstanceState.getParcelableArrayList(LINES);

            lastSelectedColor = savedInstanceState.getInt(LAST_SELECTED_COLOR);
            strokeWidth = savedInstanceState.getInt(STROKE_WIDTH);
            roundToTheNearest = savedInstanceState.getInt(ROUND_TO_THE_NEAREST);
            drawGridLines = savedInstanceState.getBoolean(DRAW_DOTTED_LINES);
            cutPoint = savedInstanceState.getBoolean(CUT_POINT);
        }
    }
}
