package com.example.uaagi_app.network.api;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.uaagi_app.network.VolleySingleton;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.network.mapper.JobFetchMapper;
import com.example.uaagi_app.utils.Helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobFetchService {

    private static final String TAG = "JobFetchService";
    private static final String FETCH_JOB_SERVICE_URL = "https://uaagionehire.bscs3b.com/MobileAPI/api/FetchJobsService.php";

    private final Context context;

    public JobFetchService(Context context) {
        this.context = context;
    }
    public void fetchJobs(JobFetchCallback callback) {

        StringRequest request = new StringRequest(
                Request.Method.POST,
                FETCH_JOB_SERVICE_URL,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        boolean success = json.optBoolean("success", false);

                        if (success) {
                            JSONArray jobsArray = json.optJSONArray("data");
                            if (jobsArray != null) {
                                List<JobFetchResponse> jobs = new ArrayList<>();
                                for (int i = 0; i < jobsArray.length(); i++) {
                                    JSONObject jobJson = jobsArray.optJSONObject(i);
                                    if (jobJson != null) jobs.add(JobFetchMapper.fromJson(jobJson));
                                }
                                callback.onResponse(jobs);
                            } else {
                                callback.onError("No jobs available");
                            }
                        } else {
                            callback.onError(json.optString("message", "Unknown error"));
                        }


                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Error", e);
                        callback.onError("Parsing error");
                    }
                },
                error -> {
                    Log.e(TAG, "Volley Error", error);
                    callback.onError("Network error");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(Helpers.getUserId(context)));
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface JobFetchCallback {
        void onResponse(List<JobFetchResponse> jobs);
        void onError(String errorMessage);
    }

}

