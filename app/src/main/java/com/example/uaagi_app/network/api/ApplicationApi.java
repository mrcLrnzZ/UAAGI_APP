package com.example.uaagi_app.network.api;

import com.example.uaagi_app.network.dto.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApplicationApi {

    @FormUrlEncoded
    @POST("index.php/application-send")
    Call<ApiResponse> submitApplication(
            @Field("user_id") int userId,
            @Field("job_id") int jobId,
            @Field("apply_method") String applyMethod
    );
}
