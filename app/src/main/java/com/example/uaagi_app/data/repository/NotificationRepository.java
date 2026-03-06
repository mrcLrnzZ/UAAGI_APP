package com.example.uaagi_app.data.repository;

import com.example.uaagi_app.data.model.NotificationModel;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Looper;

public class NotificationRepository {

    private static NotificationRepository instance;
    private List<NotificationModel> notifications;
    private List<NotificationsListener> listeners;
    private Handler handler;

    private NotificationRepository() {
        notifications = new ArrayList<>();
        listeners = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper());

        handler.post(updateRunnable);
    }

    public static synchronized NotificationRepository getInstance() {
        if (instance == null) {
            instance = new NotificationRepository();
        }
        return instance;
    }

    public void addNotification(NotificationModel notification) {
        notifications.add(0, notification);
        notifyListeners();
    }

    public List<NotificationModel> getNotifications() {
        return notifications;
    }

    public void clearNotifications() {
        notifications.clear();
        notifyListeners();
    }

    public void addListener(NotificationsListener listener) {
        listeners.add(listener);
        listener.onNotificationsUpdated(notifications);
    }

    public void removeListener(NotificationsListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (NotificationsListener listener : listeners) {
            listener.onNotificationsUpdated(notifications);
        }
    }

    // Runnable that updates "time ago" every second
    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            notifyListeners(); // notify UI to refresh timeAgo
            handler.postDelayed(this, 1000); // repeat every second
        }
    };
    public interface NotificationsListener {
        void onNotificationsUpdated(List<NotificationModel> notifications);
    }
}
