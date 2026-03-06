package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.dto.JobEnums.Company;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
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
    private EditText searchJob;
    private List<JobFetchResponse> allJobs;
    private List<JobFetchResponse> filteredJobs;
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
        searchJob = view.findViewById(R.id.searchJob);
        setupSearchFunctionality();
    }

    private void setupSearchFunctionality() {
        SimpleTextWatcher.bindTextWatcher(searchJob,
                new SimpleTextWatcher(query -> filterJobs(query))
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

        loadingContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        jobRecyclerView.setVisibility(View.VISIBLE);

        if (companyName == null || departmentName == null) return;

        filteredJobs = new ArrayList<>();

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

        allJobs = new ArrayList<>(filteredJobs);

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

    private void filterJobs(String query){
        if (allJobs == null) {
            return;
        }

        List<JobFetchResponse> searchResults = new ArrayList<>();

        if (query.isEmpty()) {
            searchResults.addAll(allJobs);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (JobFetchResponse job : allJobs) {
                if (job.getJobTitle() != null &&
                    job.getJobTitle().toLowerCase().contains(lowerCaseQuery)) {
                    searchResults.add(job);
                }
            }
        }

        UiHelpers.jobCardAdapter(
                jobRecyclerView,
                searchResults,
                requireActivity().getSupportFragmentManager(),
                requireContext()
        );
    }

    @Override
    public void onRetry() {
        fetchJobs();
    }
}