package com.marksoft.stringart;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ShareIntent {

    private static final String TAG = "ShareIntent";
    public static final int PERMISSION_TO_SHARE = 8;

    public static void share(Activity activity, final DrawingView drawingView) {

        //Point maxPoint = PointUtility.calculateMaxSize(drawingView.getDataHandler().getDataFragment().getPoints());

        //Create a canvas instance using this bitmap using Canvas(Bitmap) constructor
        Bitmap bitmap = Bitmap.createBitmap(drawingView.getWidth(), drawingView.getHeight(),
//                drawingView.getCanvas().getWidth(),
//                drawingView.getCanvas().getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        DrawingView sharedDrawingView = new DrawingView(drawingView.getContext());
        sharedDrawingView.setDataHandler(drawingView.getDataHandler());
        sharedDrawingView.getDataHandler().getDataFragment().setDrawGridLines(false);

        sharedDrawingView.drawBackGround(canvas);
        sharedDrawingView.drawPoints(canvas);
        sharedDrawingView.drawLines(canvas);

        bitmap = PointUtility.trimBitmap(bitmap, Color.WHITE);

        Uri fileLocation = saveBitmap(bitmap);

        Log.d(TAG, "Shared fileLocation uri: " + fileLocation);
        Intent intent = ShareIntent.getImageIntent(fileLocation);
        activity.startActivity(intent);
    }

    public static Uri saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/tempStringArtImage.png");
        FileOutputStream fos;

        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }

        return Uri.fromFile(imagePath);
    }

    public static Intent getImageIntent(Uri imageUri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");

        if (imageUri == null) {
            Log.d(TAG, "Trying to share a null image");
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

                // Show an explanation to the user *asynchronously* -- don't block
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