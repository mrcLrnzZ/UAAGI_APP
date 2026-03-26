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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.JobViewModel;
import com.example.uaagi_app.network.Services.ApplicationService;
import com.example.uaagi_app.network.Services.JobService;
import com.example.uaagi_app.network.dto.Applicant;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.SessionManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements FragmentError.RetryListener {

    private RecyclerView jobRecyclerView;
    private View loadingContainer;
    private View errorContainer;
    private TextView tvNumOfJobs, tvAppliedStatCount, tvResultCount;
    private TextInputEditText etSearch;
    private ChipGroup chipGroupFilter;
    private JobViewModel jobViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_navigate_home, container, false);
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        jobViewModel.getLoadingState().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null && isLoading) {
                showLoading();
            }
        });

        jobViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                showError(error);
            }
        });
        jobViewModel.getJobList().observe(getViewLifecycleOwner(), jobs -> {
            if (jobs != null) {
                showContent(jobs);
                tvNumOfJobs.setText(String.valueOf(jobs.size()));
                tvResultCount.setText(jobs.size() + " open positions");
            }
        });
        setupUiStates(view);
        jobRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fetchData();

        chipGroupFilter.setOnCheckedChangeListener((group, checkedId) -> {
            Chip selectedChip = group.findViewById(checkedId);

            if (selectedChip != null) {
                String filter = selectedChip.getText().toString();
                jobViewModel.setChipFilter(filter);
                tvResultCount.setText(jobViewModel.getJobList().getValue().size() + " open positions");
            }
        });
        return view;
    }
    private void setupUiStates(View view){
        jobRecyclerView = view.findViewById(R.id.job_container);
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
        tvNumOfJobs = view.findViewById(R.id.NumOfJobs);
        chipGroupFilter = view.findViewById(R.id.chipGroupFilter);
        tvAppliedStatCount = view.findViewById(R.id.tvAppliedStatCount);
        tvResultCount = view.findViewById(R.id.tvResultCount);
        etSearch = view.findViewById(R.id.etSearch);
        setupSearchFunctionality();
    }
    private void setupSearchFunctionality() {
        SimpleTextWatcher.bindTextWatcher(etSearch,
                new SimpleTextWatcher(query -> {
                    jobViewModel.setSearchQuery(query);
                })
        );
    }
    private void fetchData() {
        jobViewModel.fetchJobForUser(requireContext());

        int userId = SessionManager.getInstance(requireContext()).getUserId();

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
                requireContext(),
                jobViewModel
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
