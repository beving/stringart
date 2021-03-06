package com.markbev.stringart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Draw points on screen then draw lines to connect them.
 * Created by e62032 on 4/13/2016.
 */
public class DrawingView extends View {

    //NOTE: Do not store static data here, or else it will be lost when the orientation of the screen changes.
    // Instead store data in the RetainedFragment
    private static final String TAG = "DrawingView";
    private DataHandler dataHandler;
    private Gesture gesture;
    private final Paint paint = new Paint();

    public DrawingView(Context context) {
        super(context);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gesture = new Gesture(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackGround(canvas);
        drawGridLines(canvas);
        drawPoints(canvas);
        drawLines(canvas);
    }

    public void drawBackGround(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(SharedPreferencesUtility.getBackgroundColor(getContext()));  //Set the color for the canvas background
        canvas.drawPaint(paint);
    }

    public void drawLines(Canvas canvas) {
        //Draw Lines
        for (Line line : dataHandler.getDataFragment().getLines()) {
            paint.setStrokeWidth(line.getThickness());
            paint.setStyle(Paint.Style.STROKE);

            paint.setColor(line.getColor());  //Set the color for the line
            canvas.drawLine(
                    Math.round(line.getStartPoint().x),  //starting coordinates
                    Math.round(line.getStartPoint().y),
                    Math.round(line.getEndPoint().x),    //ending coordinates
                    Math.round(line.getEndPoint().y),
                    paint);
        }
    }

    public void drawPoints(Canvas canvas) {
        //Only draw a point when there is only just one point, so the user can see it on the screen
        if (dataHandler.getDataFragment().getPoints().size() == 1) {
            paint.setColor(Color.BLACK); //Set the color for the points
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            canvas.drawPoints(PointUtility.toArray(getPoints()), paint);
        }
    }

    private void drawGridLines(Canvas canvas) {
        //Draw Dotted (Grid) Lines
        if (SharedPreferencesUtility.isGridLinesOn(getContext())) {

            int spacing = SharedPreferencesUtility.getGridSpacing(getContext());

            //Draw Dotted Lines along the X axis (horizontally)
            for (int y = 0; y < canvas.getHeight(); y += spacing) {
                paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                paint.setAntiAlias(true);
                paint.setFilterBitmap(true);
                paint.setColor(Color.LTGRAY);
                paint.setStrokeWidth(1);
                paint.setStyle(Paint.Style.STROKE);
                paint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));

                canvas.drawLine(0, y,
                        canvas.getWidth(), y,
                        paint);

                //Draw Dotted Lines along the Y axis (vertically)
                for (int x = 0; x < canvas.getWidth(); x += spacing) {
                    paint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));

                    canvas.drawLine(x, 0,
                            x, canvas.getHeight(),
                            paint);
                }
            }
        }
    }

    @NonNull
    public Point createRoundedPoint(float x, float y) {
        Point newPoint = new Point(
                round(x, SharedPreferencesUtility.getGridSpacing(getContext())),
                round(y, SharedPreferencesUtility.getGridSpacing(getContext())));

        if (!getPoints().contains(newPoint)) {
            getPoints().add(newPoint);
        }
        return newPoint;
    }

//    @NonNull
//    private Point createRoundedPoint(float x, float y) {
//        return new Point(
//                round(x, SharedPreferencesUtility.getGridSpacing(getContext())),
//                round(y, SharedPreferencesUtility.getGridSpacing(getContext())));
//    }

    public boolean cutPoint(float x, float y) {

        if (dataHandler.getDataFragment().isCutPoint()) {
            Point pointToRemove = createRoundedPoint(x, y);
            List<Line> linesToDelete = new ArrayList<>();

            for (Line line : dataHandler.getDataFragment().getLines()) {
                if (line.getEndPoint().equals(pointToRemove) ||
                        line.getStartPoint().equals(pointToRemove)) {
                    linesToDelete.add(line);
                }
            }
            dataHandler.getDataFragment().getLines().removeAll(linesToDelete);

            List<Point> pointsToRemove = new ArrayList<>();
            for (Point point : dataHandler.getDataFragment().getPoints()) {
                if (point.equals(pointToRemove)) {
                    pointsToRemove.add(point);
                }
            }
            dataHandler.getDataFragment().getPoints().removeAll(pointsToRemove);
            dataHandler.getDataFragment().setCutPoint(false);

            return true;
        }
        return false;
    }

    public void createLine(Point newPoint) {

        //For now make all lines connect to our new point
        for (Point otherPoint : getPoints()) {

            Line newLine = new Line(newPoint, otherPoint,
                    SharedPreferencesUtility.getLineColor(getContext()),
                    SharedPreferencesUtility.getStrokeWidth(getContext())
            );

            if (!getLines().contains(newLine)) {
                getLines().add(newLine);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        gesture.onTouch(motionEvent);
        return true;
    }

    public void clear() {
        dataHandler.getDataFragment().clear();
        SharedPreferencesUtility.clearLinesAndPoints(getContext());
    }

    //Round so that it is easier to draw.
    private int round(float numberF, int roundedToNearest) {
        return roundedToNearest * (Math.round(numberF / roundedToNearest));
    }

    public boolean undoAdditionOfLastPoint() {
        if (!getPoints().isEmpty()) {
            Point pointToRemove = getPoints().get(getPoints().size() - 1);

            dataHandler.getDataFragment().getLines().removeAll(getLastLinesCreated());
            getPoints().remove(pointToRemove);
        } else {
            return false;
        }
        return true;
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

    public void cutPoint() {
        dataHandler.getDataFragment().setCutPoint(true);
    }

    public void reDraw() {
        this.invalidate();
    }

    //For any action that has to be selected first then worked on, this method will undo the action previously selected.
    public void undoPreviousAction(int itemId) {
        if (itemId != R.id.action_cut) {
            dataHandler.getDataFragment().setCutPoint(false);
        }
    }

    protected List<Point> getPoints() {
        return dataHandler.getDataFragment().getPoints();
    }

    protected List<Line> getLines() {
        return dataHandler.getDataFragment().getLines();
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }
}