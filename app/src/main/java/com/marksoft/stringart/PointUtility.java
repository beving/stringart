package com.marksoft.stringart;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

import java.util.Collection;
import java.util.List;

/**
 * Utility to convert a Collection of points to an array of floats,
 * so that Canvas can draw the points.
 * Created by e62032 on 4/13/2016.
 */

public class PointUtility {
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
        maxPoint.x +=80;
        maxPoint.y +=80;

        return maxPoint;
    }

    public static Bitmap trimBitmap(Bitmap bmp, int backgroundColor) {
        int imgHeight = bmp.getHeight();
        int imgWidth  = bmp.getWidth();

        //TRIM WIDTH - LEFT
        int startWidth = 0;
        for(int x = 0; x < imgWidth; x++) {
            if (startWidth == 0) {
                for (int y = 0; y < imgHeight; y++) {
                    if (bmp.getPixel(x, y) != backgroundColor) {  //TODO if we ever need to
                        startWidth = x;
                        break;
                    }
                }
            } else break;
        }

        //TRIM WIDTH - RIGHT
        int endWidth  = 0;
        for(int x = imgWidth - 1; x >= 0; x--) {
            if (endWidth == 0) {
                for (int y = 0; y < imgHeight; y++) {
                    if (bmp.getPixel(x, y) != backgroundColor) {  //Color.TRANSPARENT
                        endWidth = x;
                        break;
                    }
                }
            } else break;
        }

        //TRIM HEIGHT - TOP
        int startHeight = 0;
        for(int y = 0; y < imgHeight; y++) {
            if (startHeight == 0) {
                for (int x = 0; x < imgWidth; x++) {
                    if (bmp.getPixel(x, y) != backgroundColor) {
                        startHeight = y;
                        break;
                    }
                }
            } else break;
        }

        //TRIM HEIGHT - BOTTOM
        int endHeight = 0;
        for(int y = imgHeight - 1; y >= 0; y--) {
            if (endHeight == 0 ) {
                for (int x = 0; x < imgWidth; x++) {
                    if (bmp.getPixel(x, y) != backgroundColor) {
                        endHeight = y;
                        break;
                    }
                }
            } else break;
        }

        startHeight-=30;
        startWidth-=30;

        endHeight+=30;
        endWidth+=30;

        int x =  startWidth;
        int y = startHeight;
        int width = endWidth - startWidth;
        int height = endHeight - startHeight;

        Log.d("PointUtility", "X: " + x + " Y: " + y + " width: " + width + " height: " + height + " endWidth: " + endWidth + " endHeight: " + endHeight);
        //java.lang.IllegalArgumentException: x + width must be <= bitmap.width()

        Log.d("PointUtility", " bmp.width: " + bmp.getWidth() + " bmp.height: " + bmp.getHeight());



        return Bitmap.createBitmap(
                bmp,
                x,
                y,
                width,
                height        );

    }

}
