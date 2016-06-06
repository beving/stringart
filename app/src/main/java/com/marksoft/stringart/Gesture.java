package com.marksoft.stringart;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Borrowed by e62032 on 4/7/2016.
 * Originally from:  http://www.theappguruz.com/blog/android-gestures-tutorial-in-android
 */
class Gesture extends GestureDetector.SimpleOnGestureListener {
    private final DrawingView myView;

    public Gesture(DrawingView myView) {
        this.myView = myView;
    }

    public void onLongPress(MotionEvent motionEvent) {
        Log.d("Gesture.onLongPress", "X Y: " + motionEvent.getX()+ " " + motionEvent.getY());

        myView.createLine(
                myView.createPoint(
                        motionEvent.getX(), motionEvent.getY()));
        myView.reDraw();
    }

    public boolean onDoubleTap(MotionEvent e) {
        Log.d("Gesture.onDoubleTap", "Gesture.onDoubleTap");

        myView.drawLines();
        return false;
    }
}

