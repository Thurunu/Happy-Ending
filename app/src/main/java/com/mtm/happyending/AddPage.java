package com.mtm.happyending;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mtm.happyending.Database.DBHandler;
import com.mtm.happyending.Database.HappyEndingModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AddPage extends AppCompatActivity {
    private ImageButton calendar, yesterday, tomorrow, backButton, addImage;
    private TextView today;
    private EditText headline, startTyping;
    private ImageView imageDisplay;
    private Button saveButton;
    private Context context;
    private LocalDate currentDate;
    private Uri imagePath;

    ActivityResultLauncher<PickVisualMediaRequest> launcher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri o) {
            if(o == null){
                Toast.makeText(AddPage.this, "No image Selected", Toast.LENGTH_SHORT).show();
            }else{
                imagePath = o;
                Glide.with(getApplicationContext()).load(o).into(imageDisplay);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        context = this;

        calendar = findViewById(R.id.calendarButton);
        today = findViewById(R.id.todayDate);
        yesterday = findViewById(R.id.previousdate);
        tomorrow = findViewById(R.id.forwarddate);
        backButton = findViewById(R.id.back_button_main);
        headline = findViewById(R.id.headline);
        startTyping = findViewById(R.id.startTyping);
        imageDisplay = findViewById(R.id.selectImageDisplay);
        addImage = findViewById(R.id.addImageButton);
        saveButton = findViewById(R.id.saveButton);

        // Set initial date
        currentDate = LocalDate.now();
        updateDateTextView(currentDate);

        // Date Picker
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Yesterday button
        yesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate = currentDate.minusDays(1); // Move back by one day
                updateDateTextView(currentDate);
            }
        });

        // Tomorrow button
        tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate = currentDate.plusDays(1); // Move forward by one day
                updateDateTextView(currentDate);
            }
        });

        //Back to home
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MainActivity.class));
            }
        });

        // add image
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                        .build());
            }
        });

        //Save button action
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = headline.getText().toString();
                String description = startTyping.getText().toString();
                String imageLocation = imagePath != null ? imagePath.toString() : null; // Check if imagePath is null
                String dateString = today.getText().toString(); // Get the text from the TextView
                String time = Calendar.getInstance().getTime().toString();

                HappyEndingModel happyEndingModel = new HappyEndingModel(title, description, imageLocation, dateString, time);
                DBHandler dbHandler = new DBHandler(context);
                dbHandler.addPage(happyEndingModel);

                startActivity(new Intent(context, MainActivity.class));



            }
        });
    }

    private void updateDateTextView(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = formatter.format(date);
        today.setText(formattedDate);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        currentDate = LocalDate.of(year, month + 1, dayOfMonth);
                        updateDateTextView(currentDate);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}
