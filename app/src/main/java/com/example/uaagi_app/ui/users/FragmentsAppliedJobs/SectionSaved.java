package com.example.uaagi_app.ui.users.FragmentsAppliedJobs;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.utils.SessionManager;

import java.util.List;

public class SectionSaved extends Fragment {

    private RecyclerView rvSaved;
    private FrameLayout noJobs, errorContainer, loadingContainer;
    public SectionSaved() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applied_jobs_section_saved, container, false);
        rvSaved = view.findViewById(R.id.rvSaved);
        noJobs = view.findViewById(R.id.noJobs);
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
        return view;
    }
    private void fetchJobs() {
        showLoading();
        JobService service = new JobService(requireContext());
        service.fetchSavedJob(SessionManager.getInstance(requireContext()).getUserId(), new JobService.JobServiceCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> response) {

                Log.d("SectionSaved", "Response received: " + response.size());
                noJobs.setVisibility(View.GONE);
                loadingContainer.setVisibility(View.GONE);
                rvSaved.setVisibility(View.VISIBLE);
                jobCardAdapter(rvSaved, response, getChildFragmentManager(), requireContext());
            }
            @Override
            public void onError(String errorMessage) {
                showError(errorMessage);
            }
        });
    }
    private void UiHandler(List<JobFetchResponse> savedJobs) {
        Log.d("SectionSaved", "Saved jobs: " + savedJobs);
        if (savedJobs.isEmpty()) {
            showEmpty();
        } else {
            showContent(savedJobs);
        }
    }
    private void showContent(List<JobFetchResponse> savedJobs) {

        noJobs.setVisibility(View.GONE);
        loadingContainer.setVisibility(View.GONE);
        rvSaved.setVisibility(View.VISIBLE);

        UiHelpers.jobCardAdapter(
                rvSaved,
                savedJobs,
                getChildFragmentManager(),
                requireContext()
        );
    }
    private void showEmpty() {
        rvSaved.setVisibility(View.GONE);
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
        rvSaved.setVisibility(View.GONE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.loading_container,
                FragmentLoading.newInstance()
        );
    }
    public static void jobCardAdapter(
            RecyclerView recyclerView,
            List<JobFetchResponse> jobs,
            FragmentManager fragmentManager,
            Context context
    ) {

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

                            ImageView ivBookmark = view.findViewById(R.id.ivBookmark);
                            ImageView ivLike = view.findViewById(R.id.ivLike);

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

                        /* -------------------------
                           Bookmark (Save Job)
                           ------------------------- */

                            ivBookmark.setOnClickListener(v -> {

                                if (ivBookmark.getColorFilter() != null) {

                                    Helpers.actionUnsaveJob(context, job.getId(), new JobService.FeedbackCallback() {
                                        @Override
                                        public void feedback(String message) {
                                            ivBookmark.setColorFilter(null);
                                            UiHelpers.showToast(message, context);
                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            UiHelpers.showToast(errorMessage, context);
                                        }
                                    });

                                } else {

                                    Helpers.actionSaveJob(context, job.getId(), new JobService.FeedbackCallback() {
                                        @Override
                                        public void feedback(String message) {

                                            ivBookmark.setColorFilter(
                                                    ContextCompat.getColor(context, R.color.holo_red_dark)
                                            );

                                            UiHelpers.showToast(message, context);
                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            UiHelpers.showToast(errorMessage, context);
                                        }
                                    });

                                }
                            });

                        /* -------------------------
                           Like Job
                           ------------------------- */

                            ivLike.setOnClickListener(v -> {

                                if (ivLike.getColorFilter() != null) {

                                    ivLike.setColorFilter(null);
                                    UiHelpers.showToast("Unliked!", context);

                                } else {

                                    ivLike.setColorFilter(
                                            ContextCompat.getColor(context, R.color.holo_blue_light)
                                    );

                                    UiHelpers.showToast("Liked!", context);
                                }
                            });

                        }
                );

    /* -------------------------
       Card Click -> Job Details
       ------------------------- */

        adapter.setOnItemClickListener((job, position) -> {

            JobDesc fragment = new JobDesc();

            Bundle bundle = new Bundle();
            bundle.putString("jobId", String.valueOf(job.getId()));
            bundle.putString("Department", job.getDepartment());

            fragment.setArguments(bundle);

            UiHelpers.switchFragment(
                    fragmentManager,
                    fragment
            );
        });

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}


