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
    private List<JobFetchResponse> allJobs = new ArrayList<>();
    private String chipFilter = "All";
    private String searchQuery = "";
    private String companyFilter = "";
    private String departmentFilter = "";
    private boolean isInternFilter = false;
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
        errorMessage.setValue(null);
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
        errorMessage.setValue(null);
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
        errorMessage.setValue(null);
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
        errorMessage.setValue(null);
        isLoading.setValue(true);
        JobService service = new JobService(context);

        service.fetchJobsForUser(new JobService.JobServiceCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> jobs) {
                isLoading.setValue(false);

                if (jobs != null) {
                    allJobs = jobs;
                    jobList.setValue(allJobs);
                    applyFilters();
                } else {
                    allJobs = new ArrayList<>();
                    jobList.setValue(allJobs);
                    errorMessage.setValue("No jobs available.");
                }
            }

            @Override
            public void onError(String message) {
                isLoading.setValue(false);
                errorMessage.setValue(message);
            }
        });
    }
    public void removeSavedJob(JobFetchResponse job) {
        if (savedJobs.getValue() == null) return;

        List<JobFetchResponse> updatedList = new ArrayList<>(savedJobs.getValue());
        updatedList.remove(job);

        savedJobs.setValue(updatedList);
    }
    public void removeArchivedJob(JobFetchResponse job) {
        if (archivedJobs.getValue() == null) return;

        List<JobFetchResponse> updatedList = new ArrayList<>(archivedJobs.getValue());
        updatedList.remove(job);

        archivedJobs.setValue(updatedList);
    }
    private void applyFilters() {
        List<JobFetchResponse> result = new ArrayList<>();

        for (JobFetchResponse job : allJobs) {

            boolean matchesCompany = (companyFilter == null || companyFilter.isEmpty())
                    || (job.getCompany() != null && companyFilter.equals(job.getCompany().getDisplayName()));

            boolean matchesDepartment = (departmentFilter == null || departmentFilter.isEmpty())
                    || (job.getDepartment() != null && departmentFilter.equals(job.getDepartment()));

            boolean matchesIntern = isInternFilter
                    ? "Internship".equalsIgnoreCase(job.getJobType().getDisplayName())
                    : !"Internship".equalsIgnoreCase(job.getJobType().getDisplayName());

            boolean matchesSearch = (searchQuery == null || searchQuery.isEmpty())
                    || (job.getJobTitle() != null && job.getJobTitle().toLowerCase().contains(searchQuery.toLowerCase()));

            boolean matchesChip;
            switch (chipFilter != null ? chipFilter : "All") {
                case "Full-time":
                    matchesChip = job.getJobType() != null &&
                            "Full-time".equalsIgnoreCase(job.getJobType().getDisplayName());
                    break;
                case "Fully-Remote":
                    matchesChip = job.getRemoteOption() != null &&
                            "Fully-Remote".equalsIgnoreCase(job.getRemoteOption().getDisplayName());
                    break;
                case "Hybrid":
                    matchesChip = job.getRemoteOption() != null &&
                            "Hybrid".equalsIgnoreCase(job.getRemoteOption().getDisplayName());
                    break;
                case "Entry Level":
                    matchesChip = job.getExperienceLevel() != null &&
                            "Entry Level".equalsIgnoreCase(job.getExperienceLevel().getDisplayName());
                    break;
                case "All":
                default:
                    matchesChip = true;
                    break;
            }

            if (matchesCompany && matchesDepartment && matchesIntern && matchesSearch && matchesChip) {
                result.add(job);
            }
        }

        jobList.setValue(result);
    }
    public void setFilters(String company, String department, boolean isIntern) {
        this.companyFilter = company;
        this.departmentFilter = department;
        this.isInternFilter = isIntern;
        applyFilters();
    }

    public void setSearchQuery(String query) {
        this.searchQuery = query;
        applyFilters();
    }
    public void setChipFilter(String filter) {
        this.chipFilter = filter;
        applyFilters();
    }
}
