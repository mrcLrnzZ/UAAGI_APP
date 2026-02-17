package com.example.uaagi_app.network.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.uaagi_app.network.VolleySingleton;
import com.example.uaagi_app.network.dto.LoginResponse;
import com.example.uaagi_app.utils.Helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class LoginAuthService {
    private final Context context;
    private static final String BASE_URL = "https://uaagionehire.bscs3b.com/MobileAPI/api/index.php";
    private static final String VERIFY_LOGIN_URL = BASE_URL + "/auth/verify";
    private static final int TIMEOUT_MS = 10000;

    public LoginAuthService(Context context) {
        this.context = context;
    }

    public void verifyLogin(String email, String otp, VerifyLoginCallback callback) {

        JSONObject body = new JSONObject();
        try {
            body.put("email", email);
            body.put("otp", otp.toLowerCase());
        } catch (JSONException e) {
            callback.onError("Failed to create request body");
            return;
        }
        Log.d("LoginAuthService", "Request URL: " + VERIFY_LOGIN_URL);
        Log.d("LoginAuthService", "Request body: " + body);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                VERIFY_LOGIN_URL,
                body,
                response -> handleSuccess(response, callback),
                error -> handleError(error, callback)
        );

        // Retry policy
        request.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    private void handleSuccess(JSONObject response, VerifyLoginCallback callback) {
        boolean success = response.optBoolean("success", false);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.success = success;
        loginResponse.message = response.optString("message");
        loginResponse.formExist = response.optBoolean("formExist", false);
        loginResponse.userId = response.optInt("userId", 0);
        Helpers.saveLoginState(context);
        Helpers.saveUserId(context, loginResponse.userId);
        if (!success) {
            callback.onError(loginResponse.message);
            return;
        }

        callback.onResponse(loginResponse);
    }

    private void handleError(com.android.volley.VolleyError error, VerifyLoginCallback callback) {
        Log.e("LoginAuthService", "Volley Error", error);

        String errorMessage = "Network error";

        if (error.networkResponse != null && error.networkResponse.data != null) {
            String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
            Log.e("LoginAuthService", "Server Response: " + responseBody);

            try {
                JSONObject json = new JSONObject(responseBody);
                errorMessage = json.optString("message", errorMessage);
                String errorDetails = json.optString("error", "");
                if (!errorDetails.isEmpty()) {
                    errorMessage += " - " + errorDetails;
                }
            } catch (JSONException e) {
                Log.e("LoginAuthService", "Failed to parse error JSON", e);
            }
        }

        // Append HTTP status code if available
        if (error.networkResponse != null) {
            errorMessage = new String(error.networkResponse.data, StandardCharsets.UTF_8);
        }

        // Pass the final error message to callback
        callback.onError(errorMessage);
    }


    public interface VerifyLoginCallback {
        void onResponse(LoginResponse response);
        void onError(String errorMessage);
    }
}
