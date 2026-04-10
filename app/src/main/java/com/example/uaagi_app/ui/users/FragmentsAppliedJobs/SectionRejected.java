package com.example.uaagi_app.ui.users.FragmentsAppliedJobs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.adapter.AppliedJobsAdapter;
import com.example.uaagi_app.data.model.AppliedJob;
import com.example.uaagi_app.data.viewmodel.ApplicantViewModel;
import com.example.uaagi_app.network.dto.Applicant;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SectionRejected extends Fragment implements AppliedJobsAdapter.OnJobActionListener {

    private RecyclerView rvRejectedJobs;
    private LinearLayout layoutEmptyState;
    private AppliedJobsAdapter adapter;
    private List<AppliedJob> rejectedJobsList;
    private ApplicantViewModel applicantViewModel;

    public SectionRejected() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicantViewModel = new ViewModelProvider(requireActivity()).get(ApplicantViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applicantViewModel.fetchApplicantsForUser(SessionManager.getInstance(requireContext()).getUserId(), requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applied_jobs_section_rejected, container, false);
        rvRejectedJobs = view.findViewById(R.id.rvRejectedJobs);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);

        setupRecyclerView();
        observeApplicants();
        return view;
    }

    private void setupRecyclerView() {
        rejectedJobsList = new ArrayList<>();
        rvRejectedJobs.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AppliedJobsAdapter(getContext(), rejectedJobsList, this);
        rvRejectedJobs.setAdapter(adapter);
    }

    private void observeApplicants() {
        applicantViewModel.getApplicants().observe(getViewLifecycleOwner(), applicants -> {
            rejectedJobsList.clear();
            Random random = new Random();
            for (Applicant applicant : applicants) {
                String status = applicant.getStatus();
                
                if (Objects.equals(status, "Rejected") || Objects.equals(status, "rejected")) {
                    AppliedJob job = new AppliedJob(
                            String.valueOf(applicant.getApplicationId()),
                            applicant.getJobTitle(),
                            applicant.getCompany(),
                            applicant.getLocation(),
                            Helpers.formatToOrdinalDate(applicant.getSubmissionDate()),
                            applicant.getApplyMethod(),
                            "Application closed",
                            false,
                            applicant.getStatus(),
                            applicant.getViewedAt()
                    );
                    job.setMatchScore(40 + random.nextInt(20));
                    rejectedJobsList.add(job);
                }
            }
            updateUiState();
            adapter.notifyDataSetChanged();
        });
    }

    private void updateUiState() {
        if (rejectedJobsList.isEmpty()) {
            rvRejectedJobs.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            rvRejectedJobs.setVisibility(View.VISIBLE);
            layoutEmptyState.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStatusUpdated(AppliedJob job, String newStatus) {}

    @Override
    public void onJobArchived(AppliedJob job) {
        rejectedJobsList.remove(job);
        adapter.notifyDataSetChanged();
        updateUiState();
    }

    @Override
    public void onApplicationWithdrawn(AppliedJob job) {}

    @Override
    public void onViewDetails(AppliedJob job) {
        showApplicationTimeline(job);
    }

    private void showApplicationTimeline(AppliedJob job) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_application_timeline);
        
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        ((TextView) dialog.findViewById(R.id.tvDialogJobTitle)).setText(job.getJobTitle());
        ((TextView) dialog.findViewById(R.id.tvTimelineAppliedDate)).setText(job.getAppliedDate());
        ((TextView) dialog.findViewById(R.id.tvTimelineStatus)).setText("Status: " + job.getStatus());
        
        dialog.findViewById(R.id.ivCloseTimeline).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
