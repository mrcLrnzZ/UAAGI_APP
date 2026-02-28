package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.Services.JobFetchService;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.List;

public class ApplyOption extends Fragment {
    private TextView jobTitleTV, jobLocCom;
    private View contentContainer;
    private View loadingContainer;
    private View errorContainer;
    private View btnBackToDesc;
    public ApplyOption() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_careers_apply_options, container, false);
        String jobID = getArgumentsFromBundle();

        setupUiStates(view);
        jobTitleTV = view.findViewById(R.id.jobTitle);
        jobLocCom = view.findViewById(R.id.jobCompany_jobLocation);
        // Find CardView
        btnBackToDesc = view.findViewById(R.id.btnBackToDesc);
        if (jobID != null) {
            fetchJobs(Integer.parseInt(jobID));
        }
        // Click listener


        return view;
    }
    private void fetchJobs(int jobID) {
        showLoading();
        JobFetchService service = new JobFetchService(requireContext());
        service.fetchJobById(jobID, new JobFetchService.JobFetchCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> jobs) { showContent(jobs); }
            @Override
            public void onError(String errorMessage) { showError(errorMessage); }
        });
    }
    private void showContent(List<JobFetchResponse> job){
        loadingContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        contentContainer.setVisibility(View.VISIBLE);

        if (job != null && !job.isEmpty()) {
            JobFetchResponse jobData = job.get(0);
            jobTitleTV.setText(jobData.getJobTitle());
            jobLocCom.setText(String.format("%s • %s",
                    jobData.getCompany() != null ? jobData.getCompany().getDisplayName() : "N/A",
                    jobData.getLocation()));

            btnBackToDesc.setOnClickListener(v -> {
                JobDesc fragment = new JobDesc();
                Bundle bundle = new Bundle();
                String jobId = String.valueOf(jobData.getId());
                String jobDept = jobData.getDepartment();
                bundle.putString("jobId", jobId);
                bundle.putString("Department", jobDept);
                fragment.setArguments(bundle);
                UiHelpers.switchFragment(requireActivity().getSupportFragmentManager(),fragment);
            });
        }

    }
    private void setupUiStates(View view){
        contentContainer = view.findViewById(R.id.apply_option_container);
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
    }
    private void showLoading() {
        loadingContainer.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
        contentContainer.setVisibility(View.GONE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.loading_container,
                FragmentLoading.newInstance()
        );
    }
    private void showError(String message) {
        loadingContainer.setVisibility(View.GONE);
        contentContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.error_container,
                FragmentError.newInstance(message)
        );
    }
    private String getArgumentsFromBundle(){
        if (getArguments() != null) {
            return getArguments().getString("jobId");
        }
        return null;
    }
}