package com.example.uaagi_app.network.mapper;

import com.example.uaagi_app.network.dto.LoginFetchResponse;

import org.json.JSONObject;

public class LoginFetchResponseMapper {
    public static LoginFetchResponse fromJson(JSONObject loginResponse) {
        return new LoginFetchResponse.Builder()
                .success(loginResponse.optBoolean("success"))
                .message(loginResponse.optString("message"))
                .formExist(loginResponse.optBoolean("formExist"))
                .userId(loginResponse.optInt("userId"))
                .build();
    }
}
