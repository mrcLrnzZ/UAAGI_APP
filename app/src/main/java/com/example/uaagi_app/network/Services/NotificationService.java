package com.example.uaagi_app.network.Services;

import android.content.Context;

import com.example.uaagi_app.network.RetrofitClient;
import com.example.uaagi_app.network.api.NotificationApi;
import com.example.uaagi_app.network.dto.ApiResponse;
import com.example.uaagi_app.utils.NetworkUtils;

import retrofit2.Call;

public class NotificationService {

    private final Context context;
    private final NotificationApi notificationApi;

    public NotificationService(Context context) {
        this.context = context;

        this.notificationApi = RetrofitClient
                .getInstance()
                .create(NotificationApi.class);
    }

    public void fetchUserNotifications(int userId, NotificationCallback callback) {

        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection.");
            return;
        }

        Call<ApiResponse> call = notificationApi.getUserNotifications(userId);

        executeVoidCall(call, callback);
    }

    public void updateUserNotification(int userId, NotificationCallback callback) {
        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection.");
            return;
        }

        Call<ApiResponse> call = notificationApi.updateUserNotification(userId);

        executeVoidCall(call, callback);
    }

    private void executeVoidCall(Call<ApiResponse> call, NotificationCallback callback) {

        call.enqueue(new retrofit2.Callback<ApiResponse>() {

            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    ApiResponse apiResponse = response.body();

                    if (apiResponse.isSuccess()) {
                        callback.onResponse();
                    } else {
                        callback.onError(apiResponse.getMessage());
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

    public interface NotificationCallback extends RetrofitErrorHandler.ApiErrorCallback {
        void onResponse();
    }
}