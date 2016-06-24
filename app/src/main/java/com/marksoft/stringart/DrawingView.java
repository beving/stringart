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

    //NOTE: Do not store data here, or else it will be lost when the orientation of the screen changes.
    private DataHandler dataHandler;
    private boolean drawLines = false;  //TODO mv to datahandler
    private GestureDetector gestureDetector;
    private Gesture gesture;
    private final Paint paint = new Paint();


    public DrawingView(Context context) {
        super(context);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gesture = new Gesture(this);
        gestureDetector = new GestureDetector(this.getContext(), gesture);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackGround(canvas);
        drawGridLines(canvas);
        drawPoints(canvas);
        drawLines(canvas);
    }

    private void drawBackGround(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);  //Set the color for the canvas background
        canvas.drawPaint(paint);
    }

    private void drawLines(Canvas canvas) {
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

    private void drawPoints(Canvas canvas) {
        //Draw Points
        int lastSelectedColor = dataHandler.getDataFragment().getLastSelectedColor();
        paint.setColor(Color.BLACK); //Set the color for the points
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeWidth(dataHandler.getDataFragment().getStrokeWidth());
        canvas.drawPoints(PointUtility.toArray(getPoints()), paint);  //TODO dont do if drawing lines
    }

    private void drawGridLines(Canvas canvas) {
        //Draw Dotted Lines
        if (dataHandler.getDataFragment().isDrawDottedLines()) {

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
                //Log.d("DrawingView ", "y: " + y + " canvas.width: " + canvas.getWidth());

                canvas.drawLine(0, y,
                        canvas.getWidth(), y,
                        paint);

                //Draw Dotted Lines along the Y axis (vertically)
                for (int x = 0; x < canvas.getWidth(); x+=spacing) {
                    paint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));

                    canvas.drawLine(x, 0,
                            x, canvas.getHeight(),
                            paint);
                }
            }
        }
    }

    public boolean drawLines() {
        Log.d("DrawingView", " drawLines");
        drawLines = true;

        if (!getLines().isEmpty()) {
            return true;
        }
        return false;
    }

    public Point createPoint(float x, float y) {
        Point newPoint = new Point(
                round(x, dataHandler.getDataFragment().getRoundToTheNearest()),
                round(y, dataHandler.getDataFragment().getRoundToTheNearest()));

        if (!getPoints().contains(newPoint)) {
            getPoints().add(newPoint);
        } else {
            Log.d("DrawingView", "Point already added previously-->" + newPoint.toString());
        }
        //drawLines = false;
        drawLines = true;

        return newPoint;
    }
    public Point deletePoint(float x, float y) {
        Point newPoint = new Point(
                round(x, dataHandler.getDataFragment().getRoundToTheNearest()),
                round(y, dataHandler.getDataFragment().getRoundToTheNearest()));

        if (getPoints().contains(newPoint)) {
            getPoints().remove(newPoint);
            Log.d("DrawingView", "Point deleted-->" + newPoint.toString());
        } else {
            Log.d("DrawingView", "Point not deleted because it could not be found-->" + newPoint.toString());
        }
        //drawLines = false;
        drawLines = true;

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

        gesture.onTouch(motionEvent);
        return true;
    }

    public void clear() {
        dataHandler.getDataFragment().clear();
    }

    //Round so that it is easier to draw.
    private int round(float numberF, int roundedToNearest) {
        return roundedToNearest*(Math.round(numberF/roundedToNearest));
    }

    public void undoAdditionOfLastPoint() {
        if (!getPoints().isEmpty()) {
            Point pointToRemove = getPoints().get(getPoints().size() - 1);

            dataHandler.getDataFragment().getLines().removeAll(getLastLinesCreated());
            getPoints().remove(pointToRemove);
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

    private List<Point> getPoints() {
        return dataHandler.getDataFragment().getPoints();
    }

    private List<Line> getLines() {
        return dataHandler.getDataFragment().getLines();
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }
    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }
}