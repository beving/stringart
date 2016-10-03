package com.marksoft.stringart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

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

                        SharedPreferencesUtility.setStrokeWidth(context, selectedInteger);
                        drawingView.reDraw();
                        Toast.makeText(context,
                                context.getResources().getString(R.string.line_size_set) + " "
                                        + selectedInteger,
                                Toast.LENGTH_LONG).show();
                    }
                });
        alertDialog.show();
    }  //TODO do we really need context.  Could I not just just drawingView.getContext ?

    public static void gridSize(final Context context, final DrawingView drawingView, int gridSpacing) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setNegativeButton(context.getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.setTitle(context.getResources().getString(R.string.grid_spacing));
        alertDialog.setSingleChoiceItems(R.array.grid_sizes,
                getIndexOfCurrentChoice(context),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int selectedInteger) {

                        try {
                            String choices[] = context.getResources().getStringArray(R.array.grid_sizes);

                            SharedPreferencesUtility.setGridSpacing(context, Integer.parseInt(choices[selectedInteger]));
                            drawingView.reDraw();

                            Toast.makeText(context.getApplicationContext(),
                                    "You Choose : " + choices[selectedInteger],
                                    Toast.LENGTH_LONG).show();

                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        alertDialog.show();
    }

    private static int getIndexOfCurrentChoice(Context context) {
        List<String> aList = Arrays.asList(context.getResources().getStringArray(R.array.grid_sizes));
        return aList.indexOf(SharedPreferencesUtility.getGridSpacing(context)+"");
    }
}
