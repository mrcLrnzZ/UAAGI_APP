package com.example.uaagi_app.data.model;

public class NotificationModel {

    private String title;
    private String message;
    private String timeAgo;

    public NotificationModel(String title, String message, String timeAgo) {
        this.title = title;
        this.message = message;
        this.timeAgo = timeAgo;
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getTimeAgo() { return timeAgo; }
}
