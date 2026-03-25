package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

public class Home extends Fragment implements FragmentError.RetryListener {

    private RecyclerView jobRecyclerView;
    private View loadingContainer;
    private View errorContainer;
    private TextView tvNumOfJobs, tvAppliedStatCount;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_navigate_home, container, false);
        setupUiStates(view);
        jobRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fetchData();
        return view;
    }
    private void setupUiStates(View view){
        jobRecyclerView = view.findViewById(R.id.job_container);
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
        tvNumOfJobs = view.findViewById(R.id.NumOfJobs);
        tvAppliedStatCount = view.findViewById(R.id.tvAppliedStatCount);
    }
    
    private void fetchData() {
        showLoading();
        int userId = SessionManager.getInstance(requireContext()).getUserId();
        
        // Fetch Job Count and List
        JobService jobService = new JobService(requireContext());
        jobService.fetchJobsForUser(new JobService.JobServiceCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> response) {
                showContent(response);
                if (response != null) {
                    tvNumOfJobs.setText(String.valueOf(response.size()));
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
                if (applicants != null) {
                    tvAppliedStatCount.setText(String.valueOf(applicants.size()));
                }
            }
            @Override
            public void onError(String message) {
                Log.e("HomeFragment", "Error fetching applications: " + message);
            }
        });
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
        UiHelpers.jobCardAdapter(
                jobRecyclerView,
                jobs,
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
        fetchData();
    }
}
