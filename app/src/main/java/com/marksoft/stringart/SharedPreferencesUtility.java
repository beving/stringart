package com.marksoft.stringart;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.preference.PreferenceManager;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility to help handle SharedPreferences.
 * Created by e62032 on 9/21/2016.
 */
public class SharedPreferencesUtility {

    public static final String PREF_NAME = "prefName";
    public static final String STROKE_WIDTH = "strokeWidth";
    public static final String GRID_SPACING = "gridSpacing";
    public static final String GRID_LINES = "gridLines";
    public static final String LINE_COLOR = "lineColor";
    public static final String BACKGROUND_COLOR = "backgroundColor";
    public static final String LINES = "lines";
    public static final String POINTS = "points";

    public static SharedPreferences init(Context context) {
        return context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
    }

    public static int getStrokeWidth(Context context) {
        SharedPreferences sharedPreferences = init(context);
        return sharedPreferences.getInt(STROKE_WIDTH, 2);  //Default is 2
    }

    public static void setStrokeWidth(Context context, int strokeWidth) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.putInt(STROKE_WIDTH, strokeWidth);
        editor.commit();
    }

    public static int getGridSpacing(SharedPreferences sharedPreferences) {
        return sharedPreferences.getInt(GRID_SPACING, 100);  //Default is 100
    }

    public static int getGridSpacing(Context context) {
        SharedPreferences sharedPreferences = init(context);
        return sharedPreferences.getInt(GRID_SPACING, 100);  //Default is 100
    }

    public static void setGridSpacing(Context context, Integer gridSpacing) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.putInt(GRID_SPACING, gridSpacing);
        editor.commit();
    }

    public static boolean isGridLinesOn(SharedPreferences sharedPreferences) {
        return sharedPreferences.getBoolean(GRID_LINES, true);
    }

    public static void setGridLines(Context context, boolean gridLines) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.putBoolean(GRID_LINES, gridLines);
        editor.commit();
    }

    public static int getLineColor(SharedPreferences sharedPreferences) {
        return sharedPreferences.getInt(LINE_COLOR, Color.RED);
    }

    public static void setLineColor(Context context, int lineColor) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.putInt(LINE_COLOR, lineColor);
        editor.commit();
    }

    public static int getBackgroundColor(SharedPreferences sharedPreferences) {
        return sharedPreferences.getInt(BACKGROUND_COLOR, Color.WHITE);
    }

    public static void setBackgroundColor(Context context, int backgroundColor) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.putInt(BACKGROUND_COLOR, backgroundColor);
        editor.commit();
    }

    public static void addLine(Context context, Line line) { //TODO rm is not used
        List<Line> lines = getLines(context);
        lines.add(line);
        setLines(context, lines);
    }

    public static void setLines(Context context, List<Line> lines) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        Gson gson = new Gson();

        String json = gson.toJson(lines);

        editor.putString(LINES, json);
        editor.commit();
    }

    public static List<Line> getLines(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(LINES, "");
        Type type = new TypeToken<ArrayList<Line>>() {}.getType();
        List<Line> lines = gson.fromJson(json, type);

        if (lines == null) {
            lines = new ArrayList<Line>();
        }
        return lines;
    }

    public static List<Point> getPoints(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        String json = sharedPrefs.getString(POINTS, "");
        Type type = new TypeToken<ArrayList<Point>>() {}.getType();
        List<Point> points = gson.fromJson(json, type);

        if (points == null) {
            points = new ArrayList<Point>();
        }
        return points;
    }

    public static void setPoints(Context context, List<Point> points) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        Gson gson = new Gson();

        String json = gson.toJson(points);

        editor.putString(POINTS, json);

        editor.putString("TEST!", "TEST123");
        editor.commit();
    }

    public static void clear(SharedPreferences sharedPreferences) {
        sharedPreferences.edit().clear();
        sharedPreferences.edit().commit();
    }
}
