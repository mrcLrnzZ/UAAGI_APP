package com.example.uaagi_app.ui.users.FragmentsAppliedJobs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.JobViewModel;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.users.FragmentNoJobs;
import com.example.uaagi_app.ui.users.FragmentsCareers.JobDesc;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.SessionManager;

import java.util.List;

public class SectionArchived extends Fragment {

    private List<JobFetchResponse> archivedJobs;
    private RecyclerView rvArchived;
    private GenericRecyclerAdapter<JobFetchResponse> adapter;
    private FrameLayout noJobs;
    private JobViewModel jobViewModel;
    public SectionArchived() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobViewModel = new ViewModelProvider(requireActivity()).get(JobViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applied_jobs_section_archived, container, false);
        rvArchived = view.findViewById(R.id.rvArchived);
        noJobs = view.findViewById(R.id.noJobs);
        rvArchived.setLayoutManager(new LinearLayoutManager(requireContext()));

        jobViewModel.fetchArchivedJobs(SessionManager.getInstance(requireContext()).getUserId(), requireContext());
        jobViewModel.getArchivedJobs().observe(getViewLifecycleOwner(), this::UiHandler);
        return view;
    }
    private void UiHandler(List<JobFetchResponse> archivedJobs) {
        if (archivedJobs.isEmpty()) {
            showEmpty();
        } else {
            showContent(archivedJobs);
        }
    }
    private void showContent(List<JobFetchResponse> archivedJobs) {
        UiHelpers.jobCardAdapter(
                rvArchived,
                archivedJobs,
                getChildFragmentManager(),
                requireContext()
        );
    }
    private void showEmpty() {
        rvArchived.setVisibility(View.GONE);
        noJobs.setVisibility(View.VISIBLE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.noJobs,
                FragmentNoJobs.newInstance()
        );
    }
}


