package org.mehdi.nezamipour.skybeat.controller.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import org.mehdi.nezamipour.skybeat.R;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                startActivity(MainActivity.newIntent(SplashActivity.this));
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}