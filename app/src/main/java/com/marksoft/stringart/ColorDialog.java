package com.marksoft.stringart;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.thebluealliance.spectrum.SpectrumDialog;

/**
 * Dialog to choose a Color.
 * Use the Spectrum library.
 * See: https://github.com/the-blue-alliance/spectrum
 * @see SpectrumDialog
 * Created by e62032 on 4/19/2016.
 */
public class ColorDialog {

    public void colorDialog(final Context context,
                             final DrawingView drawingView,
                             FragmentManager fragmentManager) {

        int[] colors = context.getResources().getIntArray(R.array.available_colors);

        new SpectrumDialog.Builder(context)
                .setColors(colors)
                .setSelectedColorRes(R.color.white)
                .setDismissOnColorSelected(true)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                        if (positiveResult) {
                            Toast.makeText(context,
                                    context.getResources().getString(R.string.color_set) + " " +
                                    Integer.toHexString(color).toUpperCase(),
                                    Toast.LENGTH_LONG).show();
                            drawingView.getDataHandler().getDataFragment().setLastSelectedColor(color);
                        }
                    }
                }).build().show(fragmentManager, "");
    }
}
