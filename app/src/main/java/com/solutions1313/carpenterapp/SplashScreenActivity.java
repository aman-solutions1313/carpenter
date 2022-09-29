package com.solutions1313.carpenterapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.solutions1313.carpenterapp.carpenter.CarpenterDashboardActivity;
import com.solutions1313.carpenterapp.carpenter.CarpenterLoginActivity;
import com.solutions1313.carpenterapp.staff.StaffDashboardActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 500;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        context = SplashScreenActivity.this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(SplashScreenActivity.this);

        new Handler().postDelayed(() -> {

            boolean isLoggedIn = wmbPreference.getBoolean("LoggedIn", false);
            Intent intent;
            if (!isLoggedIn) {
                intent = new Intent(SplashScreenActivity.this, CarpenterLoginActivity.class);
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            } else {
                //if logged in check which type of login is done ...
                SharedPreferences getShared = getSharedPreferences("user_id", MODE_PRIVATE);
                boolean isCarpenterLogin = getShared.getBoolean("isCarpenterLogin", false);

                if (isCarpenterLogin) {
                    intent = new Intent(SplashScreenActivity.this, CarpenterDashboardActivity.class);

                } else {
                    intent = new Intent(SplashScreenActivity.this, StaffDashboardActivity.class);
                }
            }
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN);
    }
}
