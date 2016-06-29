package com.marksoft.stringart;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    //Defaults
    private int lastSelectedColor = Color.RED;
    private int strokeWidth = 4;
    private int roundToTheNearest = 100;
    private boolean drawDottedLines = true;
    private boolean cutPoint = false;

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

    public void setDrawDottedLines(boolean drawDottedLines) {
        this.drawDottedLines = drawDottedLines;
    }

    public boolean isDrawDottedLines() {
        return drawDottedLines;
    }

    public boolean isCutPoint() {
        return cutPoint;
    }

    public void setCutPoint(boolean cutPoint) {
        this.cutPoint = cutPoint;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("points", (ArrayList<Point>)points); //TODO make constants for all of these
        outState.putParcelableArrayList("lines", (ArrayList<Line>)lines);

        outState.putInt("lastSelectedColor", lastSelectedColor);
        outState.putInt("strokeWidth", strokeWidth);
        outState.putInt("roundToTheNearest", roundToTheNearest);
        outState.putBoolean("drawDottedLines", drawDottedLines);
        outState.putBoolean("cutPoint", cutPoint);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            points = savedInstanceState.getParcelableArrayList("points");
            lines = savedInstanceState.getParcelableArrayList("lines");

            lastSelectedColor = savedInstanceState.getInt("lastSelectedColor");
            strokeWidth = savedInstanceState.getInt("strokeWidth");
            roundToTheNearest = savedInstanceState.getInt("roundToTheNearest");
            drawDottedLines = savedInstanceState.getBoolean("drawDottedLines");
            cutPoint = savedInstanceState.getBoolean("cutPoint");
        }
    }
}
