package com.example.uaagi_app.network.api;

import com.example.uaagi_app.network.dto.ApiResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DocumentApi {

    @Multipart
    @POST("index.php/document/upload")
    Call<ApiResponse> uploadDocument(
            @Part MultipartBody.Part file,
            @Part("user_id") RequestBody userId,
            @Part("job_id") RequestBody jobId
    );
}