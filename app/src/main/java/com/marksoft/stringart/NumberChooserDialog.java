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
public class NumberChooserDialog {

    public static final String TAG = "NumberChooserDialog";

    private NumberChooserDialog(){}

    public static void lineSize(final Context context, final DrawingView drawingView, int gridSpacing) {
        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(context,
                R.array.line_sizes, android.R.layout.simple_selectable_list_item);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(context.getResources().getString(R.string.line_size));
        alertDialog.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer selectedInteger = Integer.parseInt(
                                arrayAdapter.getItem(which).toString());
                        Log.d(TAG, R.string.line_size_set + ": " + selectedInteger);

                        drawingView.getDataHandler().getDataFragment().setStrokeWidth(selectedInteger);
                        drawingView.reDraw();
                        Toast.makeText(context,
                                context.getResources().getString(R.string.line_size_set) + " "
                                        + selectedInteger,
                                Toast.LENGTH_LONG).show();
                    }
                });
        alertDialog.show();
    }

    public static void gridSize(final Context context, final DrawingView drawingView, int gridSpacing) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setNegativeButton(context.getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

            final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(context,
                    R.array.grid_sizes, android.R.layout.simple_selectable_list_item);

            alertDialog.setTitle(context.getResources().getString(R.string.grid_spacing));
            alertDialog.setAdapter(arrayAdapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Integer selectedInteger = Integer.parseInt(
                                    arrayAdapter.getItem(which).toString());

                            drawingView.getDataHandler().getDataFragment().setRoundToTheNearest(selectedInteger);
                            drawingView.reDraw();
                            Toast.makeText(context,
                                    context.getResources().getString(R.string.grid_spacing_set) + " "
                                            + selectedInteger,
                                    Toast.LENGTH_LONG).show();
                        }
                    });


            alertDialog.show();
    }
}
