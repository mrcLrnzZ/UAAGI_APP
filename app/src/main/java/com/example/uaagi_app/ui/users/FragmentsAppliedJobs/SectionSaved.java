package com.example.uaagi_app.ui.users.FragmentsAppliedJobs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.JobViewModel;
import com.example.uaagi_app.network.Services.JobService;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.users.FragmentNoJobs;
import com.example.uaagi_app.ui.users.FragmentsCareers.JobDesc;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.SessionManager;

import java.util.List;

public class SectionSaved extends Fragment {

    private RecyclerView rvSaved;
    private FrameLayout noJobs, errorContainer, loadingContainer;
    private JobViewModel jobViewModel;
    public SectionSaved() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobViewModel = new ViewModelProvider(requireActivity()).get(JobViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applied_jobs_section_saved, container, false);

        rvSaved = view.findViewById(R.id.rvSaved);
        noJobs = view.findViewById(R.id.noJobs);
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
        rvSaved.setLayoutManager(new LinearLayoutManager(requireContext()));
        jobViewModel.fetchSavedJobs(SessionManager.getInstance(requireContext()).getUserId(), requireContext());
        jobViewModel.getSavedJobs().observe(getViewLifecycleOwner(), this::UiHandler);

        return view;
    }

    private void UiHandler(List<JobFetchResponse> savedJobs) {
        Log.d("SectionSaved", "Saved jobs: " + savedJobs);
        if (savedJobs.isEmpty()) {
            showEmpty();
        } else {
            Log.d("SectionSaved", "Showing saved jobs");
            showContent(savedJobs);
        }
    }
    private void showContent(List<JobFetchResponse> savedJobs) {

        noJobs.setVisibility(View.GONE);
        loadingContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        rvSaved.setVisibility(View.VISIBLE);

        UiHelpers.jobCardAdapter(
                rvSaved,
                savedJobs,
                requireActivity().getSupportFragmentManager(),
                requireContext()
        );
    }
    private void showEmpty() {
        rvSaved.setVisibility(View.GONE);
        loadingContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        noJobs.setVisibility(View.VISIBLE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.noJobs,
                FragmentNoJobs.newInstance()
        );
    }
    private void showError(String message) {
        loadingContainer.setVisibility(View.GONE);
        rvSaved.setVisibility(View.GONE);
        noJobs.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.error_container,
                FragmentError.newInstance(message)
        );
    }
    private void showLoading() {
        loadingContainer.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
        noJobs.setVisibility(View.GONE);
        rvSaved.setVisibility(View.GONE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.loading_container,
                FragmentLoading.newInstance()
        );
    }
}


