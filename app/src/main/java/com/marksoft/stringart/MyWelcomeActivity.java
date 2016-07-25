package com.marksoft.stringart;


import android.content.Intent;
import android.widget.Toast;

import com.stephentuso.welcome.WelcomeScreenBuilder;
import com.stephentuso.welcome.WelcomeScreenHelper;
import com.stephentuso.welcome.ui.WelcomeActivity;
import com.stephentuso.welcome.util.WelcomeScreenConfiguration;

/**
 * Created by stephentuso on 11/15/15.
 * https://github.com/stephentuso/welcome-android/blob/master/sample/src/main/java/com/stephentuso/welcomeexample/MyWelcomeActivity.java
 * https://android-arsenal.com/details/1/3610
 */
public class MyWelcomeActivity extends WelcomeActivity {

    private boolean welcomeScreenComplete = false;

    @Override
    protected WelcomeScreenConfiguration configuration() {
        return new WelcomeScreenBuilder(this)
                //.theme(R.style.CustomWelcomeScreenTheme)
                .defaultTitleTypefacePath("Montserrat-Bold.ttf")
                .defaultHeaderTypefacePath("Montserrat-Bold.ttf")
                .titlePage(R.drawable.logo, "Welcome to String Art", R.color.text_theme_light)
//                .basicPage(R.drawable.instructions1, " ", " ")
//                .basicPage(R.drawable.instructions2, " ", " ")
//                .basicPage(R.drawable.instructions3, " ", " ")
//                .basicPage(R.drawable.instructions4, " ", " ")
//                .basicPage(R.drawable.instructions5, " ", " ")
                //.parallaxPage(R.layout.parallax_example, "Easy parallax", "Supply a layout and parallax effects will automatically be applied", R.color.text_theme_light, 0.2f, 2f)
                //.basicPage(R.drawable.photo, "Customizable", "All elements of the welcome screen can be customized easily.", R.color.text_theme_light)
                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WelcomeScreenHelper.DEFAULT_WELCOME_SCREEN_REQUEST) {
            String welcomeKey = data.getStringExtra(WelcomeActivity.WELCOME_SCREEN_KEY);

            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), welcomeKey + " completedddddd", Toast.LENGTH_SHORT).show();
                welcomeScreenComplete = true;
            } else {
                Toast.makeText(getApplicationContext(), welcomeKey + " canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}