package com.example.uaagi_app.network.api;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.uaagi_app.network.VolleySingleton;
import com.example.uaagi_app.network.dto.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
public class LoginAuthService {
    private final Context context;
    private static final String VERIFY_LOGIN_URL = "https://uaagionehire.bscs3b.com/MobileAPI/api/loginAuth.php";
    private static final int TIMEOUT_MS = 5000;
    public LoginAuthService(Context context) {
        this.context = context;
    }
    public  void verifyLogin(String Otp, String email, verifyLoginCallback callback){
        StringRequest request = new StringRequest(Request.Method.POST, VERIFY_LOGIN_URL,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        LoginResponse loginResponse = new LoginResponse();
                        loginResponse.success = obj.getBoolean("success");
                        loginResponse.message = obj.optString("message");
                        loginResponse.formExist = obj.getBoolean("formExist");
                        loginResponse.userId = obj.getInt("userId");


                        callback.onResponse(loginResponse);

                    } catch (JSONException e) {
                        Log.e("verifyLoginRequest", "Invalid JSON response: " + response, e);
                        callback.onError("Invalid server response: " + e.getMessage());
                    }
                },
                error -> {
                    Log.e("verifyLoginRequest", "VolleyError", error);

                    if (error.networkResponse != null) {
                        Log.e("verifyLoginRequest", "Status code: " + error.networkResponse.statusCode);

                        if (error.networkResponse.data != null) {
                            String body = new String(
                                    error.networkResponse.data,
                                    StandardCharsets.UTF_8
                            );
                            Log.e("verifyLoginRequest", "Server response: " + body);
                        }
                    } else {
                        Log.e("verifyLoginRequest", "No network response (timeout / SSL / DNS issue)");
                    }

                    callback.onError("Network error");
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
        void onResponse(LoginResponse response);
        void onError(String errorMessage);
    }
}
