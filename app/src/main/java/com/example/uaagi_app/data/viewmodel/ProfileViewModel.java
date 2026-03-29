package com.example.uaagi_app.data.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.uaagi_app.network.RetrofitClient;
import com.example.uaagi_app.network.Services.PreEmpFetchService;
import com.example.uaagi_app.network.Services.ProfileUpdateService;
import com.example.uaagi_app.network.api.PreEmpApi;
import com.example.uaagi_app.network.dto.PreEmpDto.Education;
import com.example.uaagi_app.network.dto.PreEmpFetchResponse;
import com.example.uaagi_app.network.dto.UpdateProfileDTO;
import com.example.uaagi_app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<PreEmpFetchResponse> preEmpData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>();

    public LiveData<PreEmpFetchResponse> getPreEmpData() {
        return preEmpData;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getUpdateSuccess() {
        return updateSuccess;
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

        PreEmpApi api = RetrofitClient.getInstance().create(PreEmpApi.class);
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

    public void updateProfile(Context context, UpdateProfileDTO dto) {
        loading.setValue(true);
        int userId = SessionManager.getInstance(context).getUserId();

        PreEmpApi api = RetrofitClient.getInstance().create(PreEmpApi.class);
        ProfileUpdateService service = new ProfileUpdateService(api);

        service.updateProfile(userId, dto, new ProfileUpdateService.ProfileUpdateCallback() {
            @Override
            public void onSuccess() {
                loading.setValue(false);
                updateSuccess.setValue(true);
                fetchContent(context);
            }

            @Override
            public void onError(String message) {
                loading.setValue(false);
                updateSuccess.setValue(false);
                error.setValue(message);
            }
        });
    }

    public LiveData<List<Education>> getEducationList() {
        return Transformations.map(preEmpData, data -> {
            if (data != null) {
                return data.getEducation();
            }
            return new ArrayList<>();
        });
    }
}
