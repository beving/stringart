package com.markbev.stringart;

import android.graphics.Point;
import android.support.annotation.NonNull;

import java.util.Collection;

/**
 * Utility to convert a Collection of points to an array of floats,
 * so that Canvas can draw the points.
 * Created by e62032 on 4/13/2016.
 */

class PointUtility {

    /**
     * Convert Collection<Point> to float[]
     *
     * @param points A collection of points
     * @return floats[]
     */
    public static float[] toArray(@NonNull Collection<Point> points) {
        int size = points.size();
        float[] returnValue = new float[size * 2];
        int i = 0;
        for (Point point : points) {
            returnValue[i] = point.x;
            i++;
            returnValue[i] = point.y;
            i++;
        }
        return returnValue;
    }

}
