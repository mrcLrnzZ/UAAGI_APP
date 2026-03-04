package com.example.uaagi_app.network.Services;

import android.util.Log;

import com.example.uaagi_app.network.dto.ApiResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Response;

public class RetrofitErrorHandler {
    private static final String TAG = "RetrofitErrorHandler";

    private static final Gson gson = new Gson();

    public static void handleError(Response<?> response, Throwable t, ApiErrorCallback callback) {
        if (t != null) {
            // Network failure
            Log.e(TAG, "Network Error", t);
            String message = t.getMessage() != null ? t.getMessage() : "Network request failed";
            callback.onError(message);
            return;
        }

        if (response != null && !response.isSuccessful()) {
            int code = response.code();
            String errorBody = "";
            try {
                if (response.errorBody() != null) {
                    errorBody = response.errorBody().string();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error reading error body", e);
            }

            Log.e(TAG, "HTTP Status: " + code + ", Response: " + errorBody);
            try {
                ApiResponse<?> apiResponse = gson.fromJson(errorBody, ApiResponse.class);
                if (apiResponse != null && apiResponse.getMessage() != null) {
                    callback.onError(apiResponse.getMessage());
                } else {
                    callback.onError("Error " + code);
                }
            } catch (Exception e) {
                callback.onError("Error " + code + ": " + errorBody);
            }
            return;
        }
        callback.onError("Unknown error occurred");
    }

    public interface ApiErrorCallback {
        void onError(String errorMessage);
    }
}