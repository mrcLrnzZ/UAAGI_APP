package com.example.uaagi_app.data.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uaagi_app.network.Services.JobService;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class JobViewModel extends ViewModel {

    private final MutableLiveData<JobFetchResponse> jobData = new MutableLiveData<>();
    private final MutableLiveData<List<JobFetchResponse>> jobList = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<List<JobFetchResponse>> savedJobs = new MutableLiveData<>();
    private final MutableLiveData<List<JobFetchResponse>> archivedJobs = new MutableLiveData<>();

    public LiveData<JobFetchResponse> getJobData() {
        return jobData;
    }

    public LiveData<List<JobFetchResponse>> getSavedJobs() {
        return savedJobs;
    }
    public LiveData<List<JobFetchResponse>> getJobList() {
        return jobList;
    }
    public LiveData<List<JobFetchResponse>> getArchivedJobs() {
        return archivedJobs;
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

        service.fetchJobById(jobId, SessionManager.getInstance(context).getUserId(),new JobService.JobServiceCallback() {

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
                if (response != null) {
                    savedJobs.setValue(response);
                } else {
                    savedJobs.setValue(new ArrayList<>());
                    errorMessage.setValue("No saved jobs found.");
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
                if (response != null) {
                    archivedJobs.setValue(response);
                } else {
                    archivedJobs.setValue(new ArrayList<>());
                    errorMessage.setValue("No archived jobs found.");
                }
            }
            @Override
            public void onError(String message) {
                isLoading.setValue(false);
                errorMessage.setValue(message);
            }
        });
    }
    public void fetchJobForUser(Context context){
        isLoading.setValue(true);
        JobService service = new JobService(context);
        service.fetchJobsForUser(new JobService.JobServiceCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> jobs) {
                isLoading.setValue(false);
                if (jobs != null) {
                    jobList.setValue(jobs);
                }else{
                    jobList.setValue(new ArrayList<>());
                    errorMessage.setValue("No jobs found for the user.");
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
