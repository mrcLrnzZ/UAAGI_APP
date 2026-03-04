package com.example.uaagi_app.data.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uaagi_app.network.Services.JobFetchService;
import com.example.uaagi_app.network.dto.JobFetchResponse;

import java.util.List;

public class JobViewModel extends ViewModel {

    private final MutableLiveData<JobFetchResponse> jobData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<JobFetchResponse> getJobData() {
        return jobData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getLoadingState() {
        return isLoading;
    }

    public void fetchJob(int jobId, Context context) {

        isLoading.setValue(true);

        JobFetchService service = new JobFetchService(context);

        service.fetchJobById(jobId, new JobFetchService.JobFetchCallback() {

            @Override
            public void onResponse(List<JobFetchResponse> response) {
                isLoading.setValue(false);

                if (response != null && !response.isEmpty()) {
                    jobData.setValue(response.get(0));
                    Log.d("JobViewModel", "Job fetched: " + response.get(0).getJobTitle());
                } else {
                    errorMessage.setValue("No job found for the given ID.");
                }
            }

            @Override
            public void onError(String message) {
                isLoading.setValue(false);
                errorMessage.setValue(message);
            }
        });
    }
}
