package com.marksoft.stringart;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Borrowed by e62032 on 4/7/2016.
 * Originally from:  http://www.theappguruz.com/blog/android-gestures-tutorial-in-android
 */
public class Gesture {
    private final DrawingView myView;

    public Gesture(DrawingView myView) {
        this.myView = myView;
    }

    public boolean onTouch(MotionEvent event) {
        try {
            Log.d("Gesture", "Gesture.onTouch event.getAction: " + event.getAction());
            switch (event.getAction()) {

                case MotionEvent.ACTION_UP:
                    if (myView.cutPoint(event.getX(), event.getY())) {
                        myView.reDraw();
                        return true;
                    }
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    myView.createLine(myView.createPoint(event.getX(), event.getY()));
                    Log.d("Gesture", "Gesture.getAction x: " + event.getX() +
                            "    y: " + event.getY());
                    myView.reDraw();
                    return true;
                default:
                    return false;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

