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
    @POST("index.php/user/save-job")
    Call<ApiResponse> saveJob(
            @Query("user_id") int userId,
            @Query("job_id") int jobId
    );
    @POST("index.php/user/unsave-job")
    Call<ApiResponse> unsaveJob(
            @Query("user_id") int userId,
            @Query("job_id") int jobId
    );
    @POST("index.php/user/archive-job")
    Call<ApiResponse> archiveJob(
            @Query("user_id") int userId,
            @Query("job_id") int jobId
    );
    @POST("index.php/user/unarchive-job")
    Call<ApiResponse> unarchiveJob(
            @Query("user_id") int userId,
            @Query("job_id") int jobId
    );
    @POST("index.php/user/fetch-saved-jobs")
    Call<ApiResponse<List<JobFetchResponse>>>  fetchSavedJobs(
            @Query("user_id") int userId
    );
    @POST("index.php/user/fetch-archived-jobs")
    Call<ApiResponse<List<JobFetchResponse>>>  fetchArchivedJobs(
            @Query("user_id") int userId
    );
    @GET("index.php/user/archive-job-id")
    Call<ApiResponse<List<Integer>>> fetchArchivedJobsId(
            @Query("user_id") int userId
    );
    @GET("index.php/user/save-job-id")
    Call<ApiResponse<List<Integer>>> fetchSavedJobsId(
            @Query("user_id") int userId
    );
}