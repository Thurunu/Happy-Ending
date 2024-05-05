package com.mtm.happyending;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.mtm.happyending.Database.Adapter;
import com.mtm.happyending.Database.DBHandler;
import com.mtm.happyending.Database.HappyEndingModel;
import com.mtm.happyending.R;

import java.util.ArrayList;
import java.util.List;

public class ViewDiary extends AppCompatActivity {

    private ListView listView;
    private ImageButton openMenu;
    private DBHandler dbHandler;
    private List<HappyEndingModel> happyEndings;


    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diary);

        context = this;
        listView = findViewById(R.id.listView);
        dbHandler = new DBHandler(context);

        happyEndings = new ArrayList<>();
        happyEndings = dbHandler.getAllPages();

        Adapter adapter = new Adapter(context, R.layout.single_page, happyEndings);

        listView.setAdapter(adapter);


    }
}