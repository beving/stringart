package com.marksoft.stringart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;
import java.util.Arrays;
import java.util.List;

/**
 * Allows selection of a number via a dialog.
 * Created by e62032 on 4/20/2016.
   see http://stackoverflow.com/questions/15762905/how-can-i-display-a-list-view-in-an-android-alert-dialog
*/
class NumberChooserDialog {

    private NumberChooserDialog(){}

    public static void lineSize(final DrawingView drawingView, final boolean applyToAllLines) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(drawingView.getContext());

        alertDialog.setNegativeButton(drawingView.getContext().getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        List<String> aList = Arrays.asList(drawingView.getContext().getResources().getStringArray(R.array.line_sizes));
        int currentSelection =  aList.indexOf(SharedPreferencesUtility.getStrokeWidth(drawingView.getContext()) + "");

        alertDialog.setTitle(drawingView.getContext().getResources().getString(R.string.line_size));
        alertDialog.setSingleChoiceItems(R.array.line_sizes, currentSelection,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedInteger) {

                        try {
                            String choices[] = drawingView.getContext().getResources().getStringArray(R.array.line_sizes);
                            int lineThickness = Integer.parseInt(choices[selectedInteger]);

                            SharedPreferencesUtility.setStrokeWidth(drawingView.getContext(), lineThickness);

                            if (applyToAllLines) {
                                for (Line line : drawingView.getLines()) {
                                    line.setThickness(lineThickness);
                                }
                            }
                            Toast.makeText(drawingView.getContext().getApplicationContext(),
                                    "You Choose : " + choices[selectedInteger],
                                    Toast.LENGTH_LONG).show();

                            drawingView.reDraw();
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        alertDialog.show();
    }

    public static void gridSize(final DrawingView drawingView) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(drawingView.getContext());

        alertDialog.setNegativeButton(drawingView.getContext().getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        List<String> aList = Arrays.asList(drawingView.getContext().getResources().getStringArray(R.array.grid_sizes));
        int currentSelection =  aList.indexOf(SharedPreferencesUtility.getGridSpacing(drawingView.getContext()) + "");

        alertDialog.setTitle(drawingView.getContext().getResources().getString(R.string.grid_spacing));
        alertDialog.setSingleChoiceItems(R.array.grid_sizes,
                currentSelection,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int selectedInteger) {

                        try {
                            String choices[] = drawingView.getContext().getResources().getStringArray(R.array.grid_sizes);

                            SharedPreferencesUtility.setGridSpacing(drawingView.getContext(), Integer.parseInt(choices[selectedInteger]));
                            drawingView.reDraw();

                            Toast.makeText(drawingView.getContext().getApplicationContext(),
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

}
