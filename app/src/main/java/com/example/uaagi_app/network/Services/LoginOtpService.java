package com.example.uaagi_app.network.Services;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.uaagi_app.network.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginOtpService {
    private static final String BASE_URL = "https://uaagionehire.bscs3b.com/MobileAPI/api/index.php";
    private static final String TAG = "LoginOtpService";
    private static final int TIMEOUT_MS = 10000;

    private final Context context;
    private final Gson gson;

    public LoginOtpService(Context context) {
        this.context = context;
        this.gson = new Gson();
    }

    public void requestOtp(String email, LoginCallback callback) {
        String url = BASE_URL + "/auth/send-otp";

        try {
            Map<String, String> requestData = new HashMap<>();
            requestData.put("email", email);

            String jsonString = gson.toJson(requestData);
            JSONObject body = new JSONObject(jsonString);

            Log.d(TAG, "Request URL: " + url);
            Log.d(TAG, "Request body: " + body);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    body,
                    response -> ApiResponseHandler.handleSendOnlySuccess(
                            response,
                            callback::onResponse,
                            callback::onError
                    ),
                    error -> ApiErrorHandler.handleError(error, callback::onError)
            );

            applyRetryPolicy(request);
            VolleySingleton.getInstance(context).addToRequestQueue(request);

        } catch (JSONException e) {
            callback.onError("Failed to create request body: " + e.getMessage());
            Log.e(TAG, "JSON conversion error", e);
        }
    }
    private void applyRetryPolicy(JsonObjectRequest request) {
        request.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
    }

    public interface LoginCallback extends ApiErrorHandler.ApiErrorCallback {
        void onResponse(String message);
    }
}
