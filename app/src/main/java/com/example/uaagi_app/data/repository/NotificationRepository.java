package com.example.uaagi_app.data.repository;

import com.example.uaagi_app.data.model.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationRepository {

    private static NotificationRepository instance;
    private List<NotificationModel> notifications;

    private NotificationRepository() {
        notifications = new ArrayList<>();
    }

    public static synchronized NotificationRepository getInstance() {
        if (instance == null) {
            instance = new NotificationRepository();
        }
        return instance;
    }

    public void addNotification(NotificationModel notification) {
        notifications.add(0, notification);
    }

    public List<NotificationModel> getNotifications() {
        return notifications;
    }

    public void clearNotifications() {
        notifications.clear();
    }
}
