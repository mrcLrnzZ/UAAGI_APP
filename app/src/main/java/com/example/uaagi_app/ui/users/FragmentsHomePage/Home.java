package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.Services.ApplicationService;
import com.example.uaagi_app.network.Services.JobService;
import com.example.uaagi_app.network.dto.Applicant;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements FragmentError.RetryListener {

    private RecyclerView jobRecyclerView;
    private View loadingContainer;
    private View errorContainer;
    private TextView tvNumOfJobs, tvAppliedStatCount, tvResultCount;
    private EditText etSearch;
    private List<JobFetchResponse> originalJobList = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_navigate_home, container, false);
        setupUiStates(view);
        
        if (jobRecyclerView != null) {
            jobRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        } else {
            Log.e("HomeFragment", "jobRecyclerView is null. Check if R.id.job_container exists in fragment_navigate_home.xml");
        }
        
        setupSearch();
        fetchData();
        return view;
    }
    private void setupUiStates(View view){
        jobRecyclerView = view.findViewById(R.id.job_container);
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
        tvNumOfJobs = view.findViewById(R.id.NumOfJobs);
        tvAppliedStatCount = view.findViewById(R.id.tvAppliedStatCount);
        tvResultCount = view.findViewById(R.id.tvResultCount);
        etSearch = view.findViewById(R.id.etSearch);
    }

    private void setupSearch() {
        if (etSearch != null) {
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterJobs(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void filterJobs(String query) {
        if (originalJobList == null || originalJobList.isEmpty()) return;

        List<JobFetchResponse> filteredList = new ArrayList<>();
        for (JobFetchResponse job : originalJobList) {
            if (job.getJobTitle().toLowerCase().contains(query.toLowerCase()) ||
                job.getCompany().getDisplayName().toLowerCase().contains(query.toLowerCase()) ||
                job.getLocation().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(job);
            }
        }

        updateRecyclerView(filteredList);
        if (tvResultCount != null) {
            tvResultCount.setText(filteredList.size() + " open positions");
        }
    }

    private void fetchData() {
        showLoading();
        int userId = SessionManager.getInstance(requireContext()).getUserId();

        // Fetch Job Count and List
        JobService jobService = new JobService(requireContext());
        jobService.fetchJobsForUser(new JobService.JobServiceCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> response) {
                originalJobList = response != null ? new ArrayList<>(response) : new ArrayList<>();
                showContent(originalJobList);
                if (response != null && tvNumOfJobs != null) {
                    tvNumOfJobs.setText(String.valueOf(response.size()));
                }
                if (response != null && tvResultCount != null) {
                    tvResultCount.setText(response.size() + " open positions");
                }
            }
            @Override
            public void onError(String errorMessage) {
                showError(errorMessage);
            }
        });

        // Fetch Applied Count
        ApplicationService appService = new ApplicationService(requireContext());
        appService.fetchApplicantsForUser(userId, new ApplicationService.FetchApplicantsCallback() {
            @Override
            public void onResponse(List<Applicant> applicants) {
                if (applicants != null && tvAppliedStatCount != null) {
                    int appliedOnlyCount = 0;
                    for (Applicant a : applicants) {
                        String status = a.getStatus();
                        String interviewStatus = a.getInterviewStatus();
                        
                        // Standardized logic: Count as "Applied" if status is "Applied" 
                        // AND it's not already in "Scheduled" interview state
                        if (status != null && status.equalsIgnoreCase("Applied")) {
                            if (interviewStatus == null || !interviewStatus.equalsIgnoreCase("Scheduled")) {
                                appliedOnlyCount++;
                            }
                        }
                    }
                    tvAppliedStatCount.setText(String.valueOf(appliedOnlyCount));
                }
            }
            @Override
            public void onError(String message) {
                Log.e("HomeFragment", "Error fetching applications: " + message);
            }
        });
    }

    private void showLoading() {
        if (loadingContainer != null) loadingContainer.setVisibility(View.VISIBLE);
        if (errorContainer != null) errorContainer.setVisibility(View.GONE);
        if (jobRecyclerView != null) jobRecyclerView.setVisibility(View.GONE);
        
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.loading_container,
                FragmentLoading.newInstance()
        );
    }
    private void showContent(List<JobFetchResponse> jobs) {
        if (loadingContainer != null) loadingContainer.setVisibility(View.GONE);
        if (errorContainer != null) errorContainer.setVisibility(View.GONE);
        if (jobRecyclerView != null) {
            jobRecyclerView.setVisibility(View.VISIBLE);
            updateRecyclerView(jobs);
        }
    }

    private void updateRecyclerView(List<JobFetchResponse> jobs) {
        UiHelpers.jobCardAdapter(
                jobRecyclerView,
                jobs,
                requireActivity().getSupportFragmentManager(),
                requireContext()
        );
    }

    private void showError(String message) {
        if (loadingContainer != null) loadingContainer.setVisibility(View.GONE);
        if (jobRecyclerView != null) jobRecyclerView.setVisibility(View.GONE);
        if (errorContainer != null) {
            errorContainer.setVisibility(View.VISIBLE);
            UiHelpers.replaceChildFragment(
                    getChildFragmentManager(),
                    R.id.error_container,
                    FragmentError.newInstance(message)
            );
        }
    }

    @Override
    public void onRetry() {
        fetchData();
    }
}
