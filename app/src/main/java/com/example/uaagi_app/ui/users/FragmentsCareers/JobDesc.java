package com.example.uaagi_app.ui.users.FragmentsCareers;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.Services.JobService;
import com.example.uaagi_app.network.dto.JobEnums.Company;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.List;

public class JobDesc extends Fragment {

    View btnApplyNow, btnBack;
    TextView jobTitle, jobCompany, jobDesc, jobLocation, jobQualifications,
            jobBenefits, jobRemoteOption, jobDept;
    String Title, Location, company, department;
    ImageButton btnBookmark, btnBlock;
    private Company selectedCompany;
    private static final String TAG = "JobDescFragment";
    
    private ProgressBar progressBar;
    private ScrollView scrollView;

    public static Fragment newInstance(int id) {
        JobDesc fragment = new JobDesc();
        Bundle args = new Bundle();
        args.putString("jobId", String.valueOf(id));
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_careers_job_desc, container, false);
        btnApplyNow = view.findViewById(R.id.btnApplyNow);
        btnBack = view.findViewById(R.id.btnBack);
        jobTitle = view.findViewById(R.id.tvJobTitle);
        jobCompany = view.findViewById(R.id.tvCompany);
        jobDesc = view.findViewById(R.id.tvJobDesc);
        jobLocation = view.findViewById(R.id.tvLocation);
        jobQualifications = view.findViewById(R.id.tvQualifications);
        jobBenefits = view.findViewById(R.id.tvPerks);
        jobRemoteOption = view.findViewById(R.id.tvWorkSetupValue);
        jobDept = view.findViewById(R.id.tvDivisionValue);
        btnBookmark = view.findViewById(R.id.btnBookmark);
        btnBlock = view.findViewById(R.id.btnBlock);
        
        progressBar = view.findViewById(R.id.progressBar);
        scrollView = view.findViewById(R.id.scrollView);

        String jobId = getArgumentsFromBundle();
        if (jobId != null) {
            fetchJobDetails(jobId);
        }

        btnApplyNow.setOnClickListener(v -> {
            ApplyOption fragment = new ApplyOption();

            Bundle bundle = new Bundle();
            bundle.putString("jobId", jobId);
            fragment.setArguments(bundle);
            UiHelpers.switchFragment(requireActivity().getSupportFragmentManager(), fragment);
        });
        btnBookmark.setOnClickListener(v -> {
            if (btnBookmark.getColorFilter() != null) {
                btnBookmark.setColorFilter(null);
                UiHelpers.showToast("Unbookmarked!", requireContext());
                return;
            }
            btnBookmark.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.holo_blue_light)
            );
            UiHelpers.showToast("Bookmarked!", requireContext());
        });
        btnBlock.setOnClickListener(v -> {
            if (btnBlock.getColorFilter() != null) {
                btnBlock.setColorFilter(null);
                UiHelpers.showToast("Unblocked!", requireContext());
                return;
            }
            btnBlock.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.holo_red_dark)
            );
            UiHelpers.showToast("Blocked!", requireContext());
        });
        Log.d(TAG, "Job department: " + department);
        return view;
    }
    private String getArgumentsFromBundle(){
        if (getArguments() != null) {
            department = getArguments().getString("Department");
            String enumName = getArguments().getString("company_enum");
            if (enumName != null) {
                try {
                    selectedCompany = Company.valueOf(enumName);
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, "Invalid company enum: " + enumName);
                }
            }
            return getArguments().getString("jobId");
        }
        return null;
    }
    private void fetchJobDetails(String jobId) {
        if (jobId == null || jobId.isEmpty()) return;
        
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if (scrollView != null) scrollView.setVisibility(View.GONE);
        
        JobService service = new JobService(requireContext());
        Log.d(TAG, "fetchJobDetails: "+ jobId);
        try {
            int id = Integer.parseInt(jobId);
            service.fetchJobById(id, new JobService.JobServiceCallback() {
                @Override
                public void onResponse(List<JobFetchResponse> response) {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    if (scrollView != null) scrollView.setVisibility(View.VISIBLE);
                    
                    if (response == null || response.isEmpty()) return;
                    JobFetchResponse job = response.get(0);
                    jobTitle.setText(job.getJobTitle());
                    jobDesc.setText(job.getJobDescription());
                    jobCompany.setText(job.getCompany().getDisplayName());
                    jobLocation.setText(job.getLocation());
                    jobQualifications.setText(job.getPreferredQualifications());
                    jobBenefits.setText(job.getBenefits());
                    jobRemoteOption.setText(job.getRemoteOption().getDisplayName());
                    jobDept.setText(job.getDepartment());

                    btnBack.setOnClickListener(v ->
                            UiHelpers.switchFragment(
                                    requireActivity().getSupportFragmentManager(),
                                    JobsOption.newInstance(job.getCompany(), job.getDepartment())
                            )
                    );
                }

                @Override
                public void onError(String errorMessage) {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    if (scrollView != null) scrollView.setVisibility(View.VISIBLE);
                    Log.e(TAG, errorMessage);
                }
            });
        } catch (NumberFormatException e) {
            if (progressBar != null) progressBar.setVisibility(View.GONE);
            Log.e(TAG, "Invalid job ID format: " + jobId);
        }
    }
}
