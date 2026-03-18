package com.example.uaagi_app.network.Services;

import android.content.Context;

import com.google.gson.Gson;

public class LoginAuth {
    private static final String BASE_URL = "https://uaagionehire.bscs3b.com/MobileAPI/api/index.php";
    private static final String VERIFY_LOGIN_URL = BASE_URL + "/auth/google";
    private final Context context;
    private final Gson gson;

    public LoginAuth(Context context) {
        this.context = context;
        this.gson = new Gson();
    }
}
