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

import java.util.ArrayList;
import java.util.List;

/**
 * Draw points on screen then draw lines to connect them.
 * Created by e62032 on 4/13/2016.
 */
public class DrawingView extends View {

    private DataHandler dataHandler;
    private boolean drawLines = false;
    private GestureDetector gestureDetector;
    private final Paint paint = new Paint();

    //TODO rm these two, use the datahandler for all data.
    private int strokeWidth = 4;
    private int roundToTheNearest = 50;

    public DrawingView(Context context) {
        super(context);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(this.getContext(), new Gesture(this));
    }

    public Point createPoint(float x, float y) {
        Point newPoint = new Point(
                round(x, roundToTheNearest),  //round(x),
                round(y, roundToTheNearest)); //round(y));

        if (!getPoints().contains(newPoint)) {
            getPoints().add(newPoint);
        } else {
            Log.d("DrawingView", "Point already added previously-->" + newPoint.toString());
        }
        drawLines = false;

        return newPoint;
    }

    public void createLine(Point newPoint) {

        //For now make all lines connect to our new point
        for (Point otherPoint : getPoints()) {

            Line newLine = new Line(newPoint, otherPoint, paint,
                    dataHandler.getDataFragment().getLastSelectedColor());

            if (!getLines().contains(newLine)) {
                getLines().add(newLine);
            }
        }
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
        paint.setColor(Color.WHITE);  //Set the color for the canvas background
        canvas.drawPaint(paint);

        int lastSelectedColor = dataHandler.getDataFragment().getLastSelectedColor();
        paint.setColor(lastSelectedColor); //Set the color for the points
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        canvas.drawPoints(PointUtility.toArray(getPoints()), paint);  //TODO dont do if drawing lines

        if (drawLines) {
            for (Line line : dataHandler.getDataFragment().getLines()) {
                paint.setColor(line.getColor());  //Set the color for the line
                canvas.drawLine(
                        Math.round(line.getStartPoint().x),  //starting coordinates
                        Math.round(line.getStartPoint().y),
                        Math.round(line.getEndPoint().x),    //ending coordinates
                        Math.round(line.getEndPoint().y),
                        paint);
            }
        }
    }

    public boolean drawLines() {
        Log.d("DrawingView", " drawLines");
        drawLines = true;

        if (!getLines().isEmpty()) {
            reDraw();
            return true;
        }
        return false;
    }

    public void clear() {
        dataHandler.getDataFragment().clear();
        reDraw();
    }

    //Round to the nearest 50th so that it is easier to draw.
    //ie Points are easy to draw in a straight line.
//    private int round(float number) {
//        return Math.round(Precision.round(number, -2, BigDecimal.ROUND_HALF_DOWN));
//    }

    private int round(float numberF, int roundedToNearest) {
        Log.d("DrawingView", "Number to round: " + numberF + " rounded to nearest "
                + roundedToNearest);
        int number = Math.round(numberF);
        int roundedNumber = (number + (roundedToNearest-1)) / roundedToNearest * roundedToNearest;

        return roundedNumber;
    }

    public void undoAdditionOfLastPoint() {
        if (!getPoints().isEmpty()) {
            Point pointToRemove = getPoints().get(getPoints().size() - 1);

            dataHandler.getDataFragment().getLines().removeAll(getLastLinesCreated());
            getPoints().remove(pointToRemove);

            reDraw();
        }
    }

    private List<Line> getLastLinesCreated() {
        List<Line> lastLines = new ArrayList<>();
        Point pointToRemove = getPoints().get(getPoints().size() - 1);

        for (Line line : dataHandler.getDataFragment().getLines()) {

            if (line.getEndPoint().equals(pointToRemove) ||
                    line.getStartPoint().equals(pointToRemove)) {
                lastLines.add(line);
            }
        }
        return lastLines;
    }

    //Force onDraw to be called.
    public void reDraw(){
        this.invalidate();
    }

    public void setColor(int color) {
        dataHandler.getDataFragment().setLastSelectedColor(color);
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

    private List<Point> getPoints() {
        return dataHandler.getDataFragment().getPoints();
    }

    private List<Line> getLines() {
        return dataHandler.getDataFragment().getLines();
    }

    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }
}