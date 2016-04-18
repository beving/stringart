package com.marksoft.stringart;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RetainedFragment dataFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        // find the retained fragment on activity restarts
        FragmentManager fm = getFragmentManager();
        dataFragment = (RetainedFragment) fm.findFragmentByTag("pointData");

        // create the fragment and data the first time
        if (dataFragment == null) {
            // add the fragment
            dataFragment = new RetainedFragment();
            fm.beginTransaction().add(dataFragment, "pointData").commit();

            dataFragment.setPoints(getDrawingView().getPoints());
            // load the data from the web
        } else { //In this case we are recreating.  Possibly due to change from landscape/portrait, etc
            if (!dataFragment.getPoints().isEmpty()) {
                getDrawingView().setPoints(dataFragment.getPoints());
                getDrawingView().drawLines();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // store the data in the fragment
        Log.d("MainActivity.onDestroy", "Size: " + getDrawingView().getPoints().size());

        dataFragment.setPoints(getDrawingView().getPoints());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d("MainActivity", "... .. .");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml..
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        DrawingView myView = getDrawingView();
        if (id == R.id.action_clear) {
            Log.d("MainActivity", "action_clear");
            myView.clear();
            return true;
        }
        if (id == R.id.action_connect) {
            Log.d("MainActivity", "action_connect");

            if (!myView.getPoints().isEmpty()) {
                myView.drawLines();
            }
            else {
                Toast.makeText(getBaseContext(), getResources().getString(R.string.pointless),
                        Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private DrawingView getDrawingView() {
        return (DrawingView)findViewById(R.id.drawingView);
    }

}
