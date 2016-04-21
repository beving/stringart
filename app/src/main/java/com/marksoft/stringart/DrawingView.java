package com.marksoft.stringart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.apache.commons.math3.util.Precision;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Draw points on screen then draw lines to connect them.
 * Created by e62032 on 4/13/2016.
 */
public class DrawingView extends View {
    private List<Point> points = new ArrayList<>();
    private boolean drawLines = false;
    private GestureDetector gestureDetector;
    private final Paint paint = new Paint();
    private int color = Color.RED; //Default
    private int strokeWidth = 2;
    private int roundToTheNearest = 50;

    public DrawingView(Context context) {
        super(context);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(this.getContext(), new Gesture(this));
    }

    public void createPoint(float x, float y) {
        Point newPoint = new Point(
                round(x, roundToTheNearest),  //round(x),
                round(y, roundToTheNearest)); //round(y));
        if (!points.contains(newPoint)) {
            points.add(newPoint);
        } else {
            Log.d("DrawingView", "Point already added previously-->" + newPoint.toString());
        }
        this.invalidate(); //Force onDraw to be called.

        drawLines = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        canvas.drawPoints(PointUtility.toArray(points), paint);

        if (drawLines) {
            for (Point outerPoint: points) {
                for (Point innerPoint : points) {

                    canvas.drawLine(
                            Math.round(outerPoint.x),  //starting coordinates
                            Math.round(outerPoint.y),
                            Math.round(innerPoint.x),  //ending coordinates
                            Math.round(innerPoint.y), paint);
                }
            }
        }
    }

    public boolean drawLines() {
        Log.d("DrawingView", " drawLines");
        drawLines = true;
        if (!getPoints().isEmpty()) {
            this.invalidate(); //Force onDraw to be called.
            return true;
        }
        return false;
    }

    public void clear() {
        //Clear out the points and lines by emptying the points out.
        points = new ArrayList<>();
        this.invalidate(); //Force onDraw to be called.
    }

    //Round to the nearest 50th so that it is easier to draw.
    //ie Points are easy to draw in a straight line.
//    private int round(float number) {
//        return Math.round(Precision.round(number, -2, BigDecimal.ROUND_HALF_DOWN));
//    }

    private int round(float numberF, int roundedToNearest) {

        Log.d("DrawingView","Number to round: " + numberF + " rounded to nearest "
                + roundedToNearest);


        int number = Math.round(numberF);
        int roundedNumber = (number + (roundedToNearest-1)) / roundedToNearest * roundedToNearest;

        return roundedNumber;
    }

    public void undoAdditionOfLastPoint() {
        if (!points.isEmpty()) {
            points.remove(points.size() - 1);
        }
        this.invalidate();
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setRoundToTheNearest(int roundToTheNearest) {
        Log.d("DrawingView","setRoundToTheNearest: " + roundToTheNearest);
        this.roundToTheNearest = roundToTheNearest;
    }

    public int getRoundToTheNearest() {
        Log.d("DrawingView","getRoundToTheNearest: " + roundToTheNearest);
        return roundToTheNearest;
    }
}