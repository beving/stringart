package com.marksoft.stringart;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;


public class ShareIntent {

    private static final String TAG = "ShareIntent";
    public static final int PERMISSION_TO_SHARE = 8;

    public static void share(Activity context, final DrawingView drawingView) {

//        getDrawingViewInfo(context);

        drawingView.setDrawingCacheEnabled(true);
        drawingView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        drawingView.reDraw();

        Bitmap bitmap = drawingView.getDrawingCache();

        String url = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,
                "StringArt_Image", "The image captured by MediaStore from the canvas of StringArt.");  //TODO getRandomFileName may not be needed.

        Log.d("ShareIntent", "share url: " + url);

        Intent intent = ShareIntent.getImageIntent(Uri.parse(url));
        context.startActivity(intent);
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

//    private static DrawingView getDrawingViewInfo(Activity activity) {
//        DrawingView dv = (DrawingView) activity.findViewById(R.id.drawingView);
//
//        if (dv != null) {
//            Log.e("MainActivity", "DrawingView: " + dv.toString());  //TODO rm for testing only.
//
//            if (dv.getPoints() != null && !dv.getPoints().isEmpty())
//                Log.e("MainActivity", "getDrawingView-->Drawing view points: " + dv.getPoints().size());  //TODO rm for testing only.
//        }
//        return dv; //TODO rm
//    }

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

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        8);


//                        new String[]{Manifest.permission.READ_CONTACTS},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}