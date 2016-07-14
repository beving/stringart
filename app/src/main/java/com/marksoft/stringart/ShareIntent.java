package com.marksoft.stringart;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.List;


public class ShareIntent {

    private static final String TAG = "ShareIntent";
    public static final int PERMISSION_TO_SHARE = 8;


    public static Point calculateCanvasSize(List<Point> points) {
        Point maxPoint = new Point(0, 0);

        for (Point point : points) {
            if (point.x > maxPoint.x) {
                maxPoint.x = point.x;
            }
            if (point.y > maxPoint.y) {
                maxPoint.y = point.y;
            }
        }

        //Add a little bit extra for a border
        maxPoint.x +=20;
        maxPoint.y +=20;

        return maxPoint;
    }

    public static void share(Activity activity, final DrawingView drawingView) {

        Point maxPoint = calculateCanvasSize(drawingView.getDataHandler().getDataFragment().getPoints());

        //Create a canvas instance using this bitmap using Canvas(Bitmap) constructor
        Bitmap bitmap = Bitmap.createBitmap(maxPoint.x, maxPoint.y, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);

        DrawingView drawingView1 = new DrawingView(drawingView.getContext());
        drawingView1.setDataHandler(drawingView.getDataHandler());
        drawingView1.getDataHandler().getDataFragment().setDrawDottedLines(false);

        drawingView1.drawBackGround(canvas);
        drawingView1.drawPoints(canvas);
        drawingView1.drawLines(canvas);

        String url = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap,
                "StringArt_Image", "The image captured by MediaStore from the canvas of StringArt.");

        Log.d("ShareIntent", "share url: " + url);

        Intent intent = ShareIntent.getImageIntent(Uri.parse(url));
        activity.startActivity(intent);
    }

    public static Intent getImageIntent(Uri imageUri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");

        if (imageUri == null) {
            Log.wtf(TAG, "Trying to share a null image");
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        }
        return Intent.createChooser(intent, null);
    }

    /**
     * Request Permissions from user.
     * See: https://developer.android.com/training/permissions/requesting.html
     *
     * @param activity
     */
    public static void requestPermissions(Activity activity) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_TO_SHARE);
            }
        }
    }
}