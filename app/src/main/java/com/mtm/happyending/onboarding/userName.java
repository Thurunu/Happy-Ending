package com.mtm.happyending.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mtm.happyending.R;

public class userName extends AppCompatActivity {

    private EditText userName;
    private Button nextButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);

        context = this;

        nextButton = findViewById(R.id.nextButton);
        userName = findViewById(R.id.userNameInputField);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Check if username already exists
        String savedUsername = sharedPreferences.getString("username", null);
        if (savedUsername != null && !savedUsername.isEmpty()) {
            // Username already exists, switch to next screen
            Intent intent = new Intent(context, PasswordScreen.class);
            startActivity(intent);
            finish(); // Finish this activity to prevent going back to it when pressing back button
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString();
                if (name.isEmpty()) {
                    // Display a toast message if the username field is empty
                    Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    // Store the username in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", name);
                    editor.apply();
                    Intent intent = new Intent(context, PasswordScreen.class);
                    startActivity(intent);
                    finish(); // Finish this activity to prevent going back to it when pressing back button
                }
            }
        });
    }
}
