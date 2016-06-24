package com.marksoft.stringart;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Calendar;

/**
 * Borrowed by e62032 on 4/7/2016.
 * Originally from:  http://www.theappguruz.com/blog/android-gestures-tutorial-in-android
 */
class Gesture extends GestureDetector.SimpleOnGestureListener {
    private final DrawingView myView;

    public Gesture(DrawingView myView) {
        this.myView = myView;
    }

//    @Override
//    public void onLongPress(MotionEvent motionEvent) {
//        Log.d("Gesture.onLongPress", "X Y: " + motionEvent.getX()+ " " + motionEvent.getY());
//
//        myView.createLine(
//                myView.createPoint(
//                        motionEvent.getX(), motionEvent.getY()));
//
//        myView.reDraw();
//    }
//
//    @Override
//    public boolean onDoubleTap(MotionEvent event) {
//        Log.d("Gesture.onDoubleTap", "Gesture.onDoubleTap");
//
//        myView.deletePoint(event.getX(), event.getY());
//        myView.reDraw();
//
//        return false;
//    }





//    @Override
//    public boolean onDown(MotionEvent motionEvent) {
//        Log.d("Gesture.onDown", "X Y: " + motionEvent.getX() + " " + motionEvent.getY());
//
//        myView.createLine(
//                myView.createPoint(
//                        motionEvent.getX(), motionEvent.getY()));
//
//        myView.reDraw();
//        return true;
//    }


    public boolean onTouch(MotionEvent event) {

        Log.d("Gesture", "Gesture.onTouch event.getAction: " + event.getAction());
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                myView.createLine(
                        myView.createPoint(
                                event.getX(), event.getY()));
                myView.reDraw();
                Log.d("Gesture", "Gesture.getAction x: " + event.getX() +
                        "    y: " + event.getY());

                return true;
            default:
                return false;
        }
    }

//    @Override
//    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float velocityX, float velocityY) {
//
//        Log.d("Gesture.onFling", "Lets have a Fling ;)");
//
//        myView.createLine(
//                myView.createPoint(
//                        motionEvent1.getX(), motionEvent1.getY()));
//
//        myView.createLine(
//                myView.createPoint(
//                        motionEvent2.getX(), motionEvent2.getY()));
//
//        myView.reDraw();
//        return false;
//    }

//    @Override
//    public boolean onDown(MotionEvent motionEvent) {
//
//        myView.createLine(
//                myView.createPoint(
//                        motionEvent.getX(), motionEvent.getY()));
//
//        Log.d("Gesture", "On down, set hut hut hut");
//        return true;
//    }

//    @Override
//    public boolean onMABoolean(MotionEvent motionEvent) {
//
//        myView.createLine(
//                myView.createPoint(
//                        motionEvent.getX(), motionEvent.getY()));
//
//        Log.d("Gesture", "On down, set hut hut hut");
//        return true;
//    }
}

