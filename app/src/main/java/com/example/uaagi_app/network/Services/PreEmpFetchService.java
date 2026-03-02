package com.example.uaagi_app.network.Services;

import android.util.Log;

import com.example.uaagi_app.network.api.PreEmpApi;
import com.example.uaagi_app.network.dto.ApiResponse;
import com.example.uaagi_app.network.dto.PreEmpFetchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreEmpFetchService {

    private final PreEmpApi api;
    private final String TAG = "PreEmpFetchService";

    public interface PreEmpFetchCallback {
        void onSuccess(PreEmpFetchResponse data);
        void onError(String message);
    }

    public PreEmpFetchService(PreEmpApi api) {
        this.api = api;
    }

    public void fetchPreEmpForm(int userId, PreEmpFetchCallback callback) {

        Log.d(TAG, "Starting fetchPreEmpForm for userId: " + userId);

        api.fetchPreEmpForUser(userId).enqueue(new Callback<ApiResponse<PreEmpFetchResponse>>() {

            @Override
            public void onResponse(Call<ApiResponse<PreEmpFetchResponse>> call,
                                   Response<ApiResponse<PreEmpFetchResponse>> response) {

                Log.d(TAG, "Response received. Code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {

                    Log.d(TAG, "Response body: " + response.body().toString());

                    ApiResponse<PreEmpFetchResponse> body = response.body();

                    if (body.isSuccess()) {

                        Log.d(TAG, "Request SUCCESS");
                        Log.d(TAG, "User First Name: " +
                                body.getData().getUserInfo().getFirstName());

                        callback.onSuccess(body.getData());

                    } else {

                        Log.e(TAG, "API returned error: " + body.getMessage());
                        callback.onError(body.getMessage());
                    }

                } else {

                    Log.e(TAG, "Server error. Code: " + response.code());
                    callback.onError("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<PreEmpFetchResponse>> call, Throwable t) {

                Log.e(TAG, "Network failure: " + t.getMessage());
                t.printStackTrace();

                callback.onError(t.getMessage());
            }
        });
    }
}