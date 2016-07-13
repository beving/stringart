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
        import android.widget.Toast;

public class ShareIntent {
    private static final String TAG = "ShareIntent";


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


    public static void share(final Activity context, final DrawingView drawingView) {

        drawingView.setDrawingCacheEnabled(true);
        drawingView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = drawingView.getDrawingCache();

//        getPermission(context);
//        hasPermission(context);

        String url = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,
                "StringArtImage",
                "The image captured by MediaStore from the canvas of StringArt.");

        context.grantUriPermission("com.marksoft.stringart", Uri.parse(url), Intent.FLAG_GRANT_READ_URI_PERMISSION); //TODO don't need these
        context.grantUriPermission("com.marksoft.stringart", Uri.parse(url), Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        context.grantUriPermission("com.marksoft.stringart", Uri.parse(url), Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        context.grantUriPermission("com.marksoft.stringart", Uri.parse(url), Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);

        Intent intent = ShareIntent.getImageIntent(Uri.parse(url));
        context.startActivity(intent);
    }

    public static void getPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
    }

    public static boolean hasPermission(Activity activity) {
        // Assume thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == 8) {
            Toast.makeText(activity, "PERMISSION GRANTED", Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(activity, "PERMISSION NOT GRANTED was: " + ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE), Toast.LENGTH_LONG).show();
        return false;
    }

//    public static void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case 8: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }

    /**
     * Request Permissions from user.
     * See: https://developer.android.com/training/permissions/requesting.html
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