package com.mtm.happyending;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

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

import com.bumptech.glide.Glide;
import com.mtm.happyending.Database.DBHandler;
import com.mtm.happyending.Database.HappyEndingModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class EditPage extends AppCompatActivity {
    private ImageButton calendar, yesterday, tomorrow, backButton, addImage;
    private TextView today;
    private EditText headline, startTyping;
    private ImageView imageDisplay;
    private Button saveButton;
    private Context context;
    private LocalDate currentDate;
    private String imageLocation;
    private Uri imagePath;

    ActivityResultLauncher<PickVisualMediaRequest> launcher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri o) {
            if(o == null){
                Toast.makeText(EditPage.this, "No image Selected", Toast.LENGTH_SHORT).show();
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

        // Catch the parsed id from the adapter
        DBHandler dbHandler = new DBHandler(context);
        final String id = getIntent().getStringExtra("id");
        HappyEndingModel happyEndingModel =  dbHandler.getSingleToDo(Integer.parseInt(id));

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

        // Format and set the date
        String dateFromDatabase = happyEndingModel.getDate();
        String formattedDate = formatDate(dateFromDatabase);
        currentDate = LocalDate.parse(formattedDate);
        today.setText(formattedDate);

        // Set title and description
        headline.setText(happyEndingModel.getTitle());
        startTyping.setText(happyEndingModel.getDescription());

        // Set button text
        saveButton.setText("Edit Page");

        // Set image
        imageLocation = happyEndingModel.getImageLocation();
        imagePath = Uri.parse(imageLocation);
        Glide.with(context)
                .load(imagePath)
                .into(imageDisplay);


        //Back Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ViewDiary.class));
                finish();
            }
        });


        //Update goes here

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

                HappyEndingModel happyEndingModel = new HappyEndingModel(Integer.parseInt(id),title, description, imageLocation, dateString, time);
                int state = dbHandler.updateSinglePage(happyEndingModel);
                if (state > 0) {
                    // Update successful
                    Toast.makeText(context, "To-do updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Update failed
                    Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(context, ViewDiary.class));





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




//Format date
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
            Date date = inputFormat.parse(dateStr);

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return ""; // Return empty string or handle the error accordingly
        }
    }
}