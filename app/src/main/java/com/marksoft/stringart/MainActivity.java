package com.marksoft.stringart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

        Log.d("MainActivity", "Item ID: " + item.getItemId());
        switch (item.getItemId()) {

            /*case (R.id.action_settings): {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(intent);

                return true;
            }*/
            case (R.id.action_undo): {
                Log.d("MainActivity", "action_undo");
                getDrawingView().undoAdditionOfLastPoint();
                break;
            }
            case (R.id.action_clear): {
                Log.d("MainActivity", "action_clear");

                new AlertDialog.Builder(this)
                        .setTitle("Clear")
                        .setMessage("Do you really want to clear all the lines and points?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                getDrawingView().clear();
                                Toast.makeText(MainActivity.this, R.string.cleared, Toast.LENGTH_SHORT).show();
                            }})
                         .setNegativeButton(android.R.string.no, null).show();
                break;
            }
            case (R.id.action_connect): {
                Log.d("MainActivity", "action_connect");

                if (!getDrawingView().drawLines()) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.pointless),
                            Toast.LENGTH_LONG).show();
                }
                break;
            }
            case (R.id.action_change_color): {
                Log.d("MainActivity", "action_change_color");
                new ColorDialog().colorDialog(getBaseContext(), getDrawingView(),
                        getSupportFragmentManager());
                break;
            }
            case (R.id.action_line_thickness): {
                Log.d("MainActivity", "action_line_thickness");
                new NumberChooserDialog().open(MainActivity.this, getDrawingView(), item.getItemId());
                getDrawingView().drawLines();
                break;
            }
            case (R.id.action_toggle_grid): {
                Log.d("MainActivity", "action_toggle_grid");

                //Set to the opposite of what it is currently
                getDataFragment().setDrawDottedLines(!getDataFragment().isDrawDottedLines());
                break;
            }
            case (R.id.action_grid_size): {
                Log.d("MainActivity", "action_grid_size");

                new NumberChooserDialog().open(MainActivity.this, getDrawingView(), item.getItemId());
                break;
            }
            default: {
                return false;
            }
        }
        getDrawingView().reDraw();

        return super.onOptionsItemSelected(item);
    }

    private DrawingView getDrawingView() {
        return (DrawingView) findViewById(R.id.drawingView);
    }

    private RetainedFragment getDataFragment() { return getDrawingView().getDataHandler().getDataFragment(); }

}
