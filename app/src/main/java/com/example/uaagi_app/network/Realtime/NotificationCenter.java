package com.example.uaagi_app.network.Realtime;

public class NotificationCenter {

    public interface Listener {
        void onNotification(String title, String message);
    }

    private static Listener listener;

    public static void setListener(Listener l) {
        listener = l;
    }

    public static void notify(String title, String message) {
        if (listener != null) {
            listener.onNotification(title, message);
        }
    }
}
