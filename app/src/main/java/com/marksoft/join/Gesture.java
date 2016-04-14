package com.marksoft.join;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Borrowed by e62032 on 4/7/2016.
 * Orignally from:  http://www.theappguruz.com/blog/android-gestures-tutorial-in-android
 */
class Gesture extends GestureDetector.SimpleOnGestureListener {
    private DrawingView myView;

    public Gesture(DrawingView myView) {
        this.myView = myView;
    }

    public void onLongPress(MotionEvent motionEvent) {
        Log.d("Gesture.onLongPress", "X: " + motionEvent.getX());
        Log.d("Gesture.onLongPress", "Y: " + motionEvent.getY());

        myView.createPoint(motionEvent.getX(), motionEvent.getY());
    }

    public boolean onDoubleTap(MotionEvent e) {
        Log.d("Gesture.onDoubleTap", "Gesture.onDoubleTap");

        myView.drawLines();
        return false;
    }
}

