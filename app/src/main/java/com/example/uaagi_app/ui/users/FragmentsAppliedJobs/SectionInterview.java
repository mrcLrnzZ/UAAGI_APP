package com.example.uaagi_app.ui.users.FragmentsAppliedJobs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.ApplicantViewModel;
import com.example.uaagi_app.network.dto.Applicant;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class SectionInterview extends Fragment {

    private RecyclerView rvInterview;
    private GenericRecyclerAdapter<Applicant> adapter;
    private List<Applicant> interviewList = new ArrayList<>();
    private ApplicantViewModel applicantViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_applied_jobs_section_interview, container, false);

        rvInterview = view.findViewById(R.id.rvInterview);
        rvInterview.setLayoutManager(new LinearLayoutManager(getContext()));

        setupAdapter();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        applicantViewModel = new ViewModelProvider(requireActivity()).get(ApplicantViewModel.class);

        int userId = SessionManager.getInstance(requireContext()).getUserId();
        applicantViewModel.fetchApplicantsForUser(userId, requireContext());

        applicantViewModel.getApplicants().observe(getViewLifecycleOwner(), applicants -> {
            interviewList.clear();

            for (Applicant applicant : applicants) {
                Log.d("Interview Record", "Job: " + applicant.getJobTitle() +
                        ", Company: " + applicant.getCompany() +
                        ", Status: " + applicant.getInterviewStatus());

                if (!applicant.getInterviewStatus().equalsIgnoreCase("Pending")    ) {
                    interviewList.add(applicant);
                }
            }

            adapter.updateList(interviewList);
        });
    }

    private void setupAdapter() {
        adapter = new GenericRecyclerAdapter<>(interviewList, R.layout.item_interview_card,
                (view, applicant, position) -> {

                    TextView tvJobTitle = view.findViewById(R.id.tvJobTitle);
                    TextView tvCompany = view.findViewById(R.id.tvCompanyName);
                    TextView tvInterviewDate = view.findViewById(R.id.tvInterviewDate);
                    TextView tvLocation = view.findViewById(R.id.tvLocation);

                    tvJobTitle.setText(Helpers.safeText(applicant.getJobTitle()));
                    tvCompany.setText(Helpers.safeText(applicant.getCompany()));
                    tvInterviewDate.setText(Helpers.formatToOrdinalDate(Helpers.safeText(applicant.getInterviewDate())));
                    tvLocation.setText(Helpers.safeText(applicant.getLocation()));
                });

        adapter.setOnItemClickListener((item, position) -> {
            showUpdateStatusDialog(item);
        });

        rvInterview.setAdapter(adapter);
    }

    private void showUpdateStatusDialog(Applicant applicant) {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_status);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        ImageView ivStatusIcon = dialog.findViewById(R.id.ivStatusIcon);
        LinearLayout infoRejectBox = dialog.findViewById(R.id.infoRejectBox);
        LinearLayout infoBox = dialog.findViewById(R.id.infoBox);
        LinearLayout statusBadgeContainer = dialog.findViewById(R.id.statusBadgeContainer);
        ImageView ivCloseDialog = dialog.findViewById(R.id.ivCloseDialog);
        TextView tvInterviewDate = dialog.findViewById(R.id.interviewDate);
        TextView tvInterviewTime = dialog.findViewById(R.id.interviewTime);
        TextView tvModeInterview = dialog.findViewById(R.id.tvModeInterview);
        TextView tvPlaceOfInterview = dialog.findViewById(R.id.tvPlaceOfInterview);
        TextView tvApplicationStatus = dialog.findViewById(R.id.tvApplicationStatus);
        Button btnDecline = dialog.findViewById(R.id.btnDecline);
        Button btnReschedule = dialog.findViewById(R.id.btnReschedule);

        tvInterviewDate.setText(
                Helpers.formatToOrdinalDate(Helpers.safeText(applicant.getInterviewDate()))
        );
        tvInterviewTime.setText(
                Helpers.formatTime(Helpers.safeText(applicant.getInterviewStart()))
        );
        tvModeInterview.setText(Helpers.safeText(applicant.getInterviewType()));
        tvPlaceOfInterview.setText(Helpers.safeText(applicant.getLocation()));
        tvApplicationStatus.setText(Helpers.capitalize(Helpers.safeText(applicant.getStatus())));

        String status = Helpers.safeText(applicant.getStatus()).toLowerCase();
        Log.d("StatusOfApplicant", "Status: " + status);

        if ("rejected".equalsIgnoreCase(Helpers.safeText(applicant.getStatus()))) {
            infoRejectBox.setVisibility(View.VISIBLE);
            infoBox.setVisibility(View.GONE);
            btnDecline.setVisibility(View.GONE);
            btnReschedule.setVisibility(View.GONE);

            // Example: dynamically add a TextView explaining rejection
            TextView reasonText = new TextView(requireContext());
            reasonText.setText(applicant.getReason());
            reasonText.setTextColor(Color.parseColor("#D32F2F")); // dark red text
            reasonText.setTextSize(14f);

            infoRejectBox.removeAllViews(); // clear old views
            infoRejectBox.addView(reasonText);
        } else {
            infoRejectBox.setVisibility(View.GONE);
        }
        switch (status) {
            case "rejected" -> {
                statusBadgeContainer.setBackgroundColor(Color.parseColor("#FDECEA")); // red
                ivStatusIcon.setImageResource(R.drawable.ic_cancel);
                ivStatusIcon.setColorFilter(Color.parseColor("#D32F2F"));
                tvApplicationStatus.setTextColor(Color.parseColor("#D32F2F"));
            }
            case "applied" -> {
                statusBadgeContainer.setBackgroundColor(Color.parseColor("#EAF3DE")); // green

                ivStatusIcon.setImageResource(R.drawable.ic_info);
                ivStatusIcon.setColorFilter(Color.parseColor("#3B6D11"));
                tvApplicationStatus.setTextColor(Color.parseColor("#3B6D11"));
            }
            case "accepted" -> {
                statusBadgeContainer.setBackgroundColor(Color.parseColor("#EAF3DE")); // green

                ivStatusIcon.setImageResource(R.drawable.ic_check_circle);
                ivStatusIcon.setColorFilter(Color.parseColor("#3B6D11"));
                tvApplicationStatus.setTextColor(Color.parseColor("#3B6D11"));
            }
            case "pending" -> {
                statusBadgeContainer.setBackgroundColor(Color.parseColor("#FFF8E1")); // yellow

                ivStatusIcon.setImageResource(R.drawable.ic_info);
            }
            default -> {
                statusBadgeContainer.setBackgroundColor(Color.parseColor("#BDBDBD")); // gray

                ivStatusIcon.setImageResource(R.drawable.ic_info);
            }
        }

        ivCloseDialog.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

}