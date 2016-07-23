package com.marksoft.stringart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by e62032 on 7/22/2016.
 * From: https://www.bignerdranch.com/blog/splash-screens-the-right-way/
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class); //TODO may want a timer because my app starts so fast ;)
        startActivity(intent);
        finish();
    }
}