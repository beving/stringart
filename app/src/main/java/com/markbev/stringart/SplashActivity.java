package com.markbev.stringart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by e62032 on 7/22/2016.
 * From: https://www.bignerdranch.com/blog/splash-screens-the-right-way/
 */
public class SplashActivity extends AppCompatActivity {

    //Not best practice, but using since app loads too fast to see the image
    private static final int DELAY_MILLIS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, DELAY_MILLIS);
    }
}