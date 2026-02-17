package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.users.FragmentsCareers.Adapter.JobEntry;
import com.example.uaagi_app.ui.users.FragmentsCareers.JobDesc;
import com.example.uaagi_app.ui.utils.UiHelpers;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.uaagi_app.network.api.JobFetchService;
import com.example.uaagi_app.network.dto.JobFetchResponse;

import java.util.List;

public class Careers extends Fragment {
    private static final String TAG = "CareersFragment";
    private List<JobFetchResponse> jobs;
    private RecyclerView jobRecyclerView;
    private  JobEntry adapter;
    public Careers() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_navigate_careers, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jobRecyclerView = view.findViewById(R.id.job_container);
        fetchJobs();
    }
    private void fetchJobs() {
        JobFetchService service = new JobFetchService(requireContext());

        service.fetchJobsForUser(new JobFetchService.JobFetchCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> response) {
                jobs = response;
                Log.d(TAG, "Jobs fetched: " + (jobs != null ? jobs.size() : 0));
                if (jobs != null && !jobs.isEmpty() && jobRecyclerView != null) {
                    adapter = new JobEntry(jobs, 0, 5, job -> {
                        JobDesc fragment = new JobDesc();

                        Bundle bundle = new Bundle();
                        String jobId = String.valueOf(job.getId());
                        bundle.putString("jobId", jobId);
                        bundle.putString("jobTitle", job.getJobTitle());
                        bundle.putString("jobLocation", job.getLocation());
                        bundle.putString("jobCompany", job.getCompany().getDisplayName());
                        fragment.setArguments(bundle);

                        UiHelpers.switchFragment(requireActivity().getSupportFragmentManager(), fragment);
                    });
                    jobRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    jobRecyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "No jobs available", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}