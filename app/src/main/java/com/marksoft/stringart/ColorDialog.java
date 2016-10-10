package com.marksoft.stringart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

/**
 * Dialog to choose a Color.
 * Using QuadFlask Color picker
 * See: https://github.com/QuadFlask/colorpicker
 *
 * @see ColorPickerDialogBuilder
 * Created by e62032 on 10/6/2016.
 */
class ColorDialog {

    public static void selectBackgroundColor(final DrawingView drawingView) {
        final Resources r = drawingView.getContext().getResources();
        final Context context = drawingView.getContext();

        ColorPickerDialogBuilder
                .with(context)
                .setTitle(r.getString(R.string.color_dialog_title))
                .initialColor(SharedPreferencesUtility.getBackgroundColor(context))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        Toast.makeText(context,
                                r.getString(R.string.color_set) + " " +
                                        Integer.toHexString(selectedColor),
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton(r.getString(R.string.ok),
                        new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                SharedPreferencesUtility.setBackgroundColor(context, selectedColor);
                                drawingView.reDraw();
                            }
                        })
                .setNegativeButton(r.getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .build()
                .show();
    }

    public static void selectLineColor(final DrawingView drawingView) {
        final Resources r = drawingView.getContext().getResources();
        final Context context = drawingView.getContext();

        ColorPickerDialogBuilder
                .with(context)
                .setTitle(r.getString(R.string.color_dialog_title))
                .initialColor(SharedPreferencesUtility.getLineColor(drawingView.getContext()))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        Toast.makeText(context,
                                r.getString(R.string.color_set) + " " +
                                        Integer.toHexString(selectedColor),
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton(r.getString(R.string.ok),
                        new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                SharedPreferencesUtility.setLineColor(context, selectedColor);
                                drawingView.reDraw();
                            }
                        })
                .setNegativeButton(r.getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .build()
                .show();
    }
}
