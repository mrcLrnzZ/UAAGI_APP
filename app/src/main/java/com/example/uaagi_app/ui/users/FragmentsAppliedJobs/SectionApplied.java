package com.example.uaagi_app.ui.users.FragmentsAppliedJobs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.adapter.AppliedJobsAdapter;
import com.example.uaagi_app.data.model.AppliedJob;
import com.example.uaagi_app.data.viewmodel.ApplicantViewModel;
import com.example.uaagi_app.network.Services.ApplicationService;
import com.example.uaagi_app.network.dto.Applicant;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SectionApplied extends Fragment implements AppliedJobsAdapter.OnJobActionListener {

    private RecyclerView rvAppliedJobs;
    private LinearLayout layoutEmptyState;
    private AppliedJobsAdapter adapter;
    private List<AppliedJob> appliedJobsList;
    private ApplicantViewModel applicantViewModel;
    private ApplicationService applicationService;

    public SectionApplied() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicantViewModel = new ViewModelProvider(requireActivity()).get(ApplicantViewModel.class);
        applicationService = new ApplicationService(requireContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applicantViewModel.fetchApplicantsForUser(SessionManager.getInstance(requireContext()).getUserId(), requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applied_jobs_section_applied, container, false);
        rvAppliedJobs = view.findViewById(R.id.rvAppliedJobs);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);

        setupRecyclerView();
        observeApplicants();
        setupSwipeToAction();
        return view;
    }

    private void setupRecyclerView() {
        appliedJobsList = new ArrayList<>();
        rvAppliedJobs.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AppliedJobsAdapter(getContext(), appliedJobsList, this);
        rvAppliedJobs.setAdapter(adapter);
    }

    private void setupSwipeToAction() {
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                AppliedJob job = appliedJobsList.get(position);
                if (direction == ItemTouchHelper.RIGHT) {
                    onJobArchived(job);
                } else if (direction == ItemTouchHelper.LEFT) {
                    onApplicationWithdrawn(job);
                }
            }
        };
        new ItemTouchHelper(swipeCallback).attachToRecyclerView(rvAppliedJobs);
    }

    private void observeApplicants() {
        applicantViewModel.getApplicants().observe(getViewLifecycleOwner(), applicants -> {
            appliedJobsList.clear();
            Random random = new Random();
            for (Applicant applicant : applicants) {
                String status = applicant.getStatus();
                String interviewStatus = applicant.getInterviewStatus();
                
                // Only show in "Applied" if NOT scheduled for interview
                boolean isScheduled = interviewStatus != null && interviewStatus.equalsIgnoreCase("Scheduled");
                
                if (!isScheduled && (Objects.equals(status, "Applied") || Objects.equals(status, "Interviewing"))) {
                    AppliedJob job = new AppliedJob(
                            String.valueOf(applicant.getApplicationId()),
                            applicant.getJobTitle(),
                            applicant.getCompany(),
                            applicant.getLocation(),
                            Helpers.formatToOrdinalDate(applicant.getSubmissionDate()),
                            applicant.getApplyMethod(),
                            "This employer typically responds within 1 day",
                            false,
                            applicant.getStatus(),
                            applicant.getViewedAt()
                    );
                    job.setMatchScore(65 + random.nextInt(31));
                    Log.d("AppliedJob", "Viewed At: " + job.getViewedAt());
                    appliedJobsList.add(job);
                }
            }
            updateUiState();
            adapter.notifyDataSetChanged();
        });
    }

    private void updateUiState() {
        if (appliedJobsList.isEmpty()) {
            rvAppliedJobs.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            rvAppliedJobs.setVisibility(View.VISIBLE);
            layoutEmptyState.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStatusUpdated(AppliedJob job, String newStatus) {
        Toast.makeText(getContext(), "Status updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onJobArchived(AppliedJob job) {
        // Archive logic can be implemented here if there is a specific API for archiving applications
        appliedJobsList.remove(job);
        adapter.notifyDataSetChanged();
        updateUiState();
        Toast.makeText(getContext(), "Removed from view", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onApplicationWithdrawn(AppliedJob job) {
        int appId = Integer.parseInt(job.getJobId()); // Using jobId field which stores applicationId
        applicationService.withdrawApplication(appId, new ApplicationService.SimpleCallback() {
            @Override
            public void onResponse(String message) {
                appliedJobsList.remove(job);
                adapter.notifyDataSetChanged();
                updateUiState();
                Toast.makeText(getContext(), "Application withdrawn", Toast.LENGTH_SHORT).show();
                applicantViewModel.fetchApplicantsForUser(SessionManager.getInstance(requireContext()).getUserId(), requireContext());
            }
            @Override
            public void onError(String message) {
                adapter.notifyDataSetChanged(); // Restore item if failed
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

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
        TextView tvViewedBy = dialog.findViewById(R.id.tvViewedBy);
        TextView tvTimelineDate = dialog.findViewById(R.id.tvTimelineDate);
        TextView tvHasViewed = dialog.findViewById(R.id.tvHasViewed);
        if (tvViewedBy != null) tvViewedBy.setText(job.getViewedAt() != null ? "Application viewed by recruiter" : "Not yet viewed");
        if (tvTimelineDate != null) tvTimelineDate.setText(Helpers.formatToOrdinalDate(job.getViewedAt()));
        if (tvHasViewed != null) tvHasViewed.setText(job.getViewedAt() != null ? "Has been Viewed" : "Not yet viewed");
        dialog.findViewById(R.id.ivCloseTimeline).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
