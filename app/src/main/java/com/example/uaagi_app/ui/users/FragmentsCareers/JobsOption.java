package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.dto.JobEnums.Company;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.utils.UiHelpers;

import com.example.uaagi_app.network.Services.JobService;
import com.example.uaagi_app.network.dto.JobFetchResponse;

import java.util.ArrayList;
import java.util.List;

public class JobsOption extends Fragment implements FragmentError.RetryListener {
    private static final String TAG = "JobsOptionFragment";
    public static final String ARG_COMPANY = "company";
    private RecyclerView jobRecyclerView;
    private View loadingContainer;
    private String companyName, departmentName;
    private View errorContainer;
    public JobsOption() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_careers_jobs, container, false);
        if (getArguments() != null) {
            Company selectedCompany = Company.valueOf(getArguments().getString(ARG_COMPANY));
            companyName = selectedCompany.getDisplayName();
            departmentName = getArguments().getString("Department");
        }
        setupUiStates(view);
        jobRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fetchJobs();
        return view;
    }
    public static JobsOption newInstance(Company company, String department) {
        JobsOption fragment = new JobsOption();
        Bundle args = new Bundle();
        args.putString(ARG_COMPANY, company.name());
        args.putString("Department", department);
        fragment.setArguments(args);
        return fragment;
    }
    private void fetchJobs() {
        showLoading();
        JobService service = new JobService(requireContext());
        service.fetchJobsForUser(new JobService.JobServiceCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> response) {
                showContent(response);
            }
            @Override
            public void onError(String errorMessage) {
                showError(errorMessage);
            }
        });
    }
    private void setupUiStates(View view){
        jobRecyclerView = view.findViewById(R.id.job_container);
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
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

        loadingContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        jobRecyclerView.setVisibility(View.VISIBLE);

        if (companyName == null || departmentName == null) return;

        List<JobFetchResponse> filteredJobs = new ArrayList<>();
        Log.d(TAG, "Company Name: " + companyName);
        Log.d(TAG, "Department Name: " + departmentName);
        Log.d(TAG, "Jobs: " + jobs.toString());
        for (JobFetchResponse job : jobs) {
            if (job.getCompany() != null &&
                    companyName.equals(job.getCompany().getDisplayName()) &&
                    departmentName.equals(job.getDepartment())) {

                filteredJobs.add(job);
            }
        }
        Log.d(TAG, "Filtered Jobs Size: " + filteredJobs.size());

        UiHelpers
                .jobCardAdapter(
                jobRecyclerView,
                filteredJobs,
                requireActivity().getSupportFragmentManager(),
                requireContext()
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
        fetchJobs();
    }
}