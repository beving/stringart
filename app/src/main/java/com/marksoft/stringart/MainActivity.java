package com.marksoft.stringart;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.stephentuso.welcome.WelcomeScreenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private long lastBackPressTime = 0;
    private DataHandler dataHandler = new DataHandler();
    private WelcomeScreenHelper welcomeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawing);

        getDrawingView().setDataHandler(dataHandler);
        getDrawingView().getDataHandler().initDataFragment(getFragmentManager(), savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onRestoreInstanceState(savedInstanceState);

        if (welcomeScreen == null) {
            welcomeScreen = new WelcomeScreenHelper(this, MyWelcomeActivity.class);
            welcomeScreen.show(savedInstanceState);
            welcomeScreen.forceShow();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml..
        try {
            Log.d("MainActivity", "Item ID: " + item.getItemId());
            switch (item.getItemId()) {

            /*case (R.id.action_settings): {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(intent);
            }*/
                case (R.id.action_undo): {
                    getDrawingView().undoAdditionOfLastPoint();
                    break;
                }
                case (R.id.action_clear): {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.clear)
                            .setMessage(R.string.clear_question)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    getDrawingView().clear();
                                    getDrawingView().reDraw();
                                    Toast.makeText(MainActivity.this, R.string.cleared, Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                    break;
                }
                case (R.id.action_cut): {
                    Log.d("MainActivity", "action_cut: " + item.getItemId());
                   getDrawingView().cutPoint();
                    break;
                }
                case (R.id.action_change_color): {
                    new ColorDialog().colorDialog(getBaseContext(), getDrawingView(),
                            getSupportFragmentManager());
                    break;
                }
                case (R.id.action_line_thickness): {
                    new NumberChooserDialog().open(MainActivity.this, getDrawingView(), item.getItemId());
                    break;
                }
                case (R.id.action_toggle_grid): {
                    //Set to the opposite of what it is currently
                    getDataFragment().setDrawDottedLines(!getDataFragment().isDrawDottedLines());
                    item.setChecked(getDataFragment().isDrawDottedLines());
                    break;
                }
                case (R.id.action_grid_size): {
                    new NumberChooserDialog().open(MainActivity.this, getDrawingView(), item.getItemId());
                    break;
                }
                case (R.id.action_save): {
//                    ent intent = new Intent(this, GLES20Activity.class);
//                    startActivity(intent);
                    //new ShareActivity().share(MainActivity.this, getDrawingView());
//                    File savedFile = save( getDrawingView());
//                    shareIntent(savedFile);
//                    whatefver();

                    requestPermissions();

                    ShareActivity.hasPermission(this);

                    ShareActivity.share(this, getDrawingView());


                    break;
                }
                default: {
                    return false;
                }
            }
            getDrawingView().undoPreviousAction(item.getItemId());
            getDrawingView().reDraw();

        } catch (Exception e) {
            Log.e("", e.getStackTrace().toString());
            e.printStackTrace();

            Toast.makeText(MainActivity.this, "Caught Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.action_toggle_grid);
        checkable.setChecked(getDataFragment().isDrawDottedLines());
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        getDataFragment().onRestoreInstanceState(bundle);
    }

    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
            Toast.makeText(MainActivity.this, R.string.close, Toast.LENGTH_LONG).show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    private DrawingView getDrawingView() {
        return (DrawingView) findViewById(R.id.drawingView);
    }

    private RetainedFragment getDataFragment() {
        return getDrawingView().getDataHandler().getDataFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
        getDataFragment().onSaveInstanceState(outState);
    }

    private void whatefver() {

        File fileToShare = new File("/data/user/0/com.marksoft.stringart/files/Image_20160712_9593.png");

        if (fileToShare.exists()) {
            Toast.makeText(this, "Image_20160712_9593 exists", Toast.LENGTH_LONG).show();
        } else   Toast.makeText(this, "NOT Image_20160712_9593  ", Toast.LENGTH_LONG).show();

        Intent intent = ShareIntent.getImageIntent(Uri.fromFile(fileToShare));
        startActivity(intent);
    }


    private void whatefdddfdfdfdver() {

        File fileToShare = new File("/data/user/0/com.marksoft.stringart/files/Image_20160712_9593.png");

        if (fileToShare.exists()) {
            Toast.makeText(this, "Image_20160712_9593 exists", Toast.LENGTH_LONG).show();
        } else   Toast.makeText(this, "NOT Image_20160712_9593  ", Toast.LENGTH_LONG).show();

        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");
        //final File photoFile = new File(getFilesDir(), "foo.jpg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileToShare));
        startActivity(Intent.createChooser(shareIntent, "Share image using"));
    }


    private File save(final DrawingView drawingView) {

        // Generating a random number to save as image name
        String fileName = getRandomFileName();

        //Log.d("DrawingView", "context.getFilesDir: " + context.getFilesDir());

        View content = drawingView;
        content.setDrawingCacheEnabled(true);
        content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        Bitmap bitmap = content.getDrawingCache();
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);  //TODO make this use a temp file so it gets thrown away see: https://developer.android.com/training/basics/data-storage/files.html
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return getFileStreamPath(fileName);
    }




   private void shareIntent(File file) {
   //private void shareIntent(Uri uri, Context context) {
        //Bitmap icon = mBitmap;
        try {

            String debugPath = Uri.fromFile(file).getPath();
            File debugFile = new File(debugPath);

            if (!debugFile.exists()) {
                Toast.makeText(this, "NOT exists! debugPath: " + debugPath, Toast.LENGTH_LONG).show();
            } else {

                grantUriPermission("com.marksoft.stringart", Uri.fromFile(file), Intent.FLAG_GRANT_READ_URI_PERMISSION);

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/png");

                //icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                Log.d("DrawingView", "URI: " + Uri.fromFile(debugFile));

                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(debugFile));

                startActivity(Intent.createChooser(share, "ShareActivity Image"));

                //Toast.makeText(context, "startActivity finished", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String getRandomFileName() {
        // Generating a random number to save as image name
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return "Image_" + timeStamp + "_" + n + ".png";
    }

    private void requestPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
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
