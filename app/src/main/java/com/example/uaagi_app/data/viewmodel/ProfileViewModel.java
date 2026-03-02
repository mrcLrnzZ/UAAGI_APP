package com.example.uaagi_app.data.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uaagi_app.network.RetrofitClient;
import com.example.uaagi_app.network.Services.PreEmpFetchService;
import com.example.uaagi_app.network.api.PreEmpApi;
import com.example.uaagi_app.network.dto.PreEmpFetchResponse;
import com.example.uaagi_app.utils.SessionManager;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<PreEmpFetchResponse> preEmpData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<PreEmpFetchResponse> getPreEmpData() {
        return preEmpData;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void setPreEmpData(PreEmpFetchResponse data) {
        preEmpData.setValue(data);
    }

    public void setLoading(boolean isLoading) {
        loading.setValue(isLoading);
    }

    public void setError(String message) {
        error.setValue(message);
    }
    public void fetchContent(Context context) {

        loading.setValue(true);

        int userId = SessionManager.getInstance(context).getUserId();

        PreEmpApi api = RetrofitClient
                .getInstance()
                .create(PreEmpApi.class);

        PreEmpFetchService service = new PreEmpFetchService(api);

        service.fetchPreEmpForm(userId, new PreEmpFetchService.PreEmpFetchCallback() {

            @Override
            public void onSuccess(PreEmpFetchResponse data) {
                loading.setValue(false);
                preEmpData.setValue(data);
            }

            @Override
            public void onError(String message) {
                loading.setValue(false);
                error.setValue(message);
            }
        });
    }
}