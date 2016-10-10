package com.marksoft.stringart;

import android.graphics.Point;

import java.util.Collection;

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
/*
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

        startHeight-= MARGIN_SZ;
        startWidth-= MARGIN_SZ;
        endHeight+= MARGIN_SZ;
        endWidth+= MARGIN_SZ;

        return Bitmap.createBitmap(
                bmp,
                startWidth,
                startHeight,
                endWidth - startWidth,
                endHeight - startHeight);

    }*/

}
