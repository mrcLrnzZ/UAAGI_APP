package com.example.uaagi_app.network.Services;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.uaagi_app.network.VolleySingleton;
import com.example.uaagi_app.network.dto.LoginFetchResponse;
import com.example.uaagi_app.network.mapper.LoginFetchResponseMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginAuthService {
    private static final String TAG = "LoginAuthService";
    private static final String BASE_URL = "https://uaagionehire.bscs3b.com/MobileAPI/api/index.php";
    private static final String VERIFY_LOGIN_URL = BASE_URL + "/auth/verify";
    private static final int TIMEOUT_MS = 10000;

    private final Context context;
    private final Gson gson;

    public LoginAuthService(Context context) {
        this.context = context;
        this.gson = new Gson();
    }

    public void verifyLogin(String email, String otp, VerifyLoginCallback callback) {
        try {
            Map<String, String> requestData = new HashMap<>();
            requestData.put("email", email);
            requestData.put("otp", otp.toLowerCase());

            String jsonString = gson.toJson(requestData);
            JSONObject body = new JSONObject(jsonString);

            Log.d(TAG, "Request URL: " + VERIFY_LOGIN_URL);
            Log.d(TAG, "Request body: " + body);

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

        } catch (JSONException e) {
            callback.onError("Failed to create request body: " + e.getMessage());
            Log.e(TAG, "JSON conversion error", e);
        }
    }



    public interface VerifyLoginCallback extends ApiErrorHandler.ApiErrorCallback {
        void onResponse(LoginFetchResponse response);
    }
}
