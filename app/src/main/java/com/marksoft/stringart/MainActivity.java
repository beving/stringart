package com.marksoft.stringart;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.marksoft.stringart.gles20.GLES20Activity;

public class MainActivity extends AppCompatActivity {

    private DataHandler dataHandler = new DataHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawingView drawingView = getDrawingView();
        drawingView.setDataHandler(dataHandler);

        dataHandler.handlePoints(drawingView, getFragmentManager());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
                                    Toast.makeText(MainActivity.this, R.string.cleared, Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                    break;
                }
                case (R.id.action_connect): {
                    if (!getDrawingView().drawLines()) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.pointless),
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case (R.id.action_change_color): {
                    new ColorDialog().colorDialog(getBaseContext(), getDrawingView(),
                            getSupportFragmentManager());
                    break;
                }
                case (R.id.action_line_thickness): {
                    new NumberChooserDialog().open(MainActivity.this, getDrawingView(), item.getItemId());
                    getDrawingView().drawLines();
                    break;
                }
                case (R.id.action_toggle_grid): {
                    //Set to the opposite of what it is currently
                    getDataFragment().setDrawDottedLines(!getDataFragment().isDrawDottedLines());
                    break;
                }
                case (R.id.action_grid_size): {
                    new NumberChooserDialog().open(MainActivity.this, getDrawingView(), item.getItemId());
                    break;
                }
                case (R.id.action_save): {
                    if (hasGLES20()) {
                        Intent intent = new Intent(this, GLES20Activity.class);
                        startActivity(intent);
                        //new Share().share(MainActivity.this, getDrawingView());
                    }
                    break;
                }
                default: {
                    return false;
                }
            }
            getDrawingView().reDraw();

        } catch (Exception e) {
            Log.e("MainActivity", "Caught Exception " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Caught Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }



    private boolean hasGLES20() {
        ActivityManager am = (ActivityManager)
                getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return info.reqGlEsVersion >= 0x20000;
    }

    private DrawingView getDrawingView() {
        return (DrawingView) findViewById(R.id.drawingView);
    }

    private RetainedFragment getDataFragment() { return getDrawingView().getDataHandler().getDataFragment(); }

}
