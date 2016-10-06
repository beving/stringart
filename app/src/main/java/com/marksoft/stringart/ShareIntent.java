package com.marksoft.stringart;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ShareIntent {

    private static final String TAG = "ShareIntent";
    public static final int PERMISSION_TO_SHARE = 8;

    public static void share(Activity activity, final DrawingView drawingView) {

        //Create a canvas instance using this bitmap using Canvas(Bitmap) constructor
        Bitmap bitmap = Bitmap.createBitmap(drawingView.getWidth(), drawingView.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        DrawingView sharedDrawingView = new DrawingView(drawingView.getContext());
        sharedDrawingView.setDataHandler(drawingView.getDataHandler());

        sharedDrawingView.drawBackGround(canvas);
        sharedDrawingView.drawPoints(canvas);
        sharedDrawingView.drawLines(canvas);

        canvas.scale(2000,2000);

        Uri fileLocation = saveBitmap(bitmap, activity);

        Log.d(TAG, "Shared fileLocation uri: " + fileLocation);
        Intent intent = ShareIntent.getImageIntent(fileLocation);
        activity.startActivity(intent);
    }

    public static Uri saveBitmap(Bitmap bitmap, Activity activity) {
        File imagePath = null;
        try {
            String fileNamePrefix = activity.getResources().getString(R.string.share_name_prefix);
            removeOldFiles(fileNamePrefix);

            imagePath = File.createTempFile(fileNamePrefix,
                    ".png",
                    Environment.getExternalStorageDirectory());

            FileOutputStream fos;
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return Uri.fromFile(imagePath);
    }

    //Clean up old files
    private static void removeOldFiles(String filesToCleanUp) {
        int numberOfFilesDeleted = 0;
        File dir = new File(Environment.getExternalStorageDirectory().getPath());
        for (File file : dir.listFiles()) {
            if (file.getName().contains(filesToCleanUp)) {
                Log.d(TAG, "Removing Old File: " + file.getName());
                if (file.delete()) {
                    numberOfFilesDeleted++;
                }
            }
        }
        Log.d(TAG, "Number of files deleted was: " + numberOfFilesDeleted);
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
    */
    public static void requestPermissions(Activity activity) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we selectLineColor an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_CONTACTS)) {

                Log.d(TAG, "ActivityCompat.shouldShowRequestPermissionRationale");
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