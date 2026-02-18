package com.example.uaagi_app.network.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.uaagi_app.network.VolleySingleton;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.network.mapper.JobFetchMapper;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
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
                response -> handleSuccess(response, callback),
                error -> handleError(error, callback)
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
                response -> handleSingleJob(response, callback),
                error -> handleError(error, callback)
        );

        applyRetryPolicy(request);
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    /* =========================================================
       Success handlers
       ========================================================= */
    private void handleSuccess(JSONObject response, JobFetchCallback callback) {
        if (!response.optBoolean("success")) {
            callback.onError(response.optString("message", "Unknown error"));
            return;
        }

        JSONArray jobsArray = response.optJSONArray("data");
        Log.d(TAG, "Jobs Array: " + jobsArray);
        if (jobsArray == null || jobsArray.length() == 0) {
            callback.onResponse(Collections.emptyList());
            return;
        }

        List<JobFetchResponse> jobs = new ArrayList<>();
        for (int i = 0; i < jobsArray.length(); i++) {
            JSONObject jobJson = jobsArray.optJSONObject(i);
            if (jobJson != null) {
                jobs.add(JobFetchMapper.fromJson(jobJson));
            }
        }

        callback.onResponse(jobs);

    }

    private void handleSingleJob(JSONObject response, JobFetchCallback callback) {
        if (!response.optBoolean("success")) {
            callback.onError(response.optString("message", "Unknown error"));
            return;
        }

        JSONObject jobJson = response.optJSONObject("data");
        Log.d(TAG, "Job JSON: " + jobJson);
        if (jobJson == null) {
            callback.onResponse(Collections.emptyList());
            return;
        }

        List<JobFetchResponse> result = new ArrayList<>();
        result.add(JobFetchMapper.fromJson(jobJson));
        callback.onResponse(result);

    }

    /* =========================================================
       Error handler
       ========================================================= */

    private void handleError(VolleyError error, JobFetchCallback callback) {
        Log.e(TAG, "Volley Error", error);
        if (error.networkResponse == null) {
            callback.onError("No internet connection. Please try again.");
            return;
        }

        int statusCode = error.networkResponse.statusCode;
        Log.e(TAG, "HTTP Status Code: " + statusCode);

        if (statusCode >= 500) {
            callback.onError("Server is unavailable. Please try again later.");
        }
        else if (statusCode == 401) {
            callback.onError("Session expired. Please log in again.");
        }
        else if (statusCode == 404) {
            callback.onError("Requested resource not found.");
        }
        else {
            callback.onError("Unexpected error occurred.");
        }
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

    public interface JobFetchCallback {
        void onResponse(List<JobFetchResponse> jobs);
        void onError(String errorMessage);
    }
}
