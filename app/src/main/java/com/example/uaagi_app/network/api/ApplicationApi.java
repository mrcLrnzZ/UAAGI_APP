package com.example.uaagi_app.network.api;

import com.example.uaagi_app.network.dto.ApiResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApplicationApi {

    @FormUrlEncoded
    @POST("index.php/application-send")
    Call<ApiResponse> submitApplication(
            @Field("user_id") int userId,
            @Field("job_id") int jobId,
            @Field("apply_method") String applyMethod
    );

    @Multipart
    @POST("index.php/application-send")
    Call<ApiResponse> submitResumeApplication(
            @Part("user_id") RequestBody userId,
            @Part("job_id") RequestBody jobId,
            @Part("apply_method") RequestBody applyMethod,
            @Part MultipartBody.Part file
    );
}
