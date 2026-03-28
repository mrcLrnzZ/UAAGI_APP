package com.example.uaagi_app.network.api;

import com.example.uaagi_app.network.dto.ApiResponse;
import com.example.uaagi_app.network.dto.Applicant;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApplicationApi {

    @FormUrlEncoded
    @POST("index.php/application-send")
    Call<ApiResponse> submitApplication(
            @Field("user_id") int userId,
            @Field("job_id") int jobId,
            @Field("apply_method") String applyMethod
    );
    @FormUrlEncoded
    @POST("index.php/application-send")
    Call<ApiResponse> submitReuseResume(
            @Field("user_id") int userId,
            @Field("job_id") int jobId,
            @Field("re_use_resume") int reUseResume,
            @Field("apply_method") String applyMethod
    );
    @PATCH("index.php/application-reject")
    Call<ApiResponse> rejectApplication(
            @Query("application_id") int applicationId
    );

    @Multipart
    @POST("index.php/application-send")
    Call<ApiResponse> submitResumeApplication(
            @Part("user_id") RequestBody userId,
            @Part("job_id") RequestBody jobId,
            @Part("apply_method") RequestBody applyMethod,
            @Part MultipartBody.Part file
    );

    @GET("index.php/applications")
    Call<ApiResponse<List<Applicant>>> fetchApplicantsForUser(
            @Query("user_id") int userId
    );

    @FormUrlEncoded
    @POST("index.php/application-withdraw")
    Call<ApiResponse> withdrawApplication(
            @Field("application_id") int applicationId
    );

    @FormUrlEncoded
    @POST("index.php/application-reschedule")
    Call<ApiResponse> rescheduleInterview(
            @Field("application_id") int applicationId,
            @Field("preferred_date_time") String preferredDateTime,
            @Field("previous_time") String previousTime,
            @Field("reason") String reason
    );
}
