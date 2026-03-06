package com.example.uaagi_app.data.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uaagi_app.network.Services.ApplicationService;
import com.example.uaagi_app.network.dto.Applicant;
import com.example.uaagi_app.network.dto.ApiResponse;

import java.util.ArrayList;
import java.util.List;

public class ApplicantViewModel extends ViewModel {

    private final MutableLiveData<List<Applicant>> applicants = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<List<Applicant>> getApplicants() {
        return applicants;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchApplicantsForUser(int userId, Context context) {
        isLoading.setValue(true);
        ApplicationService service = new ApplicationService(context);
        service.fetchApplicantsForUser(userId, new ApplicationService.FetchApplicantsCallback() {
            @Override
            public void onResponse(List<Applicant> response) {
                isLoading.setValue(false);
                applicants.setValue(response != null ? response : new ArrayList<>());
            }
            @Override
            public void onError(String message) {
                isLoading.setValue(false);
                errorMessage.setValue(message);
            }
        });
    }
}
