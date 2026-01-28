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
    public LoginAuth(Context context) {
        this.context = context;
    }
}
