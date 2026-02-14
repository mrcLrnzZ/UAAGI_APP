package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.api.JobFetchService;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.FragmentsCareers.JobDesc;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.List;

public class Home extends Fragment {
    private static final String TAG = "HomeFragment";
    private List<JobFetchResponse> jobs;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigate_home, container, false);

        fetchJobs();
        return view;
    }
    private void fetchJobs() {
        JobFetchService service = new JobFetchService(requireContext());

        service.fetchJobs(new JobFetchService.JobFetchCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> response) {
                jobs = response;
                Log.d(TAG, "Jobs fetched: " + (jobs != null ? jobs.size() : 0));

                LinearLayout jobContainer = getView().findViewById(R.id.job_container);
                if (jobs != null && !jobs.isEmpty() && jobContainer != null) {
                    int limit = Math.min(jobs.size(), 5);
                    for (int i = 0; i < limit; i++) {
                        JobFetchResponse job = jobs.get(i);
                        UiHelpers.addJobEntry(job, jobContainer, requireContext());
                    }
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
