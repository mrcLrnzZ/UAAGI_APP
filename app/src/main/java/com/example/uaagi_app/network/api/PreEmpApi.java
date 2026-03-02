package com.example.uaagi_app.network.api;

import com.example.uaagi_app.network.dto.ApiResponse;
import com.example.uaagi_app.network.dto.PreEmpFetchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PreEmpApi {
    @GET("index.php/pre-employment-forms")
    Call<ApiResponse<PreEmpFetchResponse>> fetchPreEmpForUser(
            @Query("userId") int userId
    );
}
