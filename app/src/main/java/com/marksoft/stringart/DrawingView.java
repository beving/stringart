package com.marksoft.stringart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
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
    private boolean drawDottedLines = true;
    private GestureDetector gestureDetector;
    private final Paint paint = new Paint();

    public DrawingView(Context context) {
        super(context);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(this.getContext(), new Gesture(this));
    }

    public Point createPoint(float x, float y) {
        Point newPoint = new Point(
                round(x, dataHandler.getDataFragment().getRoundToTheNearest()),  //round(x),
                round(y, dataHandler.getDataFragment().getRoundToTheNearest())); //round(y));

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

        //Setup Canvas
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);  //Set the color for the canvas background
        canvas.drawPaint(paint);

        //Draw Dotted Lines
        if (drawDottedLines) {

            int spacing = dataHandler.getDataFragment().getRoundToTheNearest();

            //Draw Dotted Lines along the X axis (horizontally)
            for (int y = 0; y < canvas.getHeight(); y+=spacing) {
                paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                paint.setAntiAlias(true);
                paint.setFilterBitmap(true);
                paint.setColor(0xffcccccc);
                paint.setStrokeWidth(1);
                paint.setStyle(Paint.Style.STROKE);
                paint.setPathEffect(new DashPathEffect(new float[] {10,5}, 0));
                Log.d("DrawingView ", "y: " + y + " canvas.width: " + canvas.getWidth());

                canvas.drawLine(0, y,
                        canvas.getWidth(), y,
                        paint);

                //Draw Dotted Lines along the Y axis (vertically)
                for (int x = 0; x < canvas.getWidth(); x+=spacing) {
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setAntiAlias(true);
                    paint.setFilterBitmap(true);
                    paint.setColor(0xffcccccc);
                    paint.setStrokeWidth(1);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
                    Log.d("DrawingView ", "x: " + x + " canvus.height: " + canvas.getHeight());

                    canvas.drawLine(x, 0,
                            x, canvas.getHeight(),
                            paint);
                }
            }
        }

        //Draw Points
        int lastSelectedColor = dataHandler.getDataFragment().getLastSelectedColor();
        paint.setColor(Color.BLACK); //Set the color for the points
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeWidth(dataHandler.getDataFragment().getStrokeWidth());
        canvas.drawPoints(PointUtility.toArray(getPoints()), paint);  //TODO dont do if drawing lines

        //Draw Lines
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

    //Round so that it is easier to draw.
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
        dataHandler.getDataFragment().setStrokeWidth(strokeWidth);
    }

    public void setRoundToTheNearest(int roundToTheNearest) {
        Log.d("DrawingView","setRoundToTheNearest: " + roundToTheNearest);
        //this.roundToTheNearest = roundToTheNearest;
        dataHandler.getDataFragment().setRoundToTheNearest(roundToTheNearest);
    }

    public int getRoundToTheNearest() {
        return dataHandler.getDataFragment().getRoundToTheNearest();
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

    public void setDrawDottedLines(boolean drawDottedLines) { this.drawDottedLines = drawDottedLines; }

    public boolean isDrawDottedLines() {return drawDottedLines; }
}