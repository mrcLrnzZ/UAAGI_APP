package com.example.uaagi_app.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeAgo {

    public static String getTimeAgo(String createdAt) {
        // Parse the timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createdTime = LocalDateTime.parse(createdAt, formatter);

        // Convert to ZonedDateTime in system default zone
        ZonedDateTime createdZoned = createdTime.atZone(ZoneId.systemDefault());
        ZonedDateTime now = ZonedDateTime.now();

        Duration duration = Duration.between(createdZoned, now);

        long seconds = duration.getSeconds();

        if (seconds < 60) return seconds + " seconds ago";
        long minutes = seconds / 60;
        if (minutes < 60) return minutes + " minutes ago";
        long hours = minutes / 60;
        if (hours < 24) return hours + " hours ago";
        long days = hours / 24;
        if (days < 7) return days + " days ago";
        long weeks = days / 7;
        if (weeks < 4) return weeks + " weeks ago";
        long months = days / 30;
        if (months < 12) return months + " months ago";
        long years = days / 365;
        return years + " years ago";
    }

}