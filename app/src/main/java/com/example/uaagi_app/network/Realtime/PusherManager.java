package com.example.uaagi_app.network.Realtime;

import android.util.Log;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONObject;

public class PusherManager {

    private Pusher pusher;

    public void connect() {

        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");

        pusher = new Pusher("d4f8ce6446750b235dac", options);

        pusher.connect();
    }
    public void disconnect() {

        if (pusher != null) {

            try {
                pusher.disconnect();
                pusher = null;

                Log.d("PUSHER", "Disconnected from Pusher");

            } catch (Exception e) {
                Log.e("PUSHER", "Disconnect error: " + e.getMessage());
            }

        }
    }
    public void subscribeToUserChannel(int userId, NotificationListener listener) {

        String channelName = "user-" + userId;

        Channel channel = pusher.subscribe(channelName);

        channel.bind("new-notification", event -> {

            try {

                JSONObject json = new JSONObject(event.getData());

                String title = json.getString("title");
                String message = json.getString("message");
                String createdAt = json.getString("created_at");

                listener.onNewNotification(title, message, createdAt);
                Log.d("PUSHER", title + " " + message);

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
    public void subscribeToPublicChannel(NotificationListener listener) {

        String channelName = "public";

        Channel channel = pusher.subscribe(channelName);

        channel.bind("announcement", event -> {

            try {

                JSONObject json = new JSONObject(event.getData());

                String title = json.getString("title");
                String message = json.getString("message");
                String createdAt = json.getString("created_at");

                listener.onNewNotification(title, message, createdAt);
                Log.d("PUSHER", "announcement: " + title);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
    public interface NotificationListener {
        void onNewNotification(String title, String message, String createdAt);
    }
}