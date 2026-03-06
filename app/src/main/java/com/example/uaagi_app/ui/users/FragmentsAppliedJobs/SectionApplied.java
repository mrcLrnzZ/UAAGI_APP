package com.example.uaagi_app.ui.users.FragmentsAppliedJobs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.uaagi_app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class SectionApplied extends Fragment implements AppliedJobsAdapter.OnJobActionListener {

    private RecyclerView rvAppliedJobs;
    private AppliedJobsAdapter adapter;
    private List<AppliedJob> appliedJobsList;
    private ApplicantViewModel applicantViewModel;

    public SectionApplied() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_applied_jobs_section_applied,
                container,
                false
        );

        rvAppliedJobs = view.findViewById(R.id.rvAppliedJobs);

        setupRecyclerView();
        observeApplicants();

        return view;
    }

    private void setupRecyclerView() {
        appliedJobsList = new ArrayList<>();

        rvAppliedJobs.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AppliedJobsAdapter(getContext(), appliedJobsList, this);
        rvAppliedJobs.setAdapter(adapter);
    }
    private void observeApplicants() {
        applicantViewModel.getApplicants().observe(getViewLifecycleOwner(), applicants -> {

            appliedJobsList.clear();

            for (Applicant applicant : applicants) {
                AppliedJob job = new AppliedJob(
                        String.valueOf(applicant.getApplicantionId()),
                        applicant.getJobTitle(),
                        applicant.getCompany(),
                        applicant.getLocation(),
                        applicant.getSubmissionDate(),
                        applicant.getApplyMethod(),
                        "This employer typically responds within 1 day",
                        true,
                        applicant.getStatus()
                );

                appliedJobsList.add(job);
            }

            adapter.notifyDataSetChanged();
        });
    }
    // ==============================
    // Adapter Callbacks
    // ==============================

    @Override
    public void onStatusUpdated(AppliedJob job, String newStatus) {
        Toast.makeText(getContext(),
                "Status updated to: " + newStatus,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onJobArchived(AppliedJob job) {
        appliedJobsList.remove(job);
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(),
                "Job archived",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onApplicationWithdrawn(AppliedJob job) {
        appliedJobsList.remove(job);
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(),
                "Application withdrawn",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewDetails(AppliedJob job) {
        Toast.makeText(getContext(),
                "Viewing: " + job.getJobTitle(),
                Toast.LENGTH_SHORT).show();
    }
}