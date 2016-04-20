package com.marksoft.stringart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DataHandler dataHandler = new DataHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataHandler.handlePoints(getDrawingView(), getFragmentManager());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // store the data in the fragment
        Log.d("MainActivity.onDestroy", "Size: " + getDrawingView().getPoints().size());

        dataHandler.getDataFragment().setPoints(getDrawingView().getPoints());
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

            case (R.id.action_settings):
                return true;
            case (R.id.action_undo): {
                Log.d("MainActivity", "action_undo");
                getDrawingView().undoAdditionOfLastPoint();
                getDrawingView().drawLines();
                return true;
            }
            case (R.id.action_clear): {
                Log.d("MainActivity", "action_clear");
                getDrawingView().clear();
                return true;
            }
            case (R.id.action_connect): {
                Log.d("MainActivity", "action_connect");

                if (!getDrawingView().drawLines()) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.pointless),
                            Toast.LENGTH_LONG).show();
                }
                return true;
            }
            case (R.id.action_change_color): {
                Log.d("MainActivity", "action_change_color");
                new ColorDialog().colorDialog(getBaseContext(), getDrawingView(),
                        getSupportFragmentManager());
                return true;
            }
            case (R.id.action_line_thickness): {
                Log.d("MainActivity", "action_line_thickness");

                myDialog();

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private DrawingView getDrawingView() {
        return (DrawingView) findViewById(R.id.drawingView);
    }

    //see http://stackoverflow.com/questions/15762905/how-can-i-display-a-list-view-in-an-android-alert-dialog
    public void myDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.setTitle("Line Thickness");

        final ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add(1);
        arrayAdapter.add(2);
        arrayAdapter.add(3);
        arrayAdapter.add(4);
        arrayAdapter.add(5);

        alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {              //TODO don't hard code text.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer selectedInteger = arrayAdapter.getItem(which);
                        Log.d("Line thickness: ", selectedInteger + "");
                        getDrawingView().setStrokeWidth(selectedInteger);
                        Toast.makeText(getBaseContext(), "Line thickness set to: " + selectedInteger,  //TODO don't hard code text.
                                Toast.LENGTH_LONG).show();
                    }
                });
        alertDialog.show();
    }
}
