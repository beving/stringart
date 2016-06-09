package com.marksoft.stringart;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
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

//        for (String fileFound: context.fileList()) {
//            Log.d("SaveActivity", "File---> " +fileFound);
//            Toast.makeText(context, "Saved as: " + fileFound, Toast.LENGTH_LONG).show();
//        }

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

//    private File saveToExternalStorage(Bitmap b, Context context){
//        // Get path to External Storage
//        //String root = Environment.getExternalStorageDirectory().toString();
//
//        File rootFile = context.getFilesDir();
//
//        Log.d("SaveActivity","Root Path name: " + rootFile.getPath());
//        File myDir = new File(rootFile.getPath() + File.separator + "MarkWasHere" + File.separator);
//        Log.d("SaveActivity","myDir: " + myDir.getAbsolutePath());
//        myDir.mkdirs();
//        Log.d("SaveActivity", "mkdirs ");
//
//        // Generating a random number to save as image name
//        Random generator = new Random();
//        int n = 10000;
//        n = generator.nextInt(n);
//        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
//        String fname = "Image_"+timeStamp+"_"+ n + ".jpg";
//
//        Log.d("SaveActivity", "fname: " + fname);
//        File file = new File(myDir, fname);
//        try {
//            if (file.exists()) {
//                file.delete();
//            }
//
//            FileOutputStream out = new FileOutputStream(file);
//            b.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            Log.d("DrawingView","Exception --> File Name: " +file.getAbsolutePath());
//            throw new RuntimeException(e);
//        }
//
//        if (file.exists()) {
//            Log.d("DrawingView","2File does exist --> File Name: " +file.getAbsolutePath());
//            Toast.makeText(context, "File does exist --> File Name: " +file.getAbsolutePath(),  Toast.LENGTH_LONG).show();
//        } else Log.d("SaveActivity", "2File does not exist: " + file.getAbsolutePath());
//
//        // Tell your media scanner to refresh/scan for new images
//
//        MediaScannerConnection.scanFile(context,
//                new String[]{file.toString()}, null,
//                new MediaScannerConnection.OnScanCompletedListener() {
//                    public void onScanCompleted(String path, Uri uri) {
//                        System.out.println("MediaScannerConnection OnScapath");
//                    }
//                });
//        return file;
//
//    }

}
