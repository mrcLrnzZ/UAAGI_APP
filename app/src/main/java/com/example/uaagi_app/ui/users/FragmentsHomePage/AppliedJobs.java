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
            tvSavedStatCount.setText(String.valueOf(count));
        });

        jobViewModel.getArchivedJobs().observe(getViewLifecycleOwner(), archivedJobs -> {
            int count = archivedJobs != null ? archivedJobs.size() : 0;
            tvArchivedStatCount.setText(String.valueOf(count));
        });

        applicantViewModel.getApplicants().observe(getViewLifecycleOwner(), applicants -> {
            int appliedCount = 0;
            int interviewCount = 0;

            if (applicants != null) {
                for (Applicant a : applicants){
                    if (a.getInterviewStatus() != null && a.getInterviewStatus().equalsIgnoreCase("Pending")){
                        appliedCount++;
                    }
                }
                for (Applicant a : applicants) {
                    if (a.getInterviewStatus() != null && a.getInterviewStatus().equalsIgnoreCase("Scheduled")) {
                        interviewCount++;
                    }
                }
            }
            tvAppliedStatCount.setText(String.valueOf(appliedCount));
            tvInterviewStatCount.setText(String.valueOf(interviewCount));
        });
    }

    private void resetTabStyles(LinearLayout tab, TextView textView) {
        tab.setBackgroundResource(R.drawable.tab_unselected_background);
        textView.setTextColor(getResources().getColor(android.R.color.darker_gray));
    }

    private void highlightTab(LinearLayout tab, TextView textView) {
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
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragmentContainer, fragment, tag)
                .commit();
    }
}
