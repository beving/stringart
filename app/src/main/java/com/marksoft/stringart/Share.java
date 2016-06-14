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
public class Share extends AppCompatActivity {

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
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Log.d("Share", "File-?????--> " +context.getFileStreamPath(fileName));

        return context.getFileStreamPath(fileName);
    }

    private void scanForNewFiles(File file, Context context) {

        if (file.exists()) {  //TODO rm, this is for testing purposes only.
            Toast.makeText(context, "File created: " + file.getName(), Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(context, "scanForNewFiles file was NOT found" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();

        MediaScannerConnection.scanFile(context,
                new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d("DrawingView", "MediaScannerConnection onScanCompleted.. path:" + path + "URI: " + uri);
                    }
                });
    }


    private void shareIntent(File file, Context context) {
        //Bitmap icon = mBitmap;
        try {

            String debugPath = Uri.fromFile(file).getPath();
            File debugFile = new File(debugPath);

            if (!debugFile.exists()) {
                Toast.makeText(context, "NOT exists! debugPath: " + debugPath, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "URI exists: " + debugPath, Toast.LENGTH_LONG).show();

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/png");

                //icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                Log.d("DrawingView", "URI: " + Uri.fromFile(file));

                Toast.makeText(context, "startActivity1", Toast.LENGTH_LONG).show();

                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                Toast.makeText(context, "startActivity2", Toast.LENGTH_LONG).show();

                context.startActivity(Intent.createChooser(share, "Share Image"));

                Toast.makeText(context, "startActivity finished", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private Intent shareIntentOld(File file, Context context){

        Log.i("Share","getDefaultShareIntent starting");
        Uri screenshotUri = Uri.fromFile(file);

        //Create an intent to send any type of image
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/png");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);

        Log.i("Share", "getDefaultShareIntent starting");

        if (sharingIntent == null) {
            Log.d("Share", "sharing intent is null");
        }

        context.startActivity(Intent.createChooser(sharingIntent, "Share image using"));
        Log.i("Share", "getDefaultShareIntent startActivity worked!");
        return sharingIntent;
    }
}