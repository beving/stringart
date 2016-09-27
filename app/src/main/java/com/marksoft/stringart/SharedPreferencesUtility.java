package com.marksoft.stringart;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

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

    public static SharedPreferences init(Context context) {
        return context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
    }

    public static int getStrokeWidth(SharedPreferences sharedPreferences) {
        return sharedPreferences.getInt(STROKE_WIDTH, 2);  //Default is 2
    }

    public static void setStrokeWidth (Context context, int strokeWidth) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.putInt(STROKE_WIDTH, strokeWidth);
        editor.commit();
    }

    public static int getGridSpacing(SharedPreferences sharedPreferences) {
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
}
