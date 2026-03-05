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
                                            showToast(message, context);
                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            showToast(errorMessage, context);
                                        }
                                    });

                                } else {

                                    Helpers.actionSaveJob(context, job.getId(), new JobService.FeedbackCallback() {
                                        @Override
                                        public void feedback(String message) {

                                            ivBookmark.setColorFilter(
                                                    ContextCompat.getColor(context, R.color.holo_red_dark)
                                            );

                                            showToast(message, context);
                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            showToast(errorMessage, context);
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
                                    showToast("Unliked!", context);

                                } else {

                                    ivLike.setColorFilter(
                                            ContextCompat.getColor(context, R.color.holo_blue_light)
                                    );

                                    showToast("Liked!", context);
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
