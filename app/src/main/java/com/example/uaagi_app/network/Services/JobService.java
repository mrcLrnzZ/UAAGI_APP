package com.example.uaagi_app.network.Services;

import android.content.Context;
import android.util.Log;

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

public class JobService {

    private final Context context;
    private final JobsApi jobsApi;

    public JobService(Context context) {
        this.context = context;
        this.jobsApi = RetrofitClient
                .getInstance()
                .create(JobsApi.class);
    }

    /* =========================================================
       Fetch jobs for user
       ========================================================= */

    public void fetchJobsForUser(JobServiceCallback callback) {

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

    public void fetchJobById(int jobId, JobServiceCallback callback) {

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

    public void fetchSavedJob(int userId, JobServiceCallback callback) {

        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection.");
            return;
        }

        Call<ApiResponse<List<JobFetchResponse>>> call =
                jobsApi.fetchSavedJobs(userId);

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
    public void fetchArchivedJob(int jobId, JobServiceCallback callback) {

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
       Archived job
       ========================================================= */
    public void archiveJob(int jobId, FeedbackCallback callback) {
        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.feedback("No internet connection.");
            return;
        }

        Call<ApiResponse> call = jobsApi.archiveJob(SessionManager.getInstance(context).getUserId(), jobId);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        callback.feedback("Job archived!");
                    } else {
                        RetrofitErrorHandler.handleError(response, null, callback::onError);
                    }
                } else {
                    RetrofitErrorHandler.handleError(response, null, callback::onError);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                RetrofitErrorHandler.handleError(null, t, callback::onError);
            }
        });
    }
    /* =========================================================
       Unarchived job
       ========================================================= */
    public void unarchiveJob(int jobId, FeedbackCallback callback) {
        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.feedback("No internet connection.");
            return;
        }

        Call<ApiResponse> call = jobsApi.unarchiveJob(SessionManager.getInstance(context).getUserId(), jobId);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        callback.feedback("Job unarchived!");
                    } else {
                        RetrofitErrorHandler.handleError(response, null, callback::onError);
                    }
                } else {
                    RetrofitErrorHandler.handleError(response, null, callback::onError);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                RetrofitErrorHandler.handleError(null, t, callback::onError);
            }
        });
    }
    /* =========================================================
       Save job
       ========================================================= */
    public void saveJob(int jobId, FeedbackCallback callback) {
        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.feedback("No internet connection.");
            return;
        }
        Log.d("saveJob", "User ID: " + SessionManager.getInstance(context).getUserId() + ", Job ID: " + jobId);
        Call<ApiResponse> call = jobsApi.saveJob(SessionManager.getInstance(context).getUserId(), jobId);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        callback.feedback("Job saved!");
                    } else {
                        RetrofitErrorHandler.handleError(response, null, callback::onError);
                    }
                } else {
                    RetrofitErrorHandler.handleError(response, null, callback::onError);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                RetrofitErrorHandler.handleError(null, t, callback::onError);
            }
        });
    }

    /* =========================================================
       Unsave job
       ========================================================= */
    public void unsaveJob(int jobId, FeedbackCallback callback) {
        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.feedback("No internet connection.");
            return;
        }
        Call<ApiResponse> call = jobsApi.unsaveJob(SessionManager.getInstance(context).getUserId(), jobId);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        callback.feedback("Job unbookmarked!");
                    } else {
                        RetrofitErrorHandler.handleError(response, null, callback::onError);
                    }
                } else {
                    RetrofitErrorHandler.handleError(response, null, callback::onError);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                RetrofitErrorHandler.handleError(null, t, callback::onError);
            }
        });
    }
    /* =========================================================
       Get save job id
       ========================================================= */
    public void fetchSavedJobId(int userId, JobIdServiceCallback callback) {
        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection.");
            return;
        }
        Call<ApiResponse<List<Integer>>> call = jobsApi.fetchSavedJobsId(userId);

        call.enqueue(new Callback<ApiResponse<List<Integer>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Integer>>> call, Response<ApiResponse<List<Integer>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Integer>> apiResponse = response.body();
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
            public void onFailure(Call<ApiResponse<List<Integer>>> call, Throwable t) {
                RetrofitErrorHandler.handleError(null, t, callback::onError);
            }
        });
    }
    /* =========================================================
       Get archived job id
       ========================================================= */
    public void fetchArchivedJobId(int userId, JobIdServiceCallback callback) {
        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection.");
            return;
        }
        Call<ApiResponse<List<Integer>>> call = jobsApi.fetchArchivedJobsId(userId);
        call.enqueue(new Callback<ApiResponse<List<Integer>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Integer>>> call, Response<ApiResponse<List<Integer>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Integer>> apiResponse = response.body();
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
            public void onFailure(Call<ApiResponse<List<Integer>>> call, Throwable t) {
                RetrofitErrorHandler.handleError(null, t, callback::onError);
            }
        });
    }
    /* =========================================================
       Callback
       ========================================================= */
    public interface JobIdServiceCallback extends RetrofitErrorHandler.ApiErrorCallback{
        void onResponse(List<Integer> jobIds);
    }
    public interface JobServiceCallback extends RetrofitErrorHandler.ApiErrorCallback{
        void onResponse(List<JobFetchResponse> jobs);
    }
    public interface FeedbackCallback extends RetrofitErrorHandler.ApiErrorCallback{
        void feedback(String message);
    }
}