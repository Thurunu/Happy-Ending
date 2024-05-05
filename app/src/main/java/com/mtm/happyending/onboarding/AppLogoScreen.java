package com.mtm.happyending.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.mtm.happyending.MainActivity;
import com.mtm.happyending.R;

public class AppLogoScreen extends AppCompatActivity {

    private static final int DELAY_TIME_MILLISECONDS = 2000; // Delay time in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_logo_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if user has a password
                if (hasPassword()) {
                    // User has a password, navigate to MainActivity
                    navigateToPasswordInputScreen();
                } else {
                    // User doesn't have a password, navigate to password creation screen
                    navigateToPasswordCreationScreen();
                }
            }
        }, DELAY_TIME_MILLISECONDS);
    }

    private boolean hasPassword() {
        // Get SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String password = sharedPreferences.getString("password", "");
        return !password.equals("");

        // Check if password exists
    }

    private void navigateToPasswordInputScreen() {
        Intent intent = new Intent(AppLogoScreen.this, EnterPassword.class);
        startActivity(intent);
        finish();
    }

    private void navigateToPasswordCreationScreen() {
        Intent intent = new Intent(AppLogoScreen.this, userName.class);
        startActivity(intent);
        finish();
    }
}
