package com.marksoft.stringart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private long lastBackPressTime = 0;
    private DataHandler dataHandler = new DataHandler();
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        sharedPreferences = SharedPreferencesUtility.init(MainActivity.this);
        getDrawingView().setDataHandler(dataHandler);
        getDrawingView().getDataHandler().initDataFragment(getBaseContext(), getFragmentManager(), savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataHandler.persistData(getBaseContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml..
        try {
            Log.d(TAG, "Item ID: " + item.getItemId());
            switch (item.getItemId()) {
                case (R.id.action_undo): {
                    if (!getDrawingView().undoAdditionOfLastPoint()) {
                        Toast.makeText(MainActivity.this, R.string.no_points_left_to_undo, Toast.LENGTH_LONG).show();
                    }
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
                    getDrawingView().cutPoint();
                    break;
                }
                case (R.id.action_change_color): {
                    ColorDialog.selectLineColor(getBaseContext(), getDrawingView(),
                            getSupportFragmentManager());
                    break;
                }
                case (R.id.action_background_color): {
                    new ColorDialog().selectBackgroundColor(getBaseContext(), getDrawingView(),
                            getSupportFragmentManager());
                    break;
                }
                case (R.id.action_line_thickness): {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.line_size)
                            .setMessage(R.string.clear_apply_to_all_lines)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(R.string.all, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    NumberChooserDialog.lineSize(getDrawingView(), true);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton(R.string.new_new, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    NumberChooserDialog.lineSize(getDrawingView(), false);
                                    dialog.dismiss();
                                }
                            }).show();
                    break;
                }
                case (R.id.action_toggle_grid): {
                    //Set to the opposite of what it is currently

                    boolean currentState = SharedPreferencesUtility.isGridLinesOn(sharedPreferences);
                    SharedPreferencesUtility.setGridLines(MainActivity.this, !currentState);
                    item.setChecked(currentState);
                    break;
                }
                case (R.id.action_grid_size): {
                    NumberChooserDialog.gridSize(getDrawingView(), item.getItemId());
                    break;
                }
                case (R.id.action_share): {
                    if (getDataFragment().isPermissibleToShare()) {
                        ShareIntent.share(this, getDrawingView());
                    } else {
                        ShareIntent.requestPermissions(this);
                    }
                    break;
                }
                default: {
                    return false;
                }
            }
            getDrawingView().undoPreviousAction(item.getItemId());
            getDrawingView().reDraw();

        } catch (Exception e) {
            Log.e(TAG, e.getStackTrace().toString());
            if (BuildConfig.DEBUG) {
                Toast.makeText(MainActivity.this, "Caught Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            e.printStackTrace();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == ShareIntent.PERMISSION_TO_SHARE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ShareIntent.share(this, getDrawingView());
                getDataFragment().setPermissableToShare(true);
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.action_toggle_grid);
        checkable.setChecked(SharedPreferencesUtility.isGridLinesOn(sharedPreferences));
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        getDataFragment().onRestoreInstanceState(bundle);
    }

    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - R.integer.time_out_exit_application) {
            Toast.makeText(MainActivity.this, R.string.close, Toast.LENGTH_LONG).show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    protected DrawingView getDrawingView() {
        return (DrawingView) findViewById(R.id.drawingView);
    }

    private RetainedFragment getDataFragment() {
        return getDrawingView().getDataHandler().getDataFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getDataFragment().onSaveInstanceState(outState);
    }

}
