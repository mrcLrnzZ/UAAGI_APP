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

import java.util.List;

public class AppliedJobs extends Fragment {

    // Views
    private LinearLayout tabSaved, tabApplied, tabInterviews, tabArchived;
    private TextView tvSaved, tvApplied, tvInterviews, tvArchived;
    private TextView tvSavedCount, tvAppliedCount, tvInterviewsCount, tvArchivedCount;
    
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
            updateCount(tvSavedCount, savedJobs != null ? savedJobs.size() : 0);
        });

        jobViewModel.getArchivedJobs().observe(getViewLifecycleOwner(), archivedJobs -> {
            updateCount(tvArchivedCount, archivedJobs != null ? archivedJobs.size() : 0);
        });

        applicantViewModel.getApplicants().observe(getViewLifecycleOwner(), applicants -> {
            updateCount(tvAppliedCount, applicants != null ? applicants.size() : 0);
            
            int interviewCount = 0;
            if (applicants != null) {
                for (Applicant a : applicants) {
                    if (a.getInterviewStatus() != null && a.getInterviewStatus().equalsIgnoreCase("Scheduled")) {
                        interviewCount++;
                    }
                }
            }
            updateCount(tvInterviewsCount, interviewCount);
        });
    }

    private void updateCount(TextView countView, int count) {
        if (count > 0) {
            countView.setText(String.valueOf(count));
            countView.setVisibility(View.VISIBLE);
        } else {
            countView.setVisibility(View.GONE);
        }
    }

    private void resetTabStyles(LinearLayout tab, TextView textView, TextView countView) {
        tab.setBackgroundResource(R.drawable.tab_unselected_background);
        textView.setTextColor(getResources().getColor(android.R.color.darker_gray));
        if (countView != null) {
            countView.setTextColor(getResources().getColor(android.R.color.darker_gray));
            countView.setBackgroundResource(R.drawable.count_badge_background);
        }
    }

    private void highlightTab(LinearLayout tab, TextView textView, TextView countView) {
        tab.setBackgroundResource(R.drawable.tab_selected_background);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        if (countView != null) {
            countView.setTextColor(getResources().getColor(android.R.color.black));
            countView.setBackgroundResource(R.drawable.circle_white);
        }
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
        tvArchivedCount = view.findViewById(R.id.tvArchivedCount);
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
        resetTabStyles(tabArchived, tvArchived, tvArchivedCount);

        // Highlight selected tab
        switch (tab) {
            case "saved":
                highlightTab(tabSaved, tvSaved, tvSavedCount);
                loadFragment(new SectionSaved(), "saved");
                break;
            case "applied":
                highlightTab(tabApplied, tvApplied, tvAppliedCount);
                loadFragment(new SectionApplied(), "applied");
                break;
            case "interviews":
                highlightTab(tabInterviews, tvInterviews, tvInterviewsCount);
                loadFragment(new SectionInterview(), "interviews");
                break;
            case "archived":
                highlightTab(tabArchived, tvArchived, tvArchivedCount);
                loadFragment(new SectionArchived(), "archived");
                break;
        }
    }

    private void loadFragment(Fragment fragment, String tag) {
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragmentContainer, fragment, tag)
                .commit();
    }
}
