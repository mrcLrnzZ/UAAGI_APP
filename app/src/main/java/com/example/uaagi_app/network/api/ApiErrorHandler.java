package com.example.uaagi_app.network.api;

import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class ApiErrorHandler {
    private static final String TAG = "ApiErrorHandler";

    public static void handleError(VolleyError error, ApiErrorCallback callback) {
        Log.e(TAG, "Volley Error", error);

        if (error instanceof TimeoutError) {
            callback.onError("Request timed out. Please try again.");
            return;
        } else if (error instanceof NoConnectionError) {
            callback.onError("No internet connection. Please try again.");
            return;
        }

        if (error.networkResponse != null && error.networkResponse.data != null) {
            int statusCode = error.networkResponse.statusCode;
            String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
            Log.e(TAG, "HTTP Status: " + statusCode + ", Response: " + responseBody);

            try {
                JSONObject json = new JSONObject(responseBody);
                callback.onError(json.optString("message", "Error " + statusCode));
            } catch (JSONException e) {
                callback.onError("Error " + statusCode + ": " + responseBody);
            }
        } else {
            callback.onError("Unknown error occurred");
        }
    }

    public interface ApiErrorCallback {
        void onError(String errorMessage);
    }
}
