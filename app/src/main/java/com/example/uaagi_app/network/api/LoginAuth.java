package com.example.uaagi_app.network.api;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.uaagi_app.network.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class LoginAuth {
    private final Context context;
    private static final String VERIFY_LOGIN_URL = "https://uaagionehire.bscs3b.com/MobileAPI/api/loginAuth.php";
    private static final int TIMEOUT_MS = 5000;
    public LoginAuth(Context context) {
        this.context = context;
    }
    public  void verifyLogin(String Otp, String email, verifyLoginCallback callback){
        StringRequest request = new StringRequest(Request.Method.POST, VERIFY_LOGIN_URL,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        boolean success = obj.getBoolean("success");
                        callback.onResponse(success, obj);
                    } catch (JSONException e) {
                        Log.e("verifyLoginRequest", "Invalid JSON response: " + response, e);
                        callback.onError("Invalid server response: " + e.getMessage());
                    }
                },
                error -> {
                    String message = error.getMessage() != null ? error.getMessage() : "Unknown network error";
                    callback.onError("Network error: " + message);
                }
                ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("OTP", Otp);
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
    public interface verifyLoginCallback{
        void onResponse(boolean success, JSONObject response);
        void onError(String errorMessage);
    }
}
