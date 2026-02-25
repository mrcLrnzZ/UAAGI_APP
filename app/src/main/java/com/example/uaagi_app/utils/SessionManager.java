package com.example.uaagi_app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {

    private static SessionManager instance;
    private final SharedPreferences prefs;
    private static final String TAG = "SESSION_MANAGER";

    private SessionManager(Context context) {
        prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveLoginState(boolean state) {
        prefs.edit().putBoolean("isLoggedIn", state).apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean("isLoggedIn", false);
    }

    public void saveUserId(int userId) {
        prefs.edit().putInt("userId", userId).apply();
    }

    public int getUserId() {
        return prefs.getInt("userId", -1);
    }

    public void saveUserEmail(String email) {
        Log.d(TAG, "Saving Email: " + email);
        prefs.edit().putString("userEmail", email).apply();
    }

    public String getUserEmail() {
        String email = prefs.getString("userEmail", "");
        Log.d(TAG, "Retrieved Email: " + email);
        return email;
    }

    public void savePreEmpResponse(boolean response) {
        prefs.edit().putBoolean("preEmpResponse", response).apply();
    }

    public boolean getPreEmpResponse() {
        return prefs.getBoolean("preEmpResponse", false);
    }

    public void logout() {
        prefs.edit().clear().apply();
    }
}