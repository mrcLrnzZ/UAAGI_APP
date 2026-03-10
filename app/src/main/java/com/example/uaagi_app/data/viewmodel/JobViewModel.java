package com.example.uaagi_app.data.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uaagi_app.network.Services.JobService;
import com.example.uaagi_app.network.dto.JobFetchResponse;

import java.util.ArrayList;
import java.util.List;

public class JobViewModel extends ViewModel {

    private final MutableLiveData<JobFetchResponse> jobData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<List<JobFetchResponse>> Jobs = new MutableLiveData<>();
    public LiveData<JobFetchResponse> getJobData() {
        return jobData;
    }

    public LiveData<List<JobFetchResponse>> getSavedJobs() {
        return Jobs;
    }
    public LiveData<List<JobFetchResponse>> getArchivedJobs() {
        return Jobs;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getLoadingState() {
        return isLoading;
    }

    public void fetchJobById(int jobId, Context context) {

        isLoading.setValue(true);

        JobService service = new JobService(context);

        service.fetchJobById(jobId, new JobService.JobServiceCallback() {

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

    public void fetchSavedJobs(int userId, Context context) {
        isLoading.setValue(true);
        JobService jobService = new JobService(context);
        jobService.fetchSavedJob(userId, new JobService.JobServiceCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> response) {
                isLoading.setValue(false);
                if (response != null && !response.isEmpty()) {
                    Log.d("JobViewModel", "Saved jobs fetched: " + response);
                    Jobs.setValue(response != null ? response : new ArrayList<>());
                    Log.d("JobViewModel", "Saved jobs after setValue: " + Jobs.getValue());
                } else {
                    Jobs.setValue(new ArrayList<>());
                    errorMessage.setValue("No saved jobs found for the given user ID.");
                }
            }
            @Override
            public void onError(String message) {
                isLoading.setValue(false);
                errorMessage.setValue(message);
            }
        });
    }

    public void fetchArchivedJobs(int userId, Context context) {
        isLoading.setValue(true);
        JobService service = new JobService(context);
        service.fetchArchivedJob(userId, new JobService.JobServiceCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> response) {
                isLoading.setValue(false);
                if (response != null && !response.isEmpty()) {
                    Jobs.setValue(response != null ? response : new ArrayList<>());
                } else {
                    Jobs.setValue(new ArrayList<>());
                    errorMessage.setValue("No archived jobs found for the given user ID.");
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


