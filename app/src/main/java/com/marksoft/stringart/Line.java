package com.marksoft.stringart;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**A Line contains two points.  It contains a unique object to paint with.
 *    Therefore giving a line unique attributes like: a different color, size, solid/dotted, etc.
 * Created by e62032 on 4/27/2016.
 */
public class Line {

    private Point startPoint;
    private Point endPoint;
    private Paint paint;
    private int color = Color.RED; //Default

    public Line(Point startPoint, Point endPoint, Paint paint, int color) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.paint = paint;
        this.color = color;
    }

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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
