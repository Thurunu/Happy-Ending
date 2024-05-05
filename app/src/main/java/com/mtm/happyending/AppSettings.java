package com.mtm.happyending;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class AppSettings extends AppCompatActivity {

    private ImageView imageView;
    private Button changeName, changePassword, restore, backup;
    private ImageButton backButton;
    private TextView userName;
    private Context context;
    private Uri imagePath;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String IMAGE_URI_KEY = "imageUri";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    ActivityResultLauncher<PickVisualMediaRequest> launcher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri o) {
            if(o == null){
                Toast.makeText(AppSettings.this, "No image Selected", Toast.LENGTH_SHORT).show();
            }else{
                imagePath = o;
                Glide.with(getApplicationContext()).load(o).into(imageView);
                sharedPreferences.edit().putString(IMAGE_URI_KEY, o.toString()).apply();

            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        context = this;

        imageView = findViewById(R.id.imageView);
        changeName = findViewById(R.id.changeName);
        changePassword = findViewById(R.id.changePassword);
        restore = findViewById(R.id.restore);
        backup = findViewById(R.id.backup);
        userName = findViewById(R.id.developerName);
        backButton = findViewById(R.id.back_button_main);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //Load profile pic
        String imageDirectory = sharedPreferences.getString(IMAGE_URI_KEY, "");
        imagePath = Uri.parse(imageDirectory);
        Glide.with(getApplicationContext()).load(imagePath).into(imageView);

        // Load username from SharedPreferences and display it
        String savedUsername = sharedPreferences.getString(USERNAME_KEY, null);
        if (savedUsername != null) {
            userName.setText(savedUsername);
        }

        //back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MainActivity.class));
            }
        });


        //Change Profile Pic
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launcher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                        .build());
            }


        });

        //change username
        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog.Builder instance with custom style
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.RoundedCornersDialog));

                // Set the title and message for the dialog
                builder.setTitle("Insert Name");
                builder.setMessage("Please insert your name:");

                // Add an EditText to the dialog for the user to input their name
                final EditText input = new EditText(context);
                builder.setView(input);

                // Set up the buttons for the dialog
                builder.setPositiveButtonIcon(ContextCompat.getDrawable(context, R.drawable.done));
                builder.setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the text entered by the user
                        String name = input.getText().toString();
                        sharedPreferences.edit().putString(USERNAME_KEY, name).apply(); // Save the password
                        Toast.makeText(context, "Your name is: " + name, Toast.LENGTH_SHORT).show();

                        // You can also save the name to SharedPreferences or any other data storage mechanism
                    }
                });
                builder.setNegativeButtonIcon(ContextCompat.getDrawable(context, R.drawable.close));
                builder.setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked cancel, do nothing or handle the cancel action
                    }
                });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                // Apply rounded corners to the dialog's window
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_corners_background);
            }
        });

        //change password

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.RoundedCornersDialog));

                builder.setTitle("Change Password");
                builder.setTitle("Please enter 4 digit number:");

                builder.setPositiveButtonIcon(ContextCompat.getDrawable(context, R.drawable.done));
                builder.setNegativeButtonIcon(ContextCompat.getDrawable(context, R.drawable.close));

                final EditText input = new EditText(context);
                builder.setView(input);

                builder.setPositiveButton("", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String password = input.getText().toString();
                        sharedPreferences.edit().putString(PASSWORD_KEY, password).apply(); // Save the password

                        Toast.makeText(context, "Password Change Successfully." , Toast.LENGTH_SHORT).show();


                    }
                });

                builder.setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                // Apply rounded corners to the dialog's window
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_corners_background);
            }
        });


        //backup





    }
}