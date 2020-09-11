package com.java.jingjia.ui.cluster;

public class EventItem {

    private final String TAG = "EventItem";
    private String id, title, date;

    public EventItem(String id, String title, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
