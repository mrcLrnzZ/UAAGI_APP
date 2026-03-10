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

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.FragmentsAppliedJobs.SectionApplied;
import com.example.uaagi_app.ui.users.FragmentsAppliedJobs.SectionArchived;
import com.example.uaagi_app.ui.users.FragmentsAppliedJobs.SectionInterview;
import com.example.uaagi_app.ui.users.FragmentsAppliedJobs.SectionSaved;

import java.util.ArrayList;
import java.util.List;

public class AppliedJobs extends Fragment {

    // Views
    private LinearLayout tabSaved, tabApplied, tabInterviews, tabArchived;
    private TextView tvSaved, tvApplied, tvInterviews, tvArchived;
    private TextView tvSavedCount, tvAppliedCount, tvInterviewsCount;
    private List<JobFetchResponse> savedJobs = new ArrayList<>();
    private List<JobFetchResponse> archivedJobs = new ArrayList<>();

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
        //logFragmentStack(getChildFragmentManager());
        return view;
    }
    private void loadInterviewJobs() {
        Toast.makeText(getContext(), "Interview jobs", Toast.LENGTH_SHORT).show();
    }
    private void loadAppliedJobs() {
        Toast.makeText(getContext(), "Applied jobs", Toast.LENGTH_SHORT).show();
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
                loadFragment(new SectionSaved(), "saved");
                break;
            case "applied":
                highlightTab(tabApplied, tvApplied, tvAppliedCount);
                loadFragment(new SectionApplied(), "applied");
                loadAppliedJobs();
                break;
            case "interviews":
                highlightTab(tabInterviews, tvInterviews, tvInterviewsCount);
                loadFragment(new SectionInterview(), "interviews");
                loadInterviewJobs();
                break;
            case "archived":
                highlightTab(tabArchived, tvArchived, null);
                loadFragment(new SectionArchived(), "archived");
                break;
        }
    }
    private void loadFragment(Fragment fragment, String tag) {

        FragmentManager fm = getChildFragmentManager();
        Fragment existingFragment = fm.findFragmentByTag(tag);

        if (existingFragment != null) {
            return;
        }

        fm.beginTransaction()
                .replace(R.id.fragmentContainer, fragment, tag)
                .commit();
    }

}