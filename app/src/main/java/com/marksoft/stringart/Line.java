package com.marksoft.stringart;

import android.graphics.Paint;
import android.graphics.Point;

/**A Line contains two points.  It contains a unique object to paint with.
 * Created by e62032 on 4/27/2016.
 */
public class Line {

    private Point startPoint;
    private Point endPoint;
    private Paint paint;

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
