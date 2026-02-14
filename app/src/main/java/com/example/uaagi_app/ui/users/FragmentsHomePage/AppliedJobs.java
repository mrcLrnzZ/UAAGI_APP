package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.adapter.AppliedJobsAdapter;
import com.example.uaagi_app.model.AppliedJob;

import java.util.ArrayList;
import java.util.List;

public class AppliedJobs extends Fragment implements AppliedJobsAdapter.OnJobActionListener {

    // Views
    private LinearLayout tabSaved, tabApplied, tabInterviews, tabArchived;
    private TextView tvSaved, tvApplied, tvInterviews, tvArchived;
    private TextView tvSavedCount, tvAppliedCount, tvInterviewsCount;
    private RecyclerView rvAppliedJobs, rvOlderJobs;
    private TextView tvOlder;

    // Data
    private List<AppliedJob> appliedJobsList;
    private List<AppliedJob> olderJobsList;
    private AppliedJobsAdapter appliedJobsAdapter;
    private AppliedJobsAdapter olderJobsAdapter;

    // Current selected tab
    private String currentTab = "applied";

    public AppliedJobs() {
        // Required empty public constructor
    }

    public static AppliedJobs newInstance() {
        return new AppliedJobs();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigate_applied_jobs, container, false);

        // Initialize views
        initializeViews(view);

        // Setup tabs
        setupTabs();

        // Load sample data
        loadSampleData();

        // Setup RecyclerViews
        setupRecyclerViews();

        return view;
    }

    private void initializeViews(View view) {
        // Tabs
        tabSaved = view.findViewById(R.id.tabSaved);
        tabApplied = view.findViewById(R.id.tabApplied);
        tabInterviews = view.findViewById(R.id.tabInterviews);
        tabArchived = view.findViewById(R.id.tabArchived);

        // Tab TextViews
        tvSaved = view.findViewById(R.id.tvSaved);
        tvApplied = view.findViewById(R.id.tvApplied);
        tvInterviews = view.findViewById(R.id.tvInterviews);
        tvArchived = view.findViewById(R.id.tvArchived);

        // Tab Counts
        tvSavedCount = view.findViewById(R.id.tvSavedCount);
        tvAppliedCount = view.findViewById(R.id.tvAppliedCount);
        tvInterviewsCount = view.findViewById(R.id.tvInterviewsCount);

        // RecyclerViews
        rvAppliedJobs = view.findViewById(R.id.rvAppliedJobs);
        rvOlderJobs = view.findViewById(R.id.rvOlderJobs);
        tvOlder = view.findViewById(R.id.tvOlder);
    }

    private void setupTabs() {
        tabSaved.setOnClickListener(v -> selectTab("saved"));
        tabApplied.setOnClickListener(v -> selectTab("applied"));
        tabInterviews.setOnClickListener(v -> selectTab("interviews"));
        tabArchived.setOnClickListener(v -> selectTab("archived"));

        // Set Applied as default selected
        selectTab("applied");
    }

    private void selectTab(String tab) {
        currentTab = tab;

        // Reset all tabs to unselected state
        resetTabStyles(tabSaved, tvSaved, tvSavedCount);
        resetTabStyles(tabApplied, tvApplied, tvAppliedCount);
        resetTabStyles(tabInterviews, tvInterviews, tvInterviewsCount);
        resetTabStyles(tabArchived, tvArchived, null);

        // Highlight selected tab
        switch (tab) {
            case "saved":
                highlightTab(tabSaved, tvSaved, tvSavedCount);
                // Load saved jobs
                Toast.makeText(getContext(), "Saved jobs", Toast.LENGTH_SHORT).show();
                break;
            case "applied":
                highlightTab(tabApplied, tvApplied, tvAppliedCount);
                // Already showing applied jobs
                break;
            case "interviews":
                highlightTab(tabInterviews, tvInterviews, tvInterviewsCount);
                // Load interview jobs
                Toast.makeText(getContext(), "Interview jobs", Toast.LENGTH_SHORT).show();
                break;
            case "archived":
                highlightTab(tabArchived, tvArchived, null);
                // Load archived jobs
                Toast.makeText(getContext(), "Archived jobs", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void resetTabStyles(LinearLayout tab, TextView textView, TextView countView) {
        tab.setBackgroundResource(R.drawable.tab_unselected_background);
        textView.setTextColor(getResources().getColor(android.R.color.darker_gray));
        if (countView != null) {
            countView.setTextColor(getResources().getColor(android.R.color.darker_gray));
        }
    }

    private void highlightTab(LinearLayout tab, TextView textView, TextView countView) {
        tab.setBackgroundResource(R.drawable.tab_selected_background);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        if (countView != null) {
            countView.setTextColor(getResources().getColor(android.R.color.white));
            countView.setBackgroundResource(R.drawable.count_badge_background);
        }
    }

    private void loadSampleData() {
        appliedJobsList = new ArrayList<>();
        olderJobsList = new ArrayList<>();

        // Sample job data (Past 14 days)
        AppliedJob job1 = new AppliedJob(
                "1",
                "Intern - IT, Marketing, Finance, Admin and HR (Feb-March Start - Remote)",
                "Tiger Global Business Services Inc.",
                "Madrigal Business Park",
                "Jan 23",
                "Indeed",
                "This employer typically responds within 1 day",
                true,
                "applied"
        );

        appliedJobsList.add(job1);

        // Sample older job data
        AppliedJob job2 = new AppliedJob(
                "2",
                "Intern - IT, Marketing, Finance, Admin and HR (Feb-March Start - Remote)",
                "Tiger Global Business Services Inc.",
                "Madrigal Business Park",
                "Jan 10",
                "Indeed",
                "This employer typically responds within 2 days",
                true,
                "applied"
        );

        olderJobsList.add(job2);

        // Update counts
        tvSavedCount.setText("0");
        tvAppliedCount.setText(String.valueOf(appliedJobsList.size()));
        tvInterviewsCount.setText("0");
    }

    private void setupRecyclerViews() {
        // Setup Applied Jobs RecyclerView
        rvAppliedJobs.setLayoutManager(new LinearLayoutManager(getContext()));
        appliedJobsAdapter = new AppliedJobsAdapter(getContext(), appliedJobsList, this);
        rvAppliedJobs.setAdapter(appliedJobsAdapter);

        // Setup Older Jobs RecyclerView
        if (!olderJobsList.isEmpty()) {
            tvOlder.setVisibility(View.VISIBLE);
            rvOlderJobs.setVisibility(View.VISIBLE);
            rvOlderJobs.setLayoutManager(new LinearLayoutManager(getContext()));
            olderJobsAdapter = new AppliedJobsAdapter(getContext(), olderJobsList, this);
            rvOlderJobs.setAdapter(olderJobsAdapter);
        }
    }

    // Adapter callback methods
    @Override
    public void onStatusUpdated(AppliedJob job, String newStatus) {
        // Handle status update - could move job to different tab based on status
        switch (newStatus) {
            case "interviewing":
                // Move to interviews tab
                break;
            case "hired":
            case "not_selected":
            case "no_longer_interested":
                // Could move to archived or remove from list
                break;
        }
    }

    @Override
    public void onJobArchived(AppliedJob job) {
        // Remove from current list and move to archived
        appliedJobsList.remove(job);
        olderJobsList.remove(job);
        appliedJobsAdapter.notifyDataSetChanged();
        if (olderJobsAdapter != null) {
            olderJobsAdapter.notifyDataSetChanged();
        }
        updateCounts();
    }

    @Override
    public void onApplicationWithdrawn(AppliedJob job) {
        // Remove from list
        appliedJobsList.remove(job);
        olderJobsList.remove(job);
        appliedJobsAdapter.notifyDataSetChanged();
        if (olderJobsAdapter != null) {
            olderJobsAdapter.notifyDataSetChanged();
        }
        updateCounts();
    }

    @Override
    public void onViewDetails(AppliedJob job) {
        // Navigate to job details screen
        Toast.makeText(getContext(), "Viewing details: " + job.getJobTitle(), Toast.LENGTH_SHORT).show();
        // Add navigation logic here
    }

    private void updateCounts() {
        tvAppliedCount.setText(String.valueOf(appliedJobsList.size() + olderJobsList.size()));
    }
}