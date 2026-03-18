package com.example.uaagi_app.network.dto;

public class GoogleAuthRequest {

    private String id_token;

    public GoogleAuthRequest(String id_token) {
        this.id_token = id_token;
    }

    public String getId_token() {
        return id_token;
    }
}