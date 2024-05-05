package com.mtm.happyending.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mtm.happyending.MainActivity;
import com.mtm.happyending.R;

public class EnterPassword extends AppCompatActivity {

    private Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0;
    private TextView  passwordField;
    private String password = "";
    private String savedPassword;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_screen);

        context = this;

        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        passwordField = findViewById(R.id.editTextNumberPassword);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        savedPassword = sharedPreferences.getString("password","");

        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (password.length() < 4) {
                    // If the password is not complete, it's still the initial password entry
                    password += button.getText().toString();
                    passwordField.setText(password);
                }
                if(password.length() == 4){
                    validate();
                }
            }
        };

        btn_0.setOnClickListener(numberClickListener);
        btn_1.setOnClickListener(numberClickListener);
        btn_2.setOnClickListener(numberClickListener);
        btn_3.setOnClickListener(numberClickListener);
        btn_4.setOnClickListener(numberClickListener);
        btn_5.setOnClickListener(numberClickListener);
        btn_6.setOnClickListener(numberClickListener);
        btn_7.setOnClickListener(numberClickListener);
        btn_8.setOnClickListener(numberClickListener);
        btn_9.setOnClickListener(numberClickListener);


    }
    private void validate(){

        if(!password.equals(savedPassword)){
            Toast.makeText(context, "Pin number doesn't match, please try again.", Toast.LENGTH_SHORT).show();
            resetPassword();
        }
        else{
            switchToNextScreen();
        }
    }
    private void resetPassword() {
        password = "";
        passwordField.setText("");
    }

    private void switchToNextScreen() {

        // Start a new activity or do any action you want here
        Intent intent = new Intent(context, MainActivity.class);
        Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }
}