package com.markbev.stringart;

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
 * Utility to handle SharedPreferences.
 * Created by e62032 on 9/21/2016.
 */
class SharedPreferencesUtility {

    private static final String PREF_NAME = "prefName";
    private static final String STROKE_WIDTH = "strokeWidth";
    private static final String GRID_SPACING = "gridSpacing";
    private static final String GRID_LINES = "gridLines";
    private static final String LINE_COLOR = "lineColor";
    private static final String BACKGROUND_COLOR = "backgroundColor";
    private static final String LINES = "lines";
    private static final String POINTS = "points";

    public static final int DEFAULT_STROKE_WIDTH = 2;
    public static final int DEFAULT_GRID_SPACING = 100;
    public static final boolean DEFAULT_GRID_LINES = true;
    public static final int DEFAULT_LINE_COLOR = Color.RED;
    public static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;

    private static SharedPreferences init(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static int getStrokeWidth(Context context) {
        SharedPreferences sharedPreferences = init(context);
        return sharedPreferences.getInt(STROKE_WIDTH, DEFAULT_STROKE_WIDTH);
    }

    public static void setStrokeWidth(Context context, int strokeWidth) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.putInt(STROKE_WIDTH, strokeWidth);
        editor.apply();
    }

    public static int getGridSpacing(Context context) {
        SharedPreferences sharedPreferences = init(context);
        return sharedPreferences.getInt(GRID_SPACING, DEFAULT_GRID_SPACING);
    }

    public static void setGridSpacing(Context context, Integer gridSpacing) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.putInt(GRID_SPACING, gridSpacing);
        editor.apply();
    }

    public static boolean isGridLinesOn(Context context) {
        SharedPreferences sharedPreferences = init(context);
        return sharedPreferences.getBoolean(GRID_LINES, DEFAULT_GRID_LINES);
    }

    public static void setGridLines(Context context, boolean gridLines) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.putBoolean(GRID_LINES, gridLines);
        editor.apply();
    }

    public static int getLineColor(Context context) {
        SharedPreferences sharedPreferences = init(context);
        return sharedPreferences.getInt(LINE_COLOR, DEFAULT_LINE_COLOR);
    }

    public static void setLineColor(Context context, int lineColor) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.putInt(LINE_COLOR, lineColor);
        editor.apply();
    }

    public static int getBackgroundColor(Context context) {
        SharedPreferences sharedPreferences = init(context);
        return sharedPreferences.getInt(BACKGROUND_COLOR, DEFAULT_BACKGROUND_COLOR);
    }

    public static void setBackgroundColor(Context context, int backgroundColor) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.putInt(BACKGROUND_COLOR, backgroundColor);
        editor.apply();
    }

    public static void setLines(Context context, List<Line> lines) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        Gson gson = new Gson();

        String json = gson.toJson(lines);

        editor.putString(LINES, json);
        editor.apply();
    }

    public static List<Line> getLines(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(LINES, "");
        Type type = new TypeToken<ArrayList<Line>>() {
        }.getType();
        List<Line> lines = gson.fromJson(json, type);

        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public static List<Point> getPoints(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        String json = sharedPrefs.getString(POINTS, "");
        Type type = new TypeToken<ArrayList<Point>>() {
        }.getType();
        List<Point> points = gson.fromJson(json, type);

        if (points == null) {
            points = new ArrayList<>();
        }
        return points;
    }

    public static void setPoints(Context context, List<Point> points) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(points);
        editor.putString(POINTS, json);
        editor.apply();
    }

    public static void clearLinesAndPoints(Context context) {

        setPoints(context, new ArrayList<Point>());
        setLines(context, new ArrayList<Line>());

        SharedPreferences sharedPreferences = SharedPreferencesUtility.init(context);
        sharedPreferences.edit().apply();
    }

    public static boolean clear(Context context) {
        SharedPreferences sharedPreferences = SharedPreferencesUtility.init(context);
        return sharedPreferences.edit().clear().commit();
    }
}
