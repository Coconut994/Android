package com.example.finalassigment;

public class Story {
    private int id;
    private int imageRes;
    private String title;
    private long views;


    public Story(int id, int imageRes, String title, long views) {
        this.id = id;
        this.imageRes = imageRes;
        this.title = title;
        this.views = views;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter methods
    public int getImageRes() { return imageRes; }
    public String getTitle() { return title; }
    public long getViews() { return views; }
}