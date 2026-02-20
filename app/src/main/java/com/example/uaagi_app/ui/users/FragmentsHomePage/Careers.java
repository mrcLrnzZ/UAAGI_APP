package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.users.FragmentsCareers.Adapter.JobEntry;
import com.example.uaagi_app.ui.users.FragmentsCareers.JobDesc;
import com.example.uaagi_app.ui.utils.UiHelpers;

import com.example.uaagi_app.network.api.JobFetchService;
import com.example.uaagi_app.network.dto.JobFetchResponse;

import java.util.List;

public class Careers extends Fragment implements FragmentError.RetryListener {
    private static final String TAG = "CareersFragment";
    private RecyclerView jobRecyclerView;
    private View loadingContainer;
    private View errorContainer;
    public Careers() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigate_careers, container, false);
        setupUiStates(view);
        jobRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fetchJobs();
        return view;
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

        JobEntry adapter = new JobEntry(jobs, 0, 5, job -> {
            JobDesc fragment = new JobDesc();
            Bundle bundle = new Bundle();
            bundle.putString("jobId", String.valueOf(job.getId()));
            bundle.putString("jobTitle", job.getJobTitle());
            bundle.putString("jobLocation", job.getLocation());
            bundle.putString("jobCompany", job.getCompany().getDisplayName());
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