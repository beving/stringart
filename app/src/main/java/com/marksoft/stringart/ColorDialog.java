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

        int[] colors = {
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                Color.CYAN, Color.MAGENTA,Color.BLACK, Color.GRAY,
                0xFF000080,0xFF808000, 0xFF800080, 0xFF008080, //end of orig
                0xFFFF6600, 0xFFFF9900,//prefer
                0xFF9999FF, 0xFF993366, 0xFFFFFFCC, 0xFFCCFFFF, 0xFF660066, 0xFFFF8080,
                0xFF0066CC, 0xFFCCCCFF, 0xFF000080, 0xFFFFFF00, 0xFF00FFFF,
                0xFF800080, 0xFF800000, 0xFF008080, 0xFF0000FF, 0xFF00CCFF, 0xFFCCFFFF,
                0xFFCCFFCC, 0xFFFFFF99, 0xFF99CCFF, 0xFFFF99CC, 0xFFCC99FF, 0xFFFFCC99,
                0xFF3366FF, 0xFF33CCCC, 0xFF99CC00, 0xFFFFCC00,
                0xFF666699, 0xFF969696, 0xFF003366, 0xFF339966, 0xFF003300, 0xFF333300,
                0xFF993300
        };

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
                                    Toast.LENGTH_LONG).show();
                            drawingView.getDataHandler().getDataFragment().setLastSelectedColor(color);
                            drawingView.drawLines();

                            Log.d("ColorDialog", "Color set to: " + color);
                        }
                    }
                }).build().show(fragmentManager, "get Colors");
    }
}
