package com.marksoft.join;

import android.graphics.Point;

import java.util.Collection;

/**
 * Created by e62032 on 4/13/2016.
 */
public class PointUtility {
    /**
     * Convert Collection<Point> to float[]
     * @param points
     * @return
     */
    public static float[] toArray(Collection<Point> points) {
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
