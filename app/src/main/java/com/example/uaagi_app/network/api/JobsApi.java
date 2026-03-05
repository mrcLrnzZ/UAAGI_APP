package com.example.uaagi_app.network.api;

import com.example.uaagi_app.network.dto.ApiResponse;
import com.example.uaagi_app.network.dto.JobFetchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JobsApi {

    @GET("index.php/jobs")
    Call<ApiResponse<List<JobFetchResponse>>>  fetchJobsForUser(
            @Query("userId") int userId
    );

    @GET("index.php/jobs")
    Call<ApiResponse<JobFetchResponse>>  fetchJobById(
            @Query("jobId") int jobId
    );

    @POST("index.php/user/fetch-saved-jobs")
    Call<ApiResponse<List<JobFetchResponse>>>  fetchSavedJobs(
            @Query("userId") int userId
    );

    @POST("index.php/user/fetch-archived-jobs")
    Call<ApiResponse<List<JobFetchResponse>>>  fetchArchivedJobs(
            @Query("userId") int userId
    );
}