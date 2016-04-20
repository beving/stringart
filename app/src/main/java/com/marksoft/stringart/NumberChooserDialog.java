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

    public void open(final Context context, final DrawingView drawingView,
        int starting, int ending, int iterateBy) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        //alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.setTitle("Line Thickness");

        final ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(context,
                android.R.layout.select_dialog_singlechoice);

        for (int i = starting; i < ending +1 ;i+=iterateBy) {
            arrayAdapter.add(i);
        }

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {              //TODO don't hard code text.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer selectedInteger = arrayAdapter.getItem(which);
                        Log.d("Line thickness: ", selectedInteger + "");

                        drawingView.setStrokeWidth(selectedInteger);
                        drawingView.drawLines();
                        Toast.makeText(context, "Line thickness set to: " + selectedInteger,  //TODO don't hard code text.
                                Toast.LENGTH_LONG).show();
                    }
                });
        alertDialog.show();
    }
}
