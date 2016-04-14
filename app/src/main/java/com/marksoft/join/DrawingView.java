package com.marksoft.join;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.apache.commons.math3.util.Precision;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Draw points on screen then draw lines to connect them.
 * Created by e62032 on 4/13/2016.
 */
public class DrawingView extends View {
    private Set<Point> points = new HashSet<>();
    public boolean drawLines = false;
    private GestureDetector gestureDetector;

    public DrawingView(Context context) { //TODO see if this is needed since we have the one below.
        super(context);
    }

    public DrawingView(Context context, AttributeSet attrs) {

        super(context, attrs);

        gestureDetector = new GestureDetector(this.getContext(), new Gesture(this));
    }

    public void createPoint(float x, float y) {
        points.add(new Point(round(x), round(y)));
        this.invalidate(); //Force onDraw to be called.
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

//        if (gestureDetector == null) {
//            gestureDetector = new GestureDetector(this.getContext(), new Gesture(this));
//        }
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

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
        drawLines = false;
    }

    public void drawLines() {
        drawLines = true;

        this.invalidate(); //Force onDraw to be called.
    }

    public void clear() {
        //Clear out the points and lines by emptying the points out.
        points = new HashSet<>();
        this.invalidate(); //Force onDraw to be called.
    }

    //Round to the nearest 50th so that it is easier to draw.
    //ie Points are easy to draw in a straight line.
    private int round(float number) {
        return Math.round(Precision.round(number, -2, BigDecimal.ROUND_HALF_DOWN));
    }

    public Set<Point> getPoints() {
        return points;
    }
}