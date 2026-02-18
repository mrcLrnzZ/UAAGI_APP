package com.example.uaagi_app.network.mapper;

import com.example.uaagi_app.network.dto.LoginResponse;

import org.json.JSONObject;

public class LoginResponseMapper {
    public static LoginResponse fromJson(JSONObject loginResponse) {
        return new LoginResponse.Builder()
                .success(loginResponse.optBoolean("success"))
                .message(loginResponse.optString("message"))
                .formExist(loginResponse.optBoolean("formExist"))
                .userId(loginResponse.optInt("userId"))
                .build();
    }
}
