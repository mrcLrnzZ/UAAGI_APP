package com.example.uaagi_app.network.mapper;

import com.example.uaagi_app.network.dto.SendOnlyResponse;

import org.json.JSONObject;

public class SendOnlyMapper {
    public static SendOnlyResponse fromJson(JSONObject sendOnlyResponse) {
        return new SendOnlyResponse.Builder()
                .success(sendOnlyResponse.optBoolean("success"))
                .message(sendOnlyResponse.optString("message"))
                .build();
    }
}
