package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.api.JobFetchService;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.FragmentsCareers.Adapter.JobEntry;
import com.example.uaagi_app.ui.users.FragmentsCareers.JobDesc;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.List;

public class Home extends Fragment {
    private static final String TAG = "HomeFragment";
    private List<JobFetchResponse> jobs;
    private RecyclerView jobRecyclerView;
    private  JobEntry adapter;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigate_home, container, false);
        jobRecyclerView = view.findViewById(R.id.job_container);
        fetchJobs();
        return view;
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
                        fragment.setArguments(bundle);

                        requireActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack(null)
                                .commit();
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
