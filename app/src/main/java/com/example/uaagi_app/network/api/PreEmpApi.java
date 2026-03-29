package com.example.uaagi_app.network.api;

import com.example.uaagi_app.network.dto.ApiResponse;
import com.example.uaagi_app.network.dto.PreEmpFetchResponse;
import com.example.uaagi_app.network.dto.UpdateProfileDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PreEmpApi {
    @GET("index.php/pre-employment-forms")
    Call<ApiResponse<PreEmpFetchResponse>> fetchPreEmpForUser(
            @Query("userId") int userId
    );
    @POST("index.php/user/update-profile")
    Call<ApiResponse<Void>> updateProfile(
            @Query("user_id") int userId,
            @Body UpdateProfileDTO updateProfileDTO
    );
}
