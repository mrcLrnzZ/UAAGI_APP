package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.ApplicantViewModel;
import com.example.uaagi_app.data.viewmodel.JobViewModel;
import com.example.uaagi_app.network.dto.Applicant;
import com.example.uaagi_app.ui.users.FragmentsAppliedJobs.SectionApplied;
import com.example.uaagi_app.ui.users.FragmentsAppliedJobs.SectionArchived;
import com.example.uaagi_app.ui.users.FragmentsAppliedJobs.SectionInterview;
import com.example.uaagi_app.ui.users.FragmentsAppliedJobs.SectionSaved;
import com.example.uaagi_app.utils.SessionManager;

public class AppliedJobs extends Fragment {

    // Views for Tabs
    private LinearLayout tabSaved, tabApplied, tabInterviews, tabArchived;
    private TextView tvSaved, tvApplied, tvInterviews, tvArchived;

    // Views for Stats
    private TextView tvAppliedStatCount, tvInterviewStatCount, tvSavedStatCount, tvArchivedStatCount;

    private JobViewModel jobViewModel;
    private ApplicantViewModel applicantViewModel;

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
        jobViewModel = new ViewModelProvider(requireActivity()).get(JobViewModel.class);
        applicantViewModel = new ViewModelProvider(requireActivity()).get(ApplicantViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigate_applied_jobs, container, false);

        // Initialize views
        initializeViews(view);

        // Setup tabs
        setupTabs();

        observeCounts();
        fetchData();

        return view;
    }

    private void fetchData() {
        int userId = SessionManager.getInstance(requireContext()).getUserId();
        jobViewModel.fetchSavedJobs(userId, requireContext());
        jobViewModel.fetchArchivedJobs(userId, requireContext());
        applicantViewModel.fetchApplicantsForUser(userId, requireContext());
    }

    private void observeCounts() {
        jobViewModel.getSavedJobs().observe(getViewLifecycleOwner(), savedJobs -> {
            int count = savedJobs != null ? savedJobs.size() : 0;
            if (tvSavedStatCount != null) tvSavedStatCount.setText(String.valueOf(count));
        });

        jobViewModel.getArchivedJobs().observe(getViewLifecycleOwner(), archivedJobs -> {
            int count = archivedJobs != null ? archivedJobs.size() : 0;
            if (tvArchivedStatCount != null) tvArchivedStatCount.setText(String.valueOf(count));
        });

        applicantViewModel.getApplicants().observe(getViewLifecycleOwner(), applicants -> {
            int interviewCount = 0;
            int appliedOnlyCount = 0;

            if (applicants != null) {
                for (Applicant a : applicants) {
                    String status = a.getStatus();
                    String interviewStatus = a.getInterviewStatus();

                    // Only count as "Interview" if explicitly scheduled
                    if (interviewStatus != null && !interviewStatus.equalsIgnoreCase("Pending")) {
                        interviewCount++;
                    }
                    // Standardized logic: Count as "Applied" if status is "Applied" or "Interviewing"
                    // but it is NOT yet scheduled for an interview.
                    else if (status != null && (status.equalsIgnoreCase("Applied") || status.equalsIgnoreCase("Interviewing"))) {
                        appliedOnlyCount++;
                    }
                }
            }

            if (tvAppliedStatCount != null) tvAppliedStatCount.setText(String.valueOf(appliedOnlyCount));
            if (tvInterviewStatCount != null) tvInterviewStatCount.setText(String.valueOf(interviewCount));
        });
    }

    private void resetTabStyles(LinearLayout tab, TextView textView) {
        if (tab == null || textView == null) return;
        tab.setBackgroundResource(R.drawable.tab_unselected_background);
        textView.setTextColor(getResources().getColor(android.R.color.darker_gray));
    }

    private void highlightTab(LinearLayout tab, TextView textView) {
        if (tab == null || textView == null) return;
        tab.setBackgroundResource(R.drawable.tab_selected_background);
        textView.setTextColor(getResources().getColor(android.R.color.white));
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

        // Stats Counts (Hero Banner)
        tvAppliedStatCount = view.findViewById(R.id.tvAppliedStatCount);
        tvInterviewStatCount = view.findViewById(R.id.tvInterviewStatCount);
        tvSavedStatCount = view.findViewById(R.id.tvSavedStatCount);
        tvArchivedStatCount = view.findViewById(R.id.tvArchivedStatCount);
    }

    private void setupTabs() {
        if (tabSaved != null) tabSaved.setOnClickListener(v -> selectTab("saved"));
        if (tabApplied != null) tabApplied.setOnClickListener(v -> selectTab("applied"));
        if (tabInterviews != null) tabInterviews.setOnClickListener(v -> selectTab("interviews"));
        if (tabArchived != null) tabArchived.setOnClickListener(v -> selectTab("archived"));

        // Set Applied as default selected
        selectTab("applied");
    }

    private void selectTab(String tab) {
        currentTab = tab;

        // Reset all tabs to unselected state
        resetTabStyles(tabSaved, tvSaved);
        resetTabStyles(tabApplied, tvApplied);
        resetTabStyles(tabInterviews, tvInterviews);
        resetTabStyles(tabArchived, tvArchived);

        // Highlight selected tab
        switch (tab) {
            case "saved":
                highlightTab(tabSaved, tvSaved);
                loadFragment(new SectionSaved(), "saved");
                break;
            case "applied":
                highlightTab(tabApplied, tvApplied);
                loadFragment(new SectionApplied(), "applied");
                break;
            case "interviews":
                highlightTab(tabInterviews, tvInterviews);
                loadFragment(new SectionInterview(), "interviews");
                break;
            case "archived":
                highlightTab(tabArchived, tvArchived);
                loadFragment(new SectionArchived(), "archived");
                break;
        }
    }

    private void loadFragment(Fragment fragment, String tag) {
        if (isAdded()) {
            FragmentManager fm = getChildFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment, tag)
                    .commit();
        }
    }
}
