package com.markbev.stringart;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Borrowed by e62032 on 4/7/2016.
 * Originally from:  http://www.theappguruz.com/blog/android-gestures-tutorial-in-android
 */
class Gesture {
    private final DrawingView myView;

    public Gesture(DrawingView myView) {
        this.myView = myView;
    }

    public void onTouch(MotionEvent event) {
        try {
            switch (event.getAction()) {

                case MotionEvent.ACTION_UP:
                    if (myView.cutPoint(event.getX(), event.getY())) {
                        myView.reDraw();
                        return;
                    }
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    myView.createLine(myView.createPoint(event.getX(), event.getY()));
                    myView.reDraw();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

