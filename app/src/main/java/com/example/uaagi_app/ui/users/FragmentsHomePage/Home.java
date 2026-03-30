package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.JobViewModel;
import com.example.uaagi_app.data.viewmodel.ProfileViewModel;
import com.example.uaagi_app.network.Services.ApplicationService;
import com.example.uaagi_app.network.dto.Applicant;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.network.dto.PreEmpDto.UserInfo;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.SessionManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.List;

public class Home extends Fragment implements FragmentError.RetryListener {

    private RecyclerView jobRecyclerView;
    private View loadingContainer;
    private View errorContainer;
    private TextView tvNumOfJobs, tvAppliedStatCount, tvResultCount, tvGreetings;
    private TextInputEditText etSearch;
    private ChipGroup chipGroupFilter;
    private JobViewModel jobViewModel;
    private ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_navigate_home, container, false);
        
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        setupUiStates(view);
        setupObservers();
        
        jobRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fetchData();

        chipGroupFilter.setOnCheckedChangeListener((group, checkedId) -> {
            Chip selectedChip = group.findViewById(checkedId);
            if (selectedChip != null) {
                String filter = selectedChip.getText().toString();
                jobViewModel.setChipFilter(filter);
                if (jobViewModel.getJobList().getValue() != null) {
                    tvResultCount.setText(getString(R.string.open_positions_count, jobViewModel.getJobList().getValue().size()));
                }
            }
        });
        
        return view;
    }

    private void setupObservers() {
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
                tvResultCount.setText(getString(R.string.open_positions_count, jobs.size()));
            }
        });

        profileViewModel.getPreEmpData().observe(getViewLifecycleOwner(), data -> {
            String greeting = getGreeting();
            if (data != null && data.getUserInfo() != null) {
                UserInfo info = data.getUserInfo();
                String firstName = info.getFirstName();
                if (firstName != null && !firstName.trim().isEmpty()) {
                    tvGreetings.setText(String.format("%s, %s!", greeting, firstName));
                } else {
                    tvGreetings.setText(String.format("%s!", greeting));
                }
            } else {
                tvGreetings.setText(String.format("%s!", greeting));
            }
        });
    }

    public String getGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour < 12) {
            return "Good Morning";
        } else if (hour < 18) {
            return "Good Afternoon";
        } else {
            return "Good Evening";
        }
    }

    private void setupUiStates(View view){
        jobRecyclerView = view.findViewById(R.id.job_container);
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
        tvNumOfJobs = view.findViewById(R.id.NumOfJobs);
        chipGroupFilter = view.findViewById(R.id.chipGroupFilter);
        tvAppliedStatCount = view.findViewById(R.id.tvAppliedStatCount);
        tvResultCount = view.findViewById(R.id.tvResultCount);
        tvGreetings = view.findViewById(R.id.tvGreetings);
        etSearch = view.findViewById(R.id.etSearch);
        setupSearchFunctionality();
    }

    private void setupSearchFunctionality() {
        SimpleTextWatcher.bindTextWatcher(etSearch,
                new SimpleTextWatcher(query -> jobViewModel.setSearchQuery(query))
        );
    }

    private void fetchData() {
        jobViewModel.fetchJobForUser(requireContext());
        profileViewModel.fetchContent(requireContext());

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
