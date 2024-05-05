package com.mtm.happyending.Database;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.PopupMenu;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mtm.happyending.EditPage;
import com.mtm.happyending.R;
import com.mtm.happyending.ViewDiary;

import java.util.List;

public class Adapter extends ArrayAdapter<HappyEndingModel> {

    private final Context context;
    private final int resource;
    private TextView title, description, date;
    private ImageButton openMenu;
    private final List<HappyEndingModel> happyEndingModels;

    public Adapter(Context context, int resource, List<HappyEndingModel> happyEndingModels){
        super(context, resource, happyEndingModels);
        this.context = context;
        this.resource = resource;
        this.happyEndingModels = happyEndingModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource, parent, false);
        date =row.findViewById(R.id.dateShowHere);
        title = row.findViewById(R.id.display_title);
        description = row.findViewById(R.id.Display_description);
        openMenu = row.findViewById(R.id.openEditMenu);

        HappyEndingModel happyEndingModel = happyEndingModels.get(position);
        date.setText(happyEndingModel.getDate());
        title.setText(happyEndingModel.getTitle());
        description.setText(happyEndingModel.getDescription());
        int pageId = happyEndingModel.getId();

        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v); // 'context' is your activity or fragment context
                popupMenu.inflate(R.menu.menu_dropdown); // Inflate the menu resource
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Handle menu item clicks here
                        if (item.getItemId() == R.id.menu_item1) {
                            Intent intent = new Intent(context, EditPage.class);
                            intent.putExtra("id", String.valueOf(pageId));

                            context.startActivity(intent);
                            return true;
                        } else if (item.getItemId() == R.id.menu_item2) {
                            // Handle menu item 2 click
                            DBHandler dbHandler = new DBHandler(context);
                            context.startActivity(new Intent(context, ViewDiary.class));
                            dbHandler.deletePage(pageId);
                            Toast.makeText(context, "Page Deleted", Toast.LENGTH_SHORT).show();


                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show(); // Show the PopupMenu
            }
        });

        return row;

    }
}
