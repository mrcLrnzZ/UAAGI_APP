package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.JobViewModel;
import com.example.uaagi_app.network.dto.JobEnums.Company;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.ui.utils.UiHelpers;

import com.example.uaagi_app.network.dto.JobFetchResponse;

import java.util.List;

public class JobsOption extends Fragment implements FragmentError.RetryListener {
    private static final String TAG = "JobsOptionFragment";
    public static final String ARG_COMPANY = "company";
    private static final String ARG_IS_INTERN = "isIntern";
    private boolean isIntern;
    private RecyclerView jobRecyclerView;
    private FrameLayout loadingContainer;
    private String companyName, departmentName;
    private EditText searchJob;

    private FrameLayout errorContainer;
    private JobViewModel jobViewModel;

    public JobsOption() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_careers_jobs, container, false);
        if (getArguments() != null) {
            String companyEnumName = getArguments().getString(ARG_COMPANY);
            if (companyEnumName != null) {
                Company selectedCompany = Company.valueOf(companyEnumName);
                companyName = selectedCompany.getDisplayName();
            }
            departmentName = getArguments().getString("Department");
            isIntern = getArguments().getBoolean(ARG_IS_INTERN);
        }

        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        setupUiStates(view);
        jobRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        setupObservers();

        jobViewModel.setFilters(companyName, departmentName, isIntern);
        jobViewModel.fetchJobForUser(requireContext());

        return view;
    }

    private void setupObservers() {
        jobViewModel.getLoadingState().observe(getViewLifecycleOwner(), isLoading -> {
            if (Boolean.TRUE.equals(isLoading)) {
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
            }
        });
    }

    public static JobsOption newInstance(Company company, String department, boolean isIntern) {
        JobsOption fragment = new JobsOption();
        Bundle args = new Bundle();
        args.putString(ARG_COMPANY, company.name());
        args.putBoolean(ARG_IS_INTERN, isIntern);
        args.putString("Department", department);
        fragment.setArguments(args);
        return fragment;
    }

    private void setupUiStates(View view){
        jobRecyclerView = view.findViewById(R.id.job_container);
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
        searchJob = view.findViewById(R.id.searchJob);
        setupSearchFunctionality();
    }

    private void setupSearchFunctionality() {
        SimpleTextWatcher.bindTextWatcher(searchJob,
                new SimpleTextWatcher(query -> {
                    if (jobViewModel != null) {
                        jobViewModel.setSearchQuery(query);
                    }
                })
        );
    }

    private void showLoading() {
        loadingContainer.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
        jobRecyclerView.setVisibility(View.GONE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.loading_container,
                FragmentLoading.newInstance()
        );
    }

    private void showContent(List<JobFetchResponse> jobs) {
        if (jobs.isEmpty()) {
            Boolean isLoading = jobViewModel.getLoadingState().getValue();
            String error = jobViewModel.getErrorMessage().getValue();

            if (Boolean.FALSE.equals(isLoading) && error == null) {
                showError("No open positions found.");
            }
            return;
        }

        loadingContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        jobRecyclerView.setVisibility(View.VISIBLE);

        UiHelpers.jobCardAdapter(
                jobRecyclerView,
                jobs,
                getParentFragmentManager(),
                requireContext(),
                jobViewModel
        );
    }

    private void showError(String message) {
        loadingContainer.setVisibility(View.GONE);
        jobRecyclerView.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.error_container,
                FragmentError.newInstance(message)
        );
    }

    @Override
    public void onRetry() {
        if (jobViewModel != null) {
            jobViewModel.fetchJobForUser(requireContext());
        }
    }
}
