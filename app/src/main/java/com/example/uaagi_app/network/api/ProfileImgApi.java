package com.example.uaagi_app.network.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProfileImgApi {
    @Multipart
    @POST("index.php/user/upload-profile")
    Call<ResponseBody> uploadProfileImage(
            @Part MultipartBody.Part image,
            @Part("user_id") RequestBody userId
    );
}
