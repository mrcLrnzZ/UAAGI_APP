package com.example.uaagi_app.network.api;

import com.example.uaagi_app.network.dto.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Query;

public interface NotificationApi {

    @GET("index.php/notification")
    Call<ApiResponse> getUserNotifications(
            @Query("user_id") int userId
    );
    @PATCH("index.php/notification")
    Call<ApiResponse> updateUserNotification(
            @Query("user_id") int userId
    );
}