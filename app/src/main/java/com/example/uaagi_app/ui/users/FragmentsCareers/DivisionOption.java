package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.Services.JobFetchService;
import com.example.uaagi_app.network.dto.JobEnums.Company;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.ArrayList;
import java.util.List;

public class DivisionOption extends Fragment {
    private RecyclerView divisionRecyclerView;
    private String brandName;
    private GenericRecyclerAdapter<JobFetchResponse> adapter;
    private FrameLayout loadingContainer, errorContainer;
    private static final String ARG_COMPANY = "company";
    public DivisionOption() {
    }

    public static DivisionOption newInstance(Company company) {
        DivisionOption fragment = new DivisionOption();
        Bundle args = new Bundle();
        args.putString(ARG_COMPANY, company.name());
        fragment.setArguments(args);
        return fragment;
    }
    private void setupUiStates(View view){
        divisionRecyclerView = view.findViewById(R.id.division_container);
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
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
        loadingContainer.setVisibility(View.GONE);
        divisionRecyclerView.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.error_container,
                FragmentError.newInstance(message)
        );
    }
    private void fetchJobs() {
        showLoading();
        JobFetchService service = new JobFetchService(requireContext());
        service.fetchJobsForUser(new JobFetchService.JobFetchCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> response) {
                showContent(response);
            }
            @Override
            public void onError(String errorMessage) {
                showError(errorMessage);
            }
        });
    }
    private void showContent(List<JobFetchResponse> jobs) {
        loadingContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        divisionRecyclerView.setVisibility(View.VISIBLE);
        List<JobFetchResponse> filteredJobs = new ArrayList<>();
        for (JobFetchResponse job : jobs) {
            if (job.getCompany() != null && (brandName == null || brandName.equals(job.getCompany().getDisplayName()))) {
                filteredJobs.add(job);
            }
        }

        adapter = new GenericRecyclerAdapter<>(
                filteredJobs,
                R.layout.item_division_card,
                (itemView, job, position) -> {
                    TextView tvDivision = itemView.findViewById(R.id.divisionTitle);
                    tvDivision.setText(job.getDepartment());

                    itemView.setOnClickListener(v -> {
                        JobsOption fragment = JobsOption.newInstance(job.getCompany(), job.getDepartment());
                        UiHelpers.switchFragment(getParentFragmentManager(), fragment);
                    });
                }
        );

        divisionRecyclerView.setAdapter(adapter);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_careers_division, container, false);
        setupUiStates(view);
        divisionRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        if (getArguments() != null) {
            Company selectedCompany = Company.valueOf(getArguments().getString(ARG_COMPANY));
            brandName = selectedCompany.getDisplayName();
        }
        fetchJobs();
        return view;
    }
}