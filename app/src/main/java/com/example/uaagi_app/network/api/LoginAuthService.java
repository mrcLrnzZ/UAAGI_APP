package com.example.uaagi_app.network.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.uaagi_app.network.VolleySingleton;
import com.example.uaagi_app.network.dto.LoginFetchResponse;
import com.example.uaagi_app.network.mapper.LoginFetchResponseMapper;
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
                response -> ApiResponseHandler.handleSingleSuccess(
                        response,
                        LoginFetchResponseMapper::fromJson,
                        callback::onResponse,
                        callback::onError
                ),
                error -> ApiErrorHandler.handleError(error, callback::onError)
        );

        // Retry policy
        request.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }



    public interface VerifyLoginCallback extends ApiErrorHandler.ApiErrorCallback {
        void onResponse(LoginFetchResponse response);
    }
}
