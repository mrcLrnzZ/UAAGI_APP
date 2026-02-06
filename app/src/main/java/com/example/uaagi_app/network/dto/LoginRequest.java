package com.example.uaagi_app.network.dto;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.uaagi_app.network.VolleySingleton;
import com.example.uaagi_app.network.api.LoginAuth;
import com.example.uaagi_app.ui.users.Login;
import com.example.uaagi_app.ui.users.PreEmpActivity;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.Helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest {
    private final Context context;
    private static final String SEND_OTP_URL = "https://uaagionehire.bscs3b.com/MobileAPI/api/sendOtp.php";
    private static final int TIMEOUT_MS = 5000;

    public LoginRequest(Context context) {
        this.context = context;
    }
    public void requestLoginOtp(String email, LoginCallback callback) {
        StringRequest request = new StringRequest(Request.Method.POST, SEND_OTP_URL,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        boolean success = obj.getBoolean("success");
                        callback.onResponse(success, obj);
                    } catch (JSONException e) {
                        // Log the full server response and the exception
                        Log.e("LoginRequest", "Invalid JSON response: " + response, e);
                        callback.onError("Invalid server response: " + e.getMessage());
                    }
                },
                error -> {
                    String message = error.getMessage() != null ? error.getMessage() : "Unknown network error";
                    callback.onError("Network error: " + message);
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface LoginCallback {
        void onResponse(boolean success, JSONObject response);
        void onError(String errorMessage);
    }
}
