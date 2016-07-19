package com.marksoft.stringart;

import android.graphics.Point;

import java.util.Collection;
import java.util.List;

/**
 * Utility to convert a Collection of points to an array of floats,
 * so that Canvas can draw the points.
 * Created by e62032 on 4/13/2016.
 */
class PointUtility {
    /**
     * Convert Collection<Point> to float[]
     * @param points A collection of points
     * @return floats[]
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

    public static Point calculateMinSize(List<Point> points) {
        Point minPoint = null;
        if (points.iterator().hasNext()) {
            minPoint = points.iterator().next(); //Just a point to start with

            for (Point point : points) {
                if (point.x < minPoint.x) {
                    minPoint.x = point.x;
                }
                if (point.y < minPoint.y) {
                    minPoint.y = point.y;
                }
            }
            //Add a little bit extra for a border
            minPoint.x -= 20;
            minPoint.y -= 20;
        }
        return minPoint;
    }

    public static Point calculateMaxSize(List<Point> points) {
        Point maxPoint = new Point(0, 0);

        for (Point point : points) {
            if (point.x > maxPoint.x) {
                maxPoint.x = point.x;
            }
            if (point.y > maxPoint.y) {
                maxPoint.y = point.y;
            }
        }

        //Add a little bit extra for a border
        maxPoint.x +=20;
        maxPoint.y +=20;

        return maxPoint;
    }



}
