package com.example.uaagi_app.network.Services;

import android.util.Log;

import com.example.uaagi_app.network.api.PreEmpApi;
import com.example.uaagi_app.network.dto.ApiResponse;
import com.example.uaagi_app.network.dto.UpdateProfileDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUpdateService {

    private final PreEmpApi api;
    private final String TAG = "ProfileUpdateService";

    public interface ProfileUpdateCallback {
        void onSuccess();
        void onError(String message);
    }

    public ProfileUpdateService(PreEmpApi api) {
        this.api = api;
    }

    public void updateProfile(int userId, UpdateProfileDTO dto, ProfileUpdateCallback callback) {
        Log.d(TAG, "Updating profile for userId: " + userId);

        api.updateProfile(userId, dto).enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess();
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.e(TAG, "Network failure: " + t.getMessage());
                callback.onError(t.getMessage());
            }
        });
    }
}
