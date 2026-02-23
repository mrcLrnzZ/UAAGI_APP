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
import com.example.uaagi_app.ui.users.FragmentsCareers.Adapter.JobEntry;
import com.example.uaagi_app.ui.utils.UiHelpers;

import com.example.uaagi_app.network.Services.JobFetchService;
import com.example.uaagi_app.network.dto.JobFetchResponse;

import java.util.ArrayList;
import java.util.List;

public class JobsOption extends Fragment implements FragmentError.RetryListener {
    private static final String TAG = "JobsOptionFragment";
    public static final String ARG_COMPANY = "company";
    private RecyclerView jobRecyclerView;
    private View loadingContainer;
    private String brandName, departmentName;
    private View errorContainer;
    public JobsOption() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_careers_jobs, container, false);
        if (getArguments() != null) {
            Company selectedCompany = Company.valueOf(getArguments().getString(ARG_COMPANY));
            brandName = selectedCompany.getDisplayName();
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
        JobFetchService service = new JobFetchService(requireContext());
        service.fetchJobsForUser(new JobFetchService.JobFetchCallback() {
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

        if (brandName == null || departmentName == null) return;

        List<JobFetchResponse> filteredJobs = new ArrayList<>();
        Log.d(TAG, "Brand Name: " + brandName);
        Log.d(TAG, "Department Name: " + departmentName);
        Log.d(TAG, "Jobs: " + jobs.toString());
        for (JobFetchResponse job : jobs) {
            if (job.getCompany() != null &&
                    brandName.equals(job.getCompany().getDisplayName()) &&
                    departmentName.equals(job.getDepartment())) {

                filteredJobs.add(job);
            }
        }
        Log.d(TAG, "Filtered Jobs Size: " + filteredJobs.size());

        JobEntry adapter = new JobEntry(filteredJobs, 0, 5, job -> {

            JobDesc fragment = new JobDesc();
            Bundle bundle = new Bundle();

            bundle.putString("jobId", String.valueOf(job.getId()));
            bundle.putString("jobTitle", job.getJobTitle());
            bundle.putString("jobLocation", job.getLocation());
            bundle.putString("Department", departmentName);
            bundle.putString("company_enum", job.getCompany().name());

            fragment.setArguments(bundle);

            UiHelpers.switchFragment(
                    requireActivity().getSupportFragmentManager(),
                    fragment
            );
        });

        jobRecyclerView.setAdapter(adapter);
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