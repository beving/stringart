package com.marksoft.stringart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by e62032 on 6/8/2016.
 */
public class SaveActivity extends AppCompatActivity {

    public void share(final Context context, final DrawingView drawingView) {
        File savedFile = save(context, drawingView);
        scanForNewFiles(savedFile, context);
        shareIntent(savedFile, context);
    }

    private File save(final Context context, final DrawingView drawingView) {

        // Generating a random number to save as image name
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String fileName = "Image_" + timeStamp + "_" + n + ".png";

        Log.d("DrawingView", "context.getFilesDir: " + context.getFilesDir());

        View content = drawingView;
        content.setDrawingCacheEnabled(true);
        content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        Bitmap bitmap = content.getDrawingCache();
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);  //TODO make this use a temp file so it gets thrown away see: https://developer.android.com/training/basics/data-storage/files.html
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Log.d("SaveActivity", "File-?????--> " +context.getFileStreamPath(fileName));

        return context.getFileStreamPath(fileName);
    }

    private void scanForNewFiles(File file, Context context) {

        if (file.exists()) {  //TODO rm, this is for testing purposes only.
            Toast.makeText(context, "scanForNewFiles file found: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(context, "scanForNewFiles file was NOT found" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();

        MediaScannerConnection.scanFile(context,
                new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        System.out.println("MediaScannerConnection onScanCompleted");
                    }
                });
    }

    private Intent shareIntent(File file, Context context){

        Log.d("SaveActivity","getDefaultShareIntent starting");
        Uri screenshotUri = Uri.fromFile(file);

        //Create an intent to send any type of image
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);

        Log.d("SaveActivity", "getDefaultShareIntent starting");

        if (sharingIntent == null) {
            Log.d("SaveActivity", "sharing intent is null");
        }

        context.startActivity(Intent.createChooser(sharingIntent, "Share image using"));
        Log.d("SaveActivity", "getDefaultShareIntent startActivity worked!");
        return sharingIntent;
    }
}