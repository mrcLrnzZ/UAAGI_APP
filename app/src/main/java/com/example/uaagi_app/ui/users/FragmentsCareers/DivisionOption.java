package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.JobViewModel;
import com.example.uaagi_app.network.dto.JobEnums.Company;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DivisionOption extends Fragment implements FragmentError.RetryListener {
    private RecyclerView divisionRecyclerView;
    private String companyName;
    private GenericRecyclerAdapter<JobFetchResponse> adapter;
    private FrameLayout loadingContainer, errorContainer;
    private EditText searchEditText;
    private JobViewModel jobViewModel;
    private static final String ARG_COMPANY = "company";
    private static final String ARG_IS_INTERN = "isIntern";
    private boolean isIntern;

    public DivisionOption() {
    }

    public static DivisionOption newInstance(Company company, boolean isIntern) {
        DivisionOption fragment = new DivisionOption();
        Bundle args = new Bundle();
        args.putString(ARG_COMPANY, company.getDisplayName());
        args.putBoolean(ARG_IS_INTERN, isIntern);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_careers_division, container, false);
        setupUiStates(view);

        if (getArguments() != null) {
            String companyDisplayName = getArguments().getString(ARG_COMPANY);
            Company selectedCompany = Company.fromString(companyDisplayName);
            isIntern = getArguments().getBoolean(ARG_IS_INTERN);
            companyName = selectedCompany != null ? selectedCompany.getDisplayName() : companyDisplayName;
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
    }

    private void setupUiStates(View view) {
        divisionRecyclerView = view.findViewById(R.id.division_container);
        divisionRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
        searchEditText = view.findViewById(R.id.search_division);
        setupSearchFunctionality();
    }

    private void setupSearchFunctionality() {
        SimpleTextWatcher.bindTextWatcher(searchEditText,
                new SimpleTextWatcher(query -> {
                    if (jobViewModel != null) {
                        filterLocalList(query);
                    }
                })
        );
    }

    private void filterLocalList(String query) {
        if (jobViewModel.getJobList().getValue() == null || adapter == null) return;

        List<JobFetchResponse> allJobs = jobViewModel.getJobList().getValue();
        List<JobFetchResponse> filteredList = new ArrayList<>();

        if (query.isEmpty()) {
            filteredList.addAll(getUniqueDivisions(allJobs));
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (JobFetchResponse job : allJobs) {
                if (job.getDepartment() != null &&
                        job.getDepartment().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(job);
                }
            }
            filteredList = getUniqueDivisions(filteredList);
        }
        adapter.updateList(filteredList);
    }

    private List<JobFetchResponse> getUniqueDivisions(List<JobFetchResponse> jobs) {
        List<JobFetchResponse> divisionList = new ArrayList<>();
        Set<String> addedDivisions = new HashSet<>();
        if (jobs != null) {
            for (JobFetchResponse job : jobs) {
                String division = job.getDepartment();
                if (division != null && !addedDivisions.contains(division)) {
                    addedDivisions.add(division);
                    divisionList.add(job);
                }
            }
        }
        return divisionList;
    }

    private void showLoading() {
        loadingContainer.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
        divisionRecyclerView.setVisibility(View.GONE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.loading_container,
                FragmentLoading.newInstance()
        );
    }

    private void showError(String message) {
        if (message == null) return;
        loadingContainer.setVisibility(View.GONE);
        divisionRecyclerView.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.error_container,
                FragmentError.newInstance(message)
        );
    }

    private void setupViewModel() {
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);

        jobViewModel.getLoadingState().removeObservers(getViewLifecycleOwner());
        jobViewModel.getErrorMessage().removeObservers(getViewLifecycleOwner());
        jobViewModel.getJobList().removeObservers(getViewLifecycleOwner());

        jobViewModel.getLoadingState().observe(getViewLifecycleOwner(), isLoading -> {
            if (Boolean.TRUE.equals(isLoading)) {
                showLoading();
            }
        });

        jobViewModel.getErrorMessage().observe(getViewLifecycleOwner(), this::showError);
        jobViewModel.getJobList().observe(getViewLifecycleOwner(), this::showContent);

        jobViewModel.setFilters(companyName, null, isIntern);

        List<JobFetchResponse> currentJobs = jobViewModel.getJobList().getValue();
        if (currentJobs == null || currentJobs.isEmpty()) {
            jobViewModel.fetchJobForUser(requireContext());
        } else {
            showContent(currentJobs);
        }
    }

    private void showContent(List<JobFetchResponse> jobs) {
        if (jobs == null) return;

        List<JobFetchResponse> divisionList = getUniqueDivisions(jobs);

        if (divisionList.isEmpty()) {
            Boolean isLoading = jobViewModel.getLoadingState().getValue();
            String error = jobViewModel.getErrorMessage().getValue();

            if (Boolean.FALSE.equals(isLoading) && error == null) {
                showError("No divisions found for " + companyName);
            }
            return;
        }

        loadingContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        divisionRecyclerView.setVisibility(View.VISIBLE);

        if (adapter == null) {
            adapter = new GenericRecyclerAdapter<>(
                    divisionList,
                    R.layout.item_division_card,
                    (itemView, job, position) -> {
                        TextView tvDivision = itemView.findViewById(R.id.divisionTitle);
                        tvDivision.setText(job.getDepartment());

                        itemView.setOnClickListener(v -> {
                            Company companyEnum = Company.fromString(job.getCompany().getDisplayName());
                            if (companyEnum != null) {
                                JobsOption fragment = JobsOption.newInstance(
                                        companyEnum,
                                        job.getDepartment(),
                                        isIntern
                                );
                                UiHelpers.switchFragment(getParentFragmentManager(), fragment);
                            }
                        });
                    }
            );
        } else {
            adapter.updateList(divisionList);
        }

        if (divisionRecyclerView.getAdapter() == null) {
            divisionRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onRetry() {
        if (jobViewModel != null) {
            jobViewModel.fetchJobForUser(requireContext());
        }
    }
}
