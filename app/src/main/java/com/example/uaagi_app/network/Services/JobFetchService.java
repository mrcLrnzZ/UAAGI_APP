package com.example.uaagi_app.network.Services;

import android.content.Context;

import com.example.uaagi_app.network.RetrofitClient;
import com.example.uaagi_app.network.api.JobsApi;
import com.example.uaagi_app.network.dto.ApiResponse;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.utils.NetworkUtils;
import com.example.uaagi_app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobFetchService {

    private final Context context;
    private final JobsApi jobsApi;

    public JobFetchService(Context context) {
        this.context = context;
        this.jobsApi = RetrofitClient
                .getInstance()
                .create(JobsApi.class);
    }

    /* =========================================================
       Fetch jobs for user
       ========================================================= */

    public void fetchJobsForUser(JobFetchCallback callback) {

        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection.");
            return;
        }

        int userId = SessionManager.getInstance(context).getUserId();

        Call<ApiResponse<List<JobFetchResponse>>> call =
                jobsApi.fetchJobsForUser(userId);

        call.enqueue(new Callback<ApiResponse<List<JobFetchResponse>>>() {
            @Override
            public void onResponse(
                    Call<ApiResponse<List<JobFetchResponse>>> call,
                    Response<ApiResponse<List<JobFetchResponse>>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    ApiResponse<List<JobFetchResponse>> apiResponse = response.body();

                    if (apiResponse.isSuccess()) {
                        callback.onResponse(apiResponse.getData());
                    } else {
                        RetrofitErrorHandler.handleError(response, null, callback::onError);
                    }

                } else {
                    RetrofitErrorHandler.handleError(response, null, callback::onError);
                }
            }

            @Override
            public void onFailure(
                    Call<ApiResponse<List<JobFetchResponse>>> call,
                    Throwable t) {

                RetrofitErrorHandler.handleError(null, t, callback::onError);
            }
        });
    }

    /* =========================================================
       Fetch single job
       ========================================================= */

    public void fetchJobById(int jobId, JobFetchCallback callback) {

        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection.");
            return;
        }

        Call<ApiResponse<JobFetchResponse>> call =
                jobsApi.fetchJobById(jobId);

        call.enqueue(new Callback<ApiResponse<JobFetchResponse>>() {
            @Override
            public void onResponse(
                    Call<ApiResponse<JobFetchResponse>> call,
                    Response<ApiResponse<JobFetchResponse>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<JobFetchResponse> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        List<JobFetchResponse> jobs = new ArrayList<>();
                        jobs.add(apiResponse.getData());
                        callback.onResponse(jobs);
                    } else {
                        RetrofitErrorHandler.handleError(response, null, callback::onError);
                    }
                } else {
                    RetrofitErrorHandler.handleError(response, null, callback::onError);
                }
            }

            @Override
            public void onFailure(
                    Call<ApiResponse<JobFetchResponse>> call,
                    Throwable t) {

                callback.onError(t.getMessage());
            }
        });
    }

    /* =========================================================
       Fetch saved job
       ========================================================= */

    public void fetchSavedJob(int jobId, JobFetchCallback callback) {

        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection.");
            return;
        }

        Call<ApiResponse<List<JobFetchResponse>>> call =
                jobsApi.fetchSavedJobs(jobId);

        call.enqueue(new Callback<ApiResponse<List<JobFetchResponse>>>() {
            @Override
            public void onResponse(
                    Call<ApiResponse<List<JobFetchResponse>>> call,
                    Response<ApiResponse<List<JobFetchResponse>>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<JobFetchResponse>> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        callback.onResponse(apiResponse.getData());
                    } else {
                        RetrofitErrorHandler.handleError(response, null, callback::onError);
                    }
                } else {
                    RetrofitErrorHandler.handleError(response, null, callback::onError);
                }
            }

            @Override
            public void onFailure(
                    Call<ApiResponse<List<JobFetchResponse>>> call,
                    Throwable t) {

                RetrofitErrorHandler.handleError(null, t, callback::onError);
            }
        });
    }
    /* =========================================================
       Fetch archived job
       ========================================================= */

    public void fetchArchivedJob(int jobId, JobFetchCallback callback) {

        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection.");
            return;
        }

        Call<ApiResponse<List<JobFetchResponse>>> call =
                jobsApi.fetchArchivedJobs(jobId);

        call.enqueue(new Callback<ApiResponse<List<JobFetchResponse>>>() {
            @Override
            public void onResponse(
                    Call<ApiResponse<List<JobFetchResponse>>> call,
                    Response<ApiResponse<List<JobFetchResponse>>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<JobFetchResponse>> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        callback.onResponse(apiResponse.getData());
                    } else {
                        RetrofitErrorHandler.handleError(response, null, callback::onError);
                    }
                } else {
                    RetrofitErrorHandler.handleError(response, null, callback::onError);
                }
            }

            @Override
            public void onFailure(
                    Call<ApiResponse<List<JobFetchResponse>>> call,
                    Throwable t) {

                RetrofitErrorHandler.handleError(null, t, callback::onError);
            }
        });
    }

    /* =========================================================
       Callback
       ========================================================= */

    public interface JobFetchCallback extends RetrofitErrorHandler.ApiErrorCallback{
        void onResponse(List<JobFetchResponse> jobs);
    }
}