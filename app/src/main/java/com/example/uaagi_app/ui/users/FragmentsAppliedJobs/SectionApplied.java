package com.example.uaagi_app.ui.users.FragmentsAppliedJobs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.adapter.AppliedJobsAdapter;
import com.example.uaagi_app.data.model.AppliedJob;

import java.util.ArrayList;
import java.util.List;

public class SectionApplied extends Fragment implements AppliedJobsAdapter.OnJobActionListener {

    private RecyclerView rvAppliedJobs;
    private AppliedJobsAdapter adapter;
    private List<AppliedJob> appliedJobsList;

    public SectionApplied() {
        // Required empty public constructor
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
        loadSampleData();

        return view;
    }

    private void setupRecyclerView() {
        appliedJobsList = new ArrayList<>();

        rvAppliedJobs.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AppliedJobsAdapter(getContext(), appliedJobsList, this);
        rvAppliedJobs.setAdapter(adapter);
    }

    private void loadSampleData() {

        AppliedJob job1 = new AppliedJob(
                "1",
                "Intern - IT, Marketing, Finance, Admin and HR",
                "Tiger Global Business Services Inc.",
                "Madrigal Business Park",
                "Jan 23",
                "Indeed",
                "This employer typically responds within 1 day",
                true,
                "applied"
        );

        AppliedJob job2 = new AppliedJob(
                "2",
                "Software Engineer Intern",
                "Tech Company PH",
                "Makati City",
                "Jan 10",
                "LinkedIn",
                "This employer typically responds within 2 days",
                false,
                "applied"
        );

        appliedJobsList.add(job1);
        appliedJobsList.add(job2);

        adapter.notifyDataSetChanged();
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