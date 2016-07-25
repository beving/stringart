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

    public static final String TAG = "NumberChooserDialog";

    public void open(final Context context, final DrawingView drawingView, int gridSpacing) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {              //TODO don't hard code text.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if (gridSpacing == R.id.action_grid_size) {
            final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(context,
                    R.array.grid_sizes, android.R.layout.simple_selectable_list_item);

            alertDialog.setTitle("Grid Spacing");
            alertDialog.setAdapter(arrayAdapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Integer selectedInteger = Integer.parseInt(
                                    arrayAdapter.getItem(which).toString());
                            Log.d("Grid Spacing: ", selectedInteger + "");
                            Log.d(TAG, R.string.grid_spacing_set + ": " + selectedInteger);

                            drawingView.getDataHandler().getDataFragment().setRoundToTheNearest(selectedInteger);
                            drawingView.reDraw();
                            Toast.makeText(context, R.string.grid_spacing_set
                                    + selectedInteger,  //TODO don't hard code text.
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        } else if (gridSpacing == R.id.action_line_thickness) {

            final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(context,
                    R.array.line_sizes, android.R.layout.simple_selectable_list_item);

            alertDialog.setTitle("Line Size");
            alertDialog.setAdapter(arrayAdapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Integer selectedInteger = Integer.parseInt(
                                    arrayAdapter.getItem(which).toString());
                            Log.d(TAG, R.string.line_size_set + ": " + selectedInteger);

                            drawingView.getDataHandler().getDataFragment().setStrokeWidth(selectedInteger);
                            drawingView.reDraw();
                            Toast.makeText(context, R.string.line_size_set +
                                            selectedInteger,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
        alertDialog.show();
    }
}
