package com.mtm.happyending.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {

    //We use version to update database.
    private static final int VERSION = 1;
    private static final String DB_NAME = "HappyEnding";
    private static final String TABLE_NAME = "happy_ending";

    //Column Names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    private static final String IMAGE_DIRECTORY = "image_directory";
    private final String TIME = "time";
    private final String DATE = "date";


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                IMAGE_DIRECTORY + " TEXT, " +
                TIME + " TEXT, " + // Adding the "time" column
                DATE + " DATE DEFAULT '0000-01-01')";
        db.execSQL(createTableQuery);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);

    }

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public void addPage(HappyEndingModel happyEndingModel) {
        SQLiteDatabase sqlLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues(); //We use this to make organize data before send it to the database.

        contentValues.put(TITLE, happyEndingModel.getTitle());
        contentValues.put(DESCRIPTION, happyEndingModel.getDescription());
        contentValues.put(IMAGE_DIRECTORY, happyEndingModel.getImageLocation());
        contentValues.put(TIME, happyEndingModel.getTime());
        contentValues.put(DATE, happyEndingModel.getDate());

        //saving data to database
        long rowID = sqlLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Log.i("Row ID:",String.valueOf(rowID));

        //optional : then we can close the database
        sqlLiteDatabase.close();
    }
    //Get all pages

    public List<HappyEndingModel> getAllPages() {
        List<HappyEndingModel> happyEnding = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) { // first we check if the database have data using following command.
            do {
                HappyEndingModel happyEndingModel = new HappyEndingModel();
                happyEndingModel.setId(cursor.getInt(0));
                happyEndingModel.setTitle(cursor.getString(1));
                happyEndingModel.setDescription(cursor.getString(2));
                happyEndingModel.setImageLocation(cursor.getString(3));
                happyEndingModel.setTime(cursor.getString(4));
                happyEndingModel.setDate(cursor.getString(5));

                happyEnding.add(happyEndingModel);
            } while (cursor.moveToNext());
        }
        return happyEnding;
    }

    // Delete item

    public void deletePage(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});

        db.close();
    }

    //update single page
    public int updateSinglePage(HappyEndingModel model){

        SQLiteDatabase sqlLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues(); //We use this to make organize data before send it to the database.


        contentValues.put(TITLE, model.getTitle());
        contentValues.put(DESCRIPTION, model.getDescription());
        contentValues.put(IMAGE_DIRECTORY, model.getImageLocation());
        contentValues.put(TIME, model.getTime());
        contentValues.put(DATE, model.getDate());


        //update data on the database

        int status = sqlLiteDatabase.update(
                TABLE_NAME,
                contentValues,
                ID + "= ?",
                new String[]{String.valueOf(model.getId())}
        );


        //optional : then we can close the database
        sqlLiteDatabase.close();

        return status;
    }

    // Single page view
    public HappyEndingModel getSingleToDo(int id){
        SQLiteDatabase db = getWritableDatabase();
        System.out.println(id);
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        ID,
                        TITLE,
                        DESCRIPTION,
                        IMAGE_DIRECTORY,
                        TIME,
                        DATE

                },
                ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        HappyEndingModel model;

        if(cursor != null && cursor.moveToNext()){

//            Log.d("CursorDebug", "ID: " + cursor.getInt(0) +
//                    ", Title: " + cursor.getString(1) +
//                    ", Description: " + cursor.getString(2) +
//                    ", Started: " + cursor.getLong(3) +
//                    ", Finished: " + cursor.getLong(4));

            model = new HappyEndingModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));

            return model;
        }
        return null;
    }

}
