package com.example.uaagi_app.network.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.uaagi_app.network.VolleySingleton;
import com.example.uaagi_app.utils.Helpers;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginOtpService {
    private final Context context;
    private static final String BASE_URL = "https://uaagionehire.bscs3b.com/MobileAPI/api/index.php";
    private static final String TAG = "LoginOtpService";

    public LoginOtpService(Context context) {
        this.context = context;
    }

    public void requestOtp(String email, LoginCallback callback) {
        String url = BASE_URL + "/auth/send-otp";

        JSONObject body = new JSONObject();
        try {
            body.put("email", email);
        } catch (JSONException e) {
            callback.onError("Failed to create request body");
            return;
        }
        Log.d(TAG, "Request URL: " + url);
        Log.d(TAG, "Request body: " + body);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                response -> handleSuccess(response, callback),
                error -> handleError(error, callback)
        );
        applyRetryPolicy(request);
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    private void handleSuccess(JSONObject response, LoginCallback callback) {
        boolean success = response.optBoolean("success", false);

        if (!success) {
            callback.onError(response.optString("message", "Unknown error"));
            return;
        }

        callback.onResponse(true, response);
    }

    private void handleError(VolleyError error, LoginCallback callback) {
        Log.e(TAG, "Volley Error", error);

        if (error.networkResponse != null) {
            Log.e(TAG, "HTTP Status Code: " + error.networkResponse.statusCode);
        }

        callback.onError("Server error");
    }

    private void applyRetryPolicy(JsonObjectRequest request) {
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
    }

    public interface LoginCallback {
        void onResponse(boolean success, JSONObject response);
        void onError(String errorMessage);
    }
}
