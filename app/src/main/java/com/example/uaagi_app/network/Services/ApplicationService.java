package com.example.uaagi_app.network.Services;

import android.content.Context;

import com.example.uaagi_app.network.RetrofitClient;
import com.example.uaagi_app.network.api.ApplicationApi;
import com.example.uaagi_app.network.api.DocumentApi;
import com.example.uaagi_app.network.dto.ApiResponse;
import com.example.uaagi_app.network.dto.Applicant;
import com.example.uaagi_app.utils.NetworkUtils;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationService {
    private final Context context;
    private final ApplicationApi applicationApi;
    private final DocumentApi documentApi;

    public ApplicationService(Context context) {
        this.context = context;
        this.applicationApi = RetrofitClient
                .getInstance()
                .create(ApplicationApi.class);
        this.documentApi = RetrofitClient
                .getInstance()
                .create(DocumentApi.class);
    }
    public void submitApplication(int userId, int jobId, String applyMethod, SubmitApplicationCallback callback) {

        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection.");
            return;
        }

        Call<ApiResponse> call = applicationApi.submitApplication(userId, jobId, applyMethod);
        executeVoidCall(call, callback);
    }
    public void submitResumeApplication(int userId, int jobId, String applyMethod,
                                        MultipartBody.Part file, SubmitApplicationCallback callback) {

        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection.");
            return;
        }

        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
        RequestBody jobIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(jobId));
        RequestBody applyMethodBody = RequestBody.create(MediaType.parse("text/plain"), applyMethod);

        Call<ApiResponse> call = applicationApi.submitResumeApplication(
                userIdBody,
                jobIdBody,
                applyMethodBody,
                file
        );

        executeVoidCall(call, callback);
    }
    private void executeVoidCall(Call<ApiResponse> call, SubmitApplicationCallback callback) {
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
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
    public void fetchApplicantsForUser(int userId, FetchApplicantsCallback callback) {

        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection.");
            return;
        }

        Call<ApiResponse<List<Applicant>>> call =
                applicationApi.fetchApplicantsForUser(userId);

        call.enqueue(new Callback<ApiResponse<List<Applicant>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Applicant>>> call,
                                   Response<ApiResponse<List<Applicant>>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    ApiResponse<List<Applicant>> apiResponse = response.body();

                    if (apiResponse.isSuccess()) {
                        callback.onResponse(apiResponse.getData());
                    } else {
                        callback.onError(apiResponse.getMessage());
                    }

                } else {
                    RetrofitErrorHandler.handleError(response, null, callback::onError);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Applicant>>> call, Throwable t) {
                RetrofitErrorHandler.handleError(null, t, callback::onError);
            }
        });
    }
    private RequestBody text(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }
    public interface FetchApplicantsCallback extends RetrofitErrorHandler.ApiErrorCallback{
        void onResponse(List<Applicant> applicants);
    }
    public interface SubmitApplicationCallback extends RetrofitErrorHandler.ApiErrorCallback{
        void onResponse();
    }
}
