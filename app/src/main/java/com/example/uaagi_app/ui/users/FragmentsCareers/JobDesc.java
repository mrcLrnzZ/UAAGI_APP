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
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.Services.JobService;
import com.example.uaagi_app.network.dto.JobEnums.Company;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.utils.SessionManager;

import java.util.List;

public class JobDesc extends Fragment {

    View btnApplyNow, btnBack;
    TextView jobTitle, jobCompany, jobDesc, jobLocation, jobQualifications,
            jobBenefits, jobRemoteOption, jobDept;
    private static final String ARG_IS_INTERN = "isIntern";
    private boolean isIntern;
    String Title, Location, company, department;
    ImageButton btnBookmark, btnBlock;
    private Company selectedCompany;
    private static final String TAG = "JobDescFragment";
    
    private ProgressBar progressBar;
    private ScrollView scrollView;
    
    private boolean isSaved = false;
    private boolean isArchived = false;
    private int jobIdInt = -1;

    public static Fragment newInstance(int id, boolean isInter) {
        JobDesc fragment = new JobDesc();
        Bundle args = new Bundle();
        args.putString("jobId", String.valueOf(id));
        args.putBoolean(ARG_IS_INTERN, isInter);
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
            try {
                jobIdInt = Integer.parseInt(jobId);
                fetchJobDetails(jobId);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid job ID: " + jobId);
            }
        }

        btnApplyNow.setOnClickListener(v -> {
            ApplyOption fragment = new ApplyOption();

            Bundle bundle = new Bundle();
            bundle.putString("jobId", jobId);
            fragment.setArguments(bundle);
            UiHelpers.switchFragment(requireActivity().getSupportFragmentManager(), fragment);
        });
        
        btnBookmark.setOnClickListener(v -> {
            if (jobIdInt == -1) return;
            if (isArchived) {
                Toast.makeText(requireContext(), "Job is archived. Unarchive first.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isSaved) {
                Helpers.actionUnsaveJob(requireContext(), jobIdInt, new JobService.FeedbackCallback() {
                    @Override
                    public void feedback(String message) {
                        isSaved = false;
                        updateStatusIcons();
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Helpers.actionSaveJob(requireContext(), jobIdInt, new JobService.FeedbackCallback() {
                    @Override
                    public void feedback(String message) {
                        isSaved = true;
                        updateStatusIcons();
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnBlock.setOnClickListener(v -> {
            if (jobIdInt == -1) return;
            if (isSaved) {
                Toast.makeText(requireContext(), "Job is saved. Unsave first.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isArchived) {
                Helpers.actionUnarchiveJob(requireContext(), jobIdInt, new JobService.FeedbackCallback() {
                    @Override
                    public void feedback(String message) {
                        isArchived = false;
                        updateStatusIcons();
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Helpers.actionArchiveJob(requireContext(), jobIdInt, new JobService.FeedbackCallback() {
                    @Override
                    public void feedback(String message) {
                        isArchived = true;
                        updateStatusIcons();
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        Log.d(TAG, "Job department: " + department);
        return view;
    }


    private void updateStatusIcons() {
        if (!isAdded()) return;

        if (isSaved) {
            btnBookmark.setColorFilter(ContextCompat.getColor(requireContext(), R.color.holo_red_dark));
        } else {
            btnBookmark.setColorFilter(ContextCompat.getColor(requireContext(), R.color.DarkBlue));
        }

        if (isArchived) {
            btnBlock.setImageResource(R.drawable.unarchive);
            btnBlock.setColorFilter(ContextCompat.getColor(requireContext(), R.color.holo_red_dark));
        } else {
            btnBlock.setImageResource(R.drawable.archive);
            btnBlock.setColorFilter(ContextCompat.getColor(requireContext(), R.color.DarkBlue));
        }
    }
    private void initializeStatesOfIcon(JobFetchResponse job){
        if (Helpers.intToBoolean(job.isSaved())) {
            btnBookmark.setColorFilter(ContextCompat.getColor(requireContext(), R.color.holo_red_dark));
            isSaved = true;
        }
        if (Helpers.intToBoolean(job.isArchived())) {
            btnBlock.setImageResource(R.drawable.unarchive);
            btnBlock.setColorFilter(ContextCompat.getColor(requireContext(), R.color.holo_red_dark));
            isArchived = true;
        }
    }

    private String getArgumentsFromBundle(){
        if (getArguments() != null) {
            isIntern = getArguments().getBoolean(ARG_IS_INTERN);
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
            service.fetchJobById(id, SessionManager.getInstance(requireContext()).getUserId(),new JobService.JobServiceCallback() {
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
                    jobQualifications.setText(
                            job.getPreferredQualifications() != null
                                    ? job.getPreferredQualifications()
                                    : job.getRequirements()
                    );
                    jobBenefits.setText(job.getBenefits());
                    jobRemoteOption.setText(job.getRemoteOption().getDisplayName());
                    jobDept.setText(job.getDepartment());
                    initializeStatesOfIcon(job);
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
