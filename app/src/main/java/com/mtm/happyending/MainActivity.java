package com.mtm.happyending;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final int[] imagePalate = {R.drawable.b_1, R.drawable.b_2, R.drawable.b_3, R.drawable.b_4, R.drawable.b_5, R.drawable.b_6, R.drawable.b_7};
    private static final long INTERVAL_ONE_DAY = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

    private TextView greetings, userNameGoesHere;
    private ImageView trendingImageView;
    private Button addButton, viewButton, settings;

    private Context context;
    private final Handler handler = new Handler();
    private Runnable updateBackgroundTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);


        greetings = findViewById(R.id.greetings);
        trendingImageView = findViewById(R.id.trendingImageView);
        userNameGoesHere = findViewById(R.id.userNameGoesHere);
        addButton = findViewById(R.id.addPage);
        viewButton = findViewById(R.id.viewDiary);
        settings = findViewById(R.id.settings);

        userNameGoesHere.setText(getUserName(sharedPreferences));

        updateBackgroundTask = new Runnable() {
            @Override
            public void run() {
                // Update background drawable
                int index = new Random().nextInt(imagePalate.length);
                trendingImageView.setBackgroundResource(imagePalate[index]);

                // Reschedule the task to run again after one day
                handler.postDelayed(this, INTERVAL_ONE_DAY);
            }
        };

        // Start the task when the activity is created
        handler.post(updateBackgroundTask);

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddPage.class);
            startActivity(intent);
        });
        viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewDiary.class);
            startActivity(intent);
        });
        settings.setOnClickListener(v -> {
            Intent intent = new Intent(context, AppSettings.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop the task when the activity pauses to prevent memory leaks
        handler.removeCallbacks(updateBackgroundTask);
    }

    private String getUserName(SharedPreferences sharedPreferences){
        String savedUsername = sharedPreferences.getString("username", null);

        return savedUsername;
    }
}
