package com.example.uaagi_app.ui.users.FragmentsAppliedJobs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
        adapter = new GenericRecyclerAdapter<>(
                archivedJobs,
                R.layout.item_job_card,
                (itemView, item, position) -> {

                    TextView tvTitle = itemView.findViewById(R.id.tvJobTitle);
                    TextView tvLocation = itemView.findViewById(R.id.tvLocation);
                    TextView tvCompany = itemView.findViewById(R.id.tvCompany);
                    TextView tvSalary = itemView.findViewById(R.id.tvSalary);
                    TextView tvJobType = itemView.findViewById(R.id.tvJobType);
                    TextView tvExperienceLevel = itemView.findViewById(R.id.tvExperienceLevel);
                    TextView tvShift = itemView.findViewById(R.id.tvShift);
                    TextView tvPayTag = itemView.findViewById(R.id.tvPayTag);

                    tvTitle.setText(item.getJobTitle());
                    tvLocation.setText(item.getLocation());
                    tvCompany.setText(item.getCompany().getDisplayName());
                    tvSalary.setText(
                            String.format("₱%,.2f – ₱%,.2f",
                                    item.getMinSalary(),
                                    item.getMaxSalary())
                    );
                    tvJobType.setText(item.getJobType().toString());
                    tvExperienceLevel.setText(item.getExperienceLevel().toString());
                    tvShift.setText(item.getRemoteOption().toString());
                    tvPayTag.setText("✓ 13th Month Pay");
                });
        adapter.setOnItemClickListener((job, position) -> {
            JobDesc fragment = new JobDesc();
            Bundle bundle = new Bundle();
            bundle.putString("jobId", String.valueOf(job.getId()));
            bundle.putString("Department", job.getDepartment());
            fragment.setArguments(bundle);
            UiHelpers.switchFragment(
                    requireActivity().getSupportFragmentManager(),
                    fragment
            );
        });
        rvArchived.setAdapter(adapter);
        ;
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


