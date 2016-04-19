package com.marksoft.stringart;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.app.FragmentManager;
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

        int[] colors = {
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                Color.CYAN, Color.MAGENTA,Color.BLACK, Color.GRAY,
                0xFFFF00FF,0xFF000080,0xFF808000, 0xFF800080, 0xFF008080 };

        new SpectrumDialog.Builder(context)
                .setColors(colors)
                .setSelectedColorRes(R.color.white)
                .setDismissOnColorSelected(true)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                        if (positiveResult) {
                            Toast.makeText(context, "Color selected: #" +
                                    Integer.toHexString(color).toUpperCase(),
                                    Toast.LENGTH_SHORT).show();
                            drawingView.setColor(color);
                        }
                    }
                }).build().show(fragmentManager, "get Colors");
    }
}
