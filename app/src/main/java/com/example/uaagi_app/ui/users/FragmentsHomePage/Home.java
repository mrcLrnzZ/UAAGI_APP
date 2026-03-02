package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.Services.JobFetchService;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.users.FragmentsCareers.JobDesc;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.List;

public class Home extends Fragment implements FragmentError.RetryListener {

    private RecyclerView jobRecyclerView;
    private View loadingContainer;
    private View errorContainer;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_navigate_home, container, false);
        setupUiStates(view);
        jobRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fetchJobs();
        return view;
    }
    private void setupUiStates(View view){
        jobRecyclerView = view.findViewById(R.id.job_container);
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
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

    private void showLoading() {
        loadingContainer.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
        jobRecyclerView.setVisibility(View.GONE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.loading_container,
                FragmentLoading.newInstance()
        );
    }
    private void showContent(List<JobFetchResponse> jobs) {
        loadingContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        jobRecyclerView.setVisibility(View.VISIBLE);

        GenericRecyclerAdapter<JobFetchResponse> adapter =
                new GenericRecyclerAdapter<>(
                        jobs,
                        R.layout.item_job_card,
                        (view, job, position) -> {

                            TextView tvTitle = view.findViewById(R.id.tvJobTitle);
                            TextView tvLocation = view.findViewById(R.id.tvLocation);
                            TextView tvCompany = view.findViewById(R.id.tvCompany);
                            TextView tvSalary = view.findViewById(R.id.tvSalary);
                            TextView tvJobType = view.findViewById(R.id.tvJobType);
                            TextView tvExperienceLevel = view.findViewById(R.id.tvExperienceLevel);
                            TextView tvShift = view.findViewById(R.id.tvShift);
                            TextView tvPayTag = view.findViewById(R.id.tvPayTag);

                            tvTitle.setText(job.getJobTitle());
                            tvLocation.setText(job.getLocation());
                            tvCompany.setText(job.getCompany().getDisplayName());
                            tvSalary.setText(
                                    String.format("₱%,.2f – ₱%,.2f",
                                            job.getMinSalary(),
                                            job.getMaxSalary())
                            );
                            tvJobType.setText(job.getJobType().toString());
                            tvExperienceLevel.setText(job.getExperienceLevel().toString());
                            tvShift.setText(job.getRemoteOption().toString());
                            tvPayTag.setText("✓ 13th Month Pay");
                        }
                );

        adapter.setOnItemClickListener((job, position) -> {
            JobDesc fragment = new JobDesc();
            Bundle bundle = new Bundle();
            bundle.putString("jobId", String.valueOf(job.getId()));
            bundle.putString("Department", job.getDepartment());
            fragment.setArguments(bundle);
           // UiHelpers.showToast("job Id: " + String.valueOf(job.getId()) + " ", requireContext());
            UiHelpers.switchFragment(
                    requireActivity().getSupportFragmentManager(),
                    fragment
            );
        });

        jobRecyclerView.setAdapter(adapter);
    }

    private void showError(String message) {
        loadingContainer.setVisibility(View.GONE);
        jobRecyclerView.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.error_container,
                FragmentError.newInstance(message)
        );
    }

    @Override
    public void onRetry() {
        fetchJobs();
    }
}
