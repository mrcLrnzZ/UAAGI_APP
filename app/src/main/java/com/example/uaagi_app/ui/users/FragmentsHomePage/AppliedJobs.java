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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.adapter.AppliedJobsAdapter;
import com.example.uaagi_app.adapter.ArchivedJobsAdapter;
import com.example.uaagi_app.adapter.InterviewJobsAdapter;
import com.example.uaagi_app.adapter.SavedJobsAdapter;
import com.example.uaagi_app.model.AppliedJob;

import java.util.ArrayList;
import java.util.List;
public class AppliedJobs extends Fragment implements AppliedJobsAdapter.OnJobActionListener {

    private LinearLayout tabSaved, tabApplied, tabInterviews, tabArchived;
    private TextView tvSaved, tvApplied, tvInterviews, tvArchived;
    private TextView tvSavedCount, tvAppliedCount, tvInterviewsCount;
    private TextView tvSectionLabel, tvEmptyMessage;
    private LinearLayout layoutEmptyState;
    private RecyclerView rvJobs;

    private List<AppliedJob> savedJobs = new ArrayList<>();
    private List<AppliedJob> appliedJobs = new ArrayList<>();
    private List<AppliedJob> interviewJobs = new ArrayList<>();
    private List<AppliedJob> archivedJobs = new ArrayList<>();

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigate_applied_jobs, container, false);

        initViews(view);
        loadSampleData();
        setupTabs();
        selectTab("applied"); // default

        return view;
    }

    private void initViews(View view) {
        tabSaved = view.findViewById(R.id.tabSaved);
        tabApplied = view.findViewById(R.id.tabApplied);
        tabInterviews = view.findViewById(R.id.tabInterviews);
        tabArchived = view.findViewById(R.id.tabArchived);

        tvSaved = view.findViewById(R.id.tvSaved);
        tvApplied = view.findViewById(R.id.tvApplied);
        tvInterviews = view.findViewById(R.id.tvInterviews);
        tvArchived = view.findViewById(R.id.tvArchived);

        tvSavedCount = view.findViewById(R.id.tvSavedCount);
        tvAppliedCount = view.findViewById(R.id.tvAppliedCount);
        tvInterviewsCount = view.findViewById(R.id.tvInterviewsCount);

        rvJobs = view.findViewById(R.id.rvJobs);
        tvSectionLabel = view.findViewById(R.id.tvSectionLabel);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        tvEmptyMessage = view.findViewById(R.id.tvEmptyMessage);

        rvJobs.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void setupTabs() {
        tabSaved.setOnClickListener(v -> selectTab("saved"));
        tabApplied.setOnClickListener(v -> selectTab("applied"));
        tabInterviews.setOnClickListener(v -> selectTab("interviews"));
        tabArchived.setOnClickListener(v -> selectTab("archived"));
    }

    private void selectTab(String tab) {
        resetTab(tabSaved, tvSaved, tvSavedCount);
        resetTab(tabApplied, tvApplied, tvAppliedCount);
        resetTab(tabInterviews, tvInterviews, tvInterviewsCount);
        resetTab(tabArchived, tvArchived, null);

        switch (tab) {
            case "saved":
                highlightTab(tabSaved, tvSaved, tvSavedCount);
                showList(savedJobs, "saved");
                break;
            case "applied":
                highlightTab(tabApplied, tvApplied, tvAppliedCount);
                showList(appliedJobs, "applied");
                break;
            case "interviews":
                highlightTab(tabInterviews, tvInterviews, tvInterviewsCount);
                showList(interviewJobs, "interviews");
                break;
            case "archived":
                highlightTab(tabArchived, tvArchived, null);
                showList(archivedJobs, "archived");
                break;
        }
    }

    private void showList(List<AppliedJob> jobs, String type) {
        if (jobs == null || jobs.isEmpty()) {
            rvJobs.setVisibility(View.GONE);
            tvSectionLabel.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
            tvEmptyMessage.setText(getEmptyMessage(type));
            return;
        }

        layoutEmptyState.setVisibility(View.GONE);
        rvJobs.setVisibility(View.VISIBLE);
        tvSectionLabel.setVisibility(type.equals("applied") ? View.VISIBLE : View.GONE);

        switch (type) {
            case "saved":
                rvJobs.setAdapter(new SavedJobsAdapter(requireContext(), jobs,
                        job -> {
                            savedJobs.remove(job);
                            showList(savedJobs, "saved");
                            updateCounts();
                        }));
                break;
            case "applied":
                rvJobs.setAdapter(new AppliedJobsAdapter(requireContext(), jobs, this));
                break;
            case "interviews":
                rvJobs.setAdapter(new InterviewJobsAdapter(requireContext(), jobs));
                break;
            case "archived":
                rvJobs.setAdapter(new ArchivedJobsAdapter(requireContext(), jobs));
                break;
        }
    }

    private String getEmptyMessage(String type) {
        switch (type) {
            case "saved": return "No saved jobs yet";
            case "applied": return "No applied jobs yet";
            case "interviews": return "No interviews scheduled";
            case "archived": return "No archived jobs";
            default: return "Nothing here yet";
        }
    }

    private void loadSampleData() {
        appliedJobs.add(new AppliedJob("1", "IT Intern - Remote",
                "Tiger Global", "Madrigal Business Park",
                "Jan 23", "Indeed",
                "Typically responds within 1 day", true, "applied"));

        savedJobs.add(new AppliedJob("2", "Marketing Associate",
                "Acme Corp", "Makati",
                "Jan 20", "LinkedIn",
                "", false, "saved"));

        interviewJobs.add(new AppliedJob("3", "HR Assistant",
                "StartupPH", "BGC",
                "Jan 15", "Kalibrr",
                "", false, "interviews"));

        archivedJobs.add(new AppliedJob("4", "Finance Analyst",
                "BDO", "Ortigas",
                "Dec 10", "Indeed",
                "", false, "archived"));

        updateCounts();
    }

    private void updateCounts() {
        tvSavedCount.setText(String.valueOf(savedJobs.size()));
        tvAppliedCount.setText(String.valueOf(appliedJobs.size()));
        tvInterviewsCount.setText(String.valueOf(interviewJobs.size()));
    }

    private void resetTab(LinearLayout tab, TextView tv, TextView count) {
        tab.setBackgroundResource(R.drawable.tab_unselected_background);
        tv.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray));
        if (count != null) count.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray));
    }

    private void highlightTab(LinearLayout tab, TextView tv, TextView count) {
        tab.setBackgroundResource(R.drawable.tab_selected_background);
        tv.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
        if (count != null) {
            count.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
            count.setBackgroundResource(R.drawable.count_badge_background);
        }
    }

    // AppliedJobsAdapter callbacks
    @Override public void onStatusUpdated(AppliedJob job, String newStatus) {}

    @Override
    public void onJobArchived(AppliedJob job) {
        appliedJobs.remove(job);
        archivedJobs.add(job);
        showList(appliedJobs, "applied");
        updateCounts();
    }

    @Override
    public void onApplicationWithdrawn(AppliedJob job) {
        appliedJobs.remove(job);
        showList(appliedJobs, "applied");
        updateCounts();
    }

    @Override
    public void onViewDetails(AppliedJob job) {
        Toast.makeText(getContext(), "Viewing: " + job.getJobTitle(), Toast.LENGTH_SHORT).show();
    }
}