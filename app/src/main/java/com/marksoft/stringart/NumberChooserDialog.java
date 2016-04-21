package com.marksoft.stringart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * Allows selection of a number via a dialog.
 * Created by e62032 on 4/20/2016.
   see http://stackoverflow.com/questions/15762905/how-can-i-display-a-list-view-in-an-android-alert-dialog
*/
class NumberChooserDialog {

    public void open(final Context context, final DrawingView drawingView, boolean gridSpacing) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        final ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_selectable_list_item);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {              //TODO don't hard code text.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if (gridSpacing) {

            for (int i = 25; i < 150 ;i+=25) {
                arrayAdapter.add(i);
            }

            alertDialog.setTitle("Grid Spacing");
            alertDialog.setAdapter(arrayAdapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Integer selectedInteger = arrayAdapter.getItem(which);
                            Log.d("Grid Spacing: ", selectedInteger + "");


                            drawingView.setRoundToTheNearest(selectedInteger);
                            drawingView.drawLines();
                            Toast.makeText(context, "Grid Spacing set to: " + selectedInteger,  //TODO don't hard code text.
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            alertDialog.setTitle("Line Size");
            for (int i = 1; i < 10 +1 ;i+=2) {
                arrayAdapter.add(i);
            }
            alertDialog.setAdapter(arrayAdapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Integer selectedInteger = arrayAdapter.getItem(which);
                            Log.d("Line Size: ", selectedInteger + "");


                            drawingView.setStrokeWidth(selectedInteger);
                            drawingView.drawLines();
                            Toast.makeText(context, "Line Size to: " + selectedInteger,  //TODO don't hard code text.
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
        alertDialog.show();
    }
}
