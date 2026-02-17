package com.example.uaagi_app.ui.users.FragmentsCareers;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.api.JobFetchService;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.FragmentsHomePage.Careers;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.List;

public class JobDesc extends Fragment {

    View btnApplyNow, btnBack;
    TextView jobTitle, jobDesc, jobLocation, jobQualifications,
            jobBenefits, jobRemoteOption, jobDept;
    private static final String TAG = "JobDescFragment";

    public JobDesc(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_careers_job_desc, container, false);
        btnApplyNow = view.findViewById(R.id.btnApplyNow);
        btnBack = view.findViewById(R.id.btnBack);
        jobTitle = view.findViewById(R.id.tvJobTitle);
        jobDesc = view.findViewById(R.id.tvJobDesc);
        jobLocation = view.findViewById(R.id.tvLocation);
        jobQualifications = view.findViewById(R.id.tvQualifications);
        jobBenefits = view.findViewById(R.id.tvPerks);
        jobRemoteOption = view.findViewById(R.id.tvWorkSetupValue);
        jobDept = view.findViewById(R.id.tvDivisionValue);

        if (getArguments() != null) {
            String jobId = getArguments().getString("jobId");

            UiHelpers.showToast(jobId, requireContext());
            fetchJobDetails(jobId);
        }
        btnApplyNow.setOnClickListener(v -> {
            UiHelpers.switchFragment(requireActivity().getSupportFragmentManager(), new ApplyOption());
        });

        btnBack.setOnClickListener(v -> {
            UiHelpers.switchFragment(requireActivity().getSupportFragmentManager(), new Careers());
        });


        return view;
    }
    private void fetchJobDetails(String jobId) {
        JobFetchService service = new JobFetchService(requireContext());
        service.fetchJobById(Integer.parseInt(jobId), new JobFetchService.JobFetchCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> response) {
                JobFetchResponse job = response.get(0);
                jobTitle.setText(job.getJobTitle());
                jobDesc.setText(job.getJobDescription());
                jobLocation.setText(job.getLocation());
                jobQualifications.setText(job.getPreferredQualifications());
                jobBenefits.setText(job.getBenefits());
                jobRemoteOption.setText(job.getRemoteOption().getDisplayName());
                jobDept.setText(job.getDepartment());
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });
    }
}

