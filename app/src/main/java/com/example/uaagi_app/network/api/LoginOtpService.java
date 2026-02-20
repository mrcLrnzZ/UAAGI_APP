package com.example.uaagi_app.network.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.uaagi_app.network.VolleySingleton;
import com.example.uaagi_app.network.mapper.SendOnlyMapper;
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
                response -> ApiResponseHandler.handleSendOnlySuccess(
                        response,
                        callback::onResponse,
                        callback::onError
                ),
                error -> ApiErrorHandler.handleError(error, callback::onError)
        );
        applyRetryPolicy(request);
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
    private void applyRetryPolicy(JsonObjectRequest request) {
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
    }

    public interface LoginCallback extends ApiErrorHandler.ApiErrorCallback {
        void onResponse(String message);
    }
}
