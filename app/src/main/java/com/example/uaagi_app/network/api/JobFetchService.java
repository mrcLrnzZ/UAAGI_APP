package com.example.uaagi_app.network.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.uaagi_app.network.VolleySingleton;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.network.mapper.JobFetchMapper;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.utils.NetworkUtils;

import java.util.List;

public class JobFetchService {
    private static final String TAG = "JobFetchService";
    private static final String BASE_URL =

            """
                    https://uaagionehire.bscs3b.com/MobileAPI/api/index.php""";
    private final Context context;
    public JobFetchService(Context context) {
        this.context = context;
    }
    /* =========================================================
       Fetch jobs for a user
       GET /api/users/{userId}/jobs
       ========================================================= */
    public void fetchJobsForUser(JobFetchCallback callback) {
        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection. Please check your network.");
            return;
        }
        int userId = Helpers.getUserId(context);
        String url = BASE_URL + "/jobs?userId=" + userId;
        Log.d(TAG, "User ID: " + userId);
        Log.d(TAG, url);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> ApiResponseHandler.handleSuccess(
                        response,
                        JobFetchMapper::fromJson,
                        callback::onResponse,
                        callback::onError
                ),
                error -> ApiErrorHandler.handleError(error, callback::onError)
        );

        applyRetryPolicy(request);
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
    /* =========================================================
       Fetch single job
       GET /api/jobs/{jobId}
       ========================================================= */
    public void fetchJobById(int jobId, JobFetchCallback callback) {
        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection. Please check your network.");
            return;
        }
        String url = BASE_URL + "/jobs?jobId=" + jobId;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> ApiResponseHandler.handleSuccess(
                        response,
                        JobFetchMapper::fromJson,
                        callback::onResponse,
                        callback::onError
                ),
                error -> ApiErrorHandler.handleError(error, callback::onError)
        );

        applyRetryPolicy(request);
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
    /* =========================================================
       Retry policy
       ========================================================= */

    private void applyRetryPolicy(JsonObjectRequest request) {
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
    }

    /* =========================================================
       Callback
       ========================================================= */

    public interface JobFetchCallback extends ApiErrorHandler.ApiErrorCallback {
        void onResponse(List<JobFetchResponse> jobs);
    }
}
