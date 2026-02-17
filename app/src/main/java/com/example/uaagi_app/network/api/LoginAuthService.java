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
        callback.onError("Network error");
    }

    public interface VerifyLoginCallback {
        void onResponse(LoginResponse response);
        void onError(String errorMessage);
    }
}
