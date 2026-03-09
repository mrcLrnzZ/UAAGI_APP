package com.example.uaagi_app.ui.utils;


import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.Services.JobService;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.users.FragmentsCareers.JobDesc;
import com.example.uaagi_app.utils.Helpers;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UiHelpers {
    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void textInputLayoutSetErr(TextInputLayout layout, String ErrMessage){
        layout.setError(ErrMessage);
    }
    public static void setRequiredHint(Context context, TextInputLayout layout, String text) {
        String hint = text + " *";
        SpannableString spannable = new SpannableString(hint);

        spannable.setSpan(
                new ForegroundColorSpan(
                        ContextCompat.getColor(context, android.R.color.holo_red_dark)
                ),
                hint.length() - 1,
                hint.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        layout.setHint(spannable);
    }
    public static void dropDownMaker(String[] array, AutoCompleteTextView textView, Context context) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, array);
        dropDownSetter(textView, adapter);
    }
    private static void dropDownSetter(AutoCompleteTextView textView, ArrayAdapter<String> adapter) {
        textView.setAdapter(adapter);
    }
    @Deprecated
    /**Legacy method for switching fragments. Problem with backstack handling.*/
    public static void switchFragment(FragmentManager fragmentManager, Fragment targetFragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, targetFragment)
                .addToBackStack(null)
                .commit();
    }
    public static void switchFragment(FragmentManager fragmentManager, Fragment targetFragment, String tag) {
        Fragment existingFragment = fragmentManager.findFragmentByTag(tag);
        if (existingFragment != null) {
            return;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, targetFragment, tag)
                .commit();
    }
    public static void switchToFragment(FragmentManager fragmentManager, Fragment targetFragment, String tag) {
        int backStackCount = fragmentManager.getBackStackEntryCount();
        List<String> backStackEntries = new ArrayList<>();

        for (int i = 0; i < backStackCount; i++) {
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(i);
            backStackEntries.add(backStackEntry.getName());
        }
        if (!backStackEntries.contains(tag)) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(
                            R.anim.fade_in,
                            R.anim.fade_out
                    )
                    .replace(R.id.fragment_container, targetFragment)
                    .addToBackStack(tag)
                    .commit();
            return;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, targetFragment, tag)
                .setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out
                )
                .commit();
        Log.d("FRAGMENT_DEBUG", "Switched to: " + tag);
    }

    @Deprecated
    /**Legacy method for switching fragments. Problem with backstack handling.*/
    public static void switchToFragment(FragmentManager fragmentManager, Fragment targetFragment) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out
                )
                .replace(R.id.fragment_container, targetFragment)
                .addToBackStack(null)
                .commit();
    }
    public static void replaceChildFragment(
            FragmentManager fragmentManager,
            int containerId,
            Fragment fragment) {

        if (fragmentManager.isStateSaved()) return;

        String tag = fragment.getClass().getSimpleName();

        Fragment existing = fragmentManager.findFragmentByTag(tag);
        if (existing != null && existing.isVisible()) {
            return;
        }

        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .commit();
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, boolean addToBackStack) {
        var transaction = fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }
    public static <T> void updateRemoveButtonVisibility(List<T> list, Button button, int minEntries) {
        button.setVisibility(list.size() > minEntries ? View.VISIBLE : View.GONE);
    }
    public static void jobCardAdapter(
            RecyclerView recyclerView,
            List<JobFetchResponse> jobs,
            FragmentManager fragmentManager,
            Context context
    ) {
        GenericRecyclerAdapter<JobFetchResponse> adapter = createJobCardAdapter(jobs, context);
        setupJobCardClickListener(adapter, fragmentManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private static GenericRecyclerAdapter<JobFetchResponse> createJobCardAdapter(
            List<JobFetchResponse> jobs,
            Context context
    ) {
        return new GenericRecyclerAdapter<>(
                jobs,
                R.layout.item_job_card,
                (view, job, position) -> {
                    JobCardViewHolder viewHolder = new JobCardViewHolder(view);
                    JobCardState state = new JobCardState();

                    populateJobData(viewHolder, job);
                    initializeJobState(viewHolder, job, state, context);
                    setupBookmarkClickListener(viewHolder, job, state, context);
                    setupArchiveClickListener(viewHolder, job, state, context);
                }
        );
    }

    private static void populateJobData(JobCardViewHolder viewHolder, JobFetchResponse job) {
        viewHolder.tvTitle.setText(job.getJobTitle());
        viewHolder.tvLocation.setText(job.getLocation());
        viewHolder.tvCompany.setText(job.getCompany().getDisplayName());
        viewHolder.tvSalary.setText(
                String.format("₱%,.2f – ₱%,.2f", job.getMinSalary(), job.getMaxSalary())
        );
        viewHolder.tvJobType.setText(job.getJobType().toString());
        viewHolder.tvExperienceLevel.setText(job.getExperienceLevel().toString());
        viewHolder.tvShift.setText(job.getRemoteOption().toString());
        viewHolder.tvPayTag.setText("✓ 13th Month Pay");
    }
    private static void initializeJobState(
            JobCardViewHolder viewHolder,
            JobFetchResponse job,
            JobCardState state,
            Context context
    ) {
        Helpers.actionFetchArchivedJobId(context, new JobService.JobIdServiceCallback() {
            @Override
            public void onResponse(List<Integer> jobIds) {
                if (jobIds != null && jobIds.contains(job.getId())) {
                    state.isArchived = true;
                    viewHolder.ivLike.setColorFilter(
                            ContextCompat.getColor(context, R.color.holo_blue_light)
                    );
                }
            }

            @Override
            public void onError(String errorMessage) {
                showToast(errorMessage, context);
            }
        });
        Helpers.actionFetchSavedJobId(context, new JobService.JobIdServiceCallback() {
            @Override
            public void onResponse(List<Integer> jobIds) {
                if (jobIds != null && jobIds.contains(job.getId())) {
                    state.isSaved = true;
                    viewHolder.ivBookmark.setColorFilter(
                            ContextCompat.getColor(context, R.color.holo_red_dark)
                    );
                }
            }

            @Override
            public void onError(String errorMessage) {
                showToast(errorMessage, context);
            }
        });
    }
    private static void setupBookmarkClickListener(
            JobCardViewHolder viewHolder,
            JobFetchResponse job,
            JobCardState state,
            Context context
    ) {
        viewHolder.ivBookmark.setOnClickListener(v -> {
            if (state.isSaved) {
                unsaveJob(viewHolder, job, state, context);
            } else {
                if (state.isArchived) {
                    showToast("Job is archived. Unarchive first.", context);
                    return;
                }
                saveJob(viewHolder, job, state, context);
            }
        });
    }

    private static void setupArchiveClickListener(
            JobCardViewHolder viewHolder,
            JobFetchResponse job,
            JobCardState state,
            Context context
    ) {
        viewHolder.ivLike.setOnClickListener(v -> {
            if (state.isArchived) {
                unarchiveJob(viewHolder, job, state, context);
            } else {
                if (state.isSaved) {
                    showToast("Job is saved. Unsave first.", context);
                    return;
                }
                archiveJob(viewHolder, job, state, context);
            }
        });
    }

    private static void saveJob(
            JobCardViewHolder viewHolder,
            JobFetchResponse job,
            JobCardState state,
            Context context
    ) {
        Helpers.actionSaveJob(context, job.getId(), new JobService.FeedbackCallback() {
            @Override
            public void feedback(String message) {
                viewHolder.ivBookmark.setColorFilter(
                        ContextCompat.getColor(context, R.color.holo_red_dark)
                );
                state.isSaved = true;
                showToast(message, context);
            }

            @Override
            public void onError(String errorMessage) {
                showToast(errorMessage, context);
            }
        });
    }
    private static void unsaveJob(
            JobCardViewHolder viewHolder,
            JobFetchResponse job,
            JobCardState state,
            Context context
    ) {
        Helpers.actionUnsaveJob(context, job.getId(), new JobService.FeedbackCallback() {
            @Override
            public void feedback(String message) {
                viewHolder.ivBookmark.setColorFilter(null);
                state.isSaved = false;
                showToast(message, context);
            }

            @Override
            public void onError(String errorMessage) {
                showToast(errorMessage, context);
            }
        });
    }
    private static void archiveJob(
            JobCardViewHolder viewHolder,
            JobFetchResponse job,
            JobCardState state,
            Context context
    ) {
        Helpers.actionArchiveJob(context, job.getId(), new JobService.FeedbackCallback() {
            @Override
            public void feedback(String message) {
                viewHolder.ivLike.setColorFilter(
                        ContextCompat.getColor(context, R.color.holo_blue_light)
                );
                state.isArchived = true;
                showToast(message, context);
            }

            @Override
            public void onError(String errorMessage) {
                showToast(errorMessage, context);
            }
        });
    }
    private static void unarchiveJob(
            JobCardViewHolder viewHolder,
            JobFetchResponse job,
            JobCardState state,
            Context context
    ) {
        Helpers.actionUnarchiveJob(context, job.getId(), new JobService.FeedbackCallback() {
            @Override
            public void feedback(String message) {
                viewHolder.ivLike.setColorFilter(null);
                state.isArchived = false;
                showToast(message, context);
            }

            @Override
            public void onError(String errorMessage) {
                showToast(errorMessage, context);
            }
        });
    }
    private static void setupJobCardClickListener(
            GenericRecyclerAdapter<JobFetchResponse> adapter,
            FragmentManager fragmentManager
    ) {
        adapter.setOnItemClickListener((job, position) -> {

            JobDesc fragment = new JobDesc();

            Bundle bundle = new Bundle();
            bundle.putString("jobId", String.valueOf(job.getId()));
            bundle.putString("Department", job.getDepartment());

            fragment.setArguments(bundle);

            UiHelpers.switchFragment(fragmentManager, fragment);
        });
    }
    private static class JobCardViewHolder {
        final TextView tvTitle;
        final TextView tvLocation;
        final TextView tvCompany;
        final TextView tvSalary;
        final TextView tvJobType;
        final TextView tvExperienceLevel;
        final TextView tvShift;
        final TextView tvPayTag;
        final ImageView ivBookmark;
        final ImageView ivLike;

        JobCardViewHolder(View view) {
            tvTitle = view.findViewById(R.id.tvJobTitle);
            tvLocation = view.findViewById(R.id.tvLocation);
            tvCompany = view.findViewById(R.id.tvCompany);
            tvSalary = view.findViewById(R.id.tvSalary);
            tvJobType = view.findViewById(R.id.tvJobType);
            tvExperienceLevel = view.findViewById(R.id.tvExperienceLevel);
            tvShift = view.findViewById(R.id.tvShift);
            tvPayTag = view.findViewById(R.id.tvPayTag);
            ivBookmark = view.findViewById(R.id.ivBookmark);
            ivLike = view.findViewById(R.id.ivLike);
        }
    }
    private static class JobCardState {
        boolean isSaved = false;
        boolean isArchived = false;
    }
}
