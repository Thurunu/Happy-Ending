package com.mtm.happyending.Database;

public class HappyEndingModel {
    private int id;
    private String title, description, imageLocation;
    private String date;
    private String time;

    public HappyEndingModel() {
    }

    public HappyEndingModel(int id, String title, String description, String imageLocation, String date, String time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageLocation = imageLocation;
        this.date = date;
        this.time = time;
    }

    public HappyEndingModel(String title, String description, String imageLocation, String date, String time) {
        this.title = title;
        this.description = description;
        this.imageLocation = imageLocation;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
