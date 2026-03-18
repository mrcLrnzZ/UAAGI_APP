package com.example.uaagi_app.network.api;

import com.example.uaagi_app.network.dto.ApiResponse;
import com.example.uaagi_app.network.dto.GoogleAuthRequest;
import com.example.uaagi_app.network.dto.LoginData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("index.php/auth/google")
    Call<ApiResponse<LoginData>> authGoogle(
            @Body GoogleAuthRequest request
    );
}
