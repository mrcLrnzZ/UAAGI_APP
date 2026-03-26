package com.example.uaagi_app.ui.users.FragmentsAppliedJobs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.ApplicantViewModel;
import com.example.uaagi_app.network.Services.ApplicationService;
import com.example.uaagi_app.network.dto.Applicant;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SectionInterview extends Fragment {

    private RecyclerView rvInterview;
    private GenericRecyclerAdapter<Applicant> adapter;
    private List<Applicant> interviewList = new ArrayList<>();
    private ApplicantViewModel applicantViewModel;
    private ApplicationService applicationService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationService = new ApplicationService(requireContext());
    }

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
                if (applicant.getInterviewStatus() != null && !applicant.getInterviewStatus().equalsIgnoreCase("Pending")) {
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

        // Bind Views
        TextView tvDialogTitle = dialog.findViewById(R.id.tvDialogTitle);
        TextView tvDialogSubtitle = dialog.findViewById(R.id.tvDialogSubtitle);
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
        TextView btnAddToCalendar = dialog.findViewById(R.id.btnAddToCalendar);
        Button btnDecline = dialog.findViewById(R.id.btnDecline);
        Button btnReschedule = dialog.findViewById(R.id.btnReschedule);

        // Common Setup
        tvDialogSubtitle.setText(String.format("%s · %s", applicant.getCompany(), applicant.getJobTitle()));
        tvInterviewDate.setText(Helpers.formatToOrdinalDate(Helpers.safeText(applicant.getInterviewDate())));
        tvInterviewTime.setText(Helpers.formatTime(Helpers.safeText(applicant.getInterviewStart())));
        tvModeInterview.setText(Helpers.safeText(applicant.getInterviewType()));
        tvPlaceOfInterview.setText(Helpers.safeText(applicant.getLocation()));

        ivCloseDialog.setOnClickListener(v -> dialog.dismiss());
        btnAddToCalendar.setOnClickListener(v -> addToCalendar(applicant));

        String status = Helpers.safeText(applicant.getInterviewStatus()).toLowerCase();

        // Reset visibility states
        statusBadgeContainer.setVisibility(View.VISIBLE);
        infoRejectBox.setVisibility(View.GONE);
        infoBox.setVisibility(View.VISIBLE);
        btnDecline.setVisibility(View.GONE);
        btnReschedule.setVisibility(View.GONE);

        switch (status) {
            case "accepted" -> {
                tvDialogTitle.setText("Interview Scheduled");
                tvApplicationStatus.setText("Approved");
                statusBadgeContainer.setBackgroundResource(R.drawable.bg_approved_badge);
                ivStatusIcon.setImageResource(R.drawable.ic_check_circle);
                int approvedColor = Color.parseColor("#4A7C59");
                ivStatusIcon.setColorFilter(approvedColor);
                tvApplicationStatus.setTextColor(approvedColor);
            }
            case "rejected" -> {
                tvDialogTitle.setText("Application Status");
                tvApplicationStatus.setText("Rejected");
                statusBadgeContainer.setBackgroundResource(R.drawable.bg_rejected_badge);
                ivStatusIcon.setImageResource(R.drawable.ic_cancel);
                int rejectedColor = Color.parseColor("#D32F2F");
                ivStatusIcon.setColorFilter(rejectedColor);
                tvApplicationStatus.setTextColor(rejectedColor);

                infoRejectBox.setVisibility(View.VISIBLE);
                infoBox.setVisibility(View.GONE);

                TextView tvReason = dialog.findViewById(R.id.tvRejectionReason);
                if (tvReason != null) {
                    tvReason.setText("Reason: " + (applicant.getReason() != null ? applicant.getReason() : "No reason provided"));
                }
            }
            case "scheduled" -> {
                tvDialogTitle.setText("You have a scheduled interview");
                statusBadgeContainer.setVisibility(View.GONE);
                btnDecline.setVisibility(View.VISIBLE);
                btnReschedule.setVisibility(View.VISIBLE);

                btnDecline.setOnClickListener(v -> showRejectConfirmationDialog(applicant, dialog));
                btnReschedule.setOnClickListener(v -> showRescheduleDialog(applicant, dialog));
            }
            default -> {
                tvDialogTitle.setText("Interview Details");
                statusBadgeContainer.setVisibility(View.GONE);
            }
        }

        dialog.show();
    }
    private void addToCalendar(Applicant applicant) {
        try {
            String dateStr = applicant.getInterviewDate();
            String timeStr = applicant.getInterviewStart();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = sdf.parse(dateStr + " " + timeStr);

            if (date != null) {
                long startTime = date.getTime();
                long endTime = startTime + (60 * 60 * 1000);

                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                        .putExtra(CalendarContract.Events.TITLE, "Interview: " + applicant.getJobTitle() + " at " + applicant.getCompany())
                        .putExtra(CalendarContract.Events.DESCRIPTION, "Interview Mode: " + applicant.getInterviewType())
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, applicant.getLocation())
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

                startActivity(intent);
            }
        } catch (Exception e) {
            Helpers.showToast("Could not add to calendar", requireContext());
        }
    }

    private void showRejectConfirmationDialog(Applicant applicant, Dialog parentDialog) {
        Dialog confirmDialog = new Dialog(requireContext());
        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmDialog.setContentView(R.layout.dialog_withdraw_application);

        if (confirmDialog.getWindow() != null) {
            confirmDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView tvTitle = confirmDialog.findViewById(R.id.tvDialogTitle);
        TextView tvSubtitle = confirmDialog.findViewById(R.id.tvDialogSubtitle);
        TextView tvMessage = confirmDialog.findViewById(R.id.tvDialogMessage);
        TextView tvDetails = confirmDialog.findViewById(R.id.tvDialogDetails);
        ImageView ivClose = confirmDialog.findViewById(R.id.ivCloseDialog);
        Button btnCancel = confirmDialog.findViewById(R.id.btnCancel);
        Button btnConfirm = confirmDialog.findViewById(R.id.btnConfirm);

        tvTitle.setText("Withdraw Application");
        tvSubtitle.setText(applicant.getCompany() + " · " + applicant.getJobTitle());
        tvMessage.setText("Are you sure you want to withdraw?");
        tvDetails.setText("This will permanently remove your application for " + applicant.getJobTitle() + " at " + applicant.getCompany() + ". This action cannot be undone.");

        btnConfirm.setText("Yes, Withdraw");

        ivClose.setOnClickListener(v -> confirmDialog.dismiss());
        btnCancel.setOnClickListener(v -> confirmDialog.dismiss());
        btnConfirm.setOnClickListener(v -> {
            applicationService.withdrawApplication(applicant.getApplicationId(), new ApplicationService.SimpleCallback() {
                @Override
                public void onResponse(String message) {
                    confirmDialog.dismiss();
                    parentDialog.dismiss();
                    Helpers.showToast("Application withdrawn", requireContext());
                    refreshData();
                }

                @Override
                public void onError(String message) {
                    Helpers.showToast("Error: " + message, requireContext());
                }
            });
        });

        confirmDialog.show();
    }

    private void showRescheduleDialog(Applicant applicant, Dialog parentDialog) {
        Dialog rescheduleDialog = new Dialog(requireContext());
        rescheduleDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rescheduleDialog.setContentView(R.layout.dialog_reschedule_interview);

        if (rescheduleDialog.getWindow() != null) {
            rescheduleDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rescheduleDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView tvPreferredDate = rescheduleDialog.findViewById(R.id.tvPreferredDate);
        TextView tvPreferredTime = rescheduleDialog.findViewById(R.id.tvPreferredTime);
        EditText etReason = rescheduleDialog.findViewById(R.id.etRescheduleReason);
        LinearLayout rowDate = rescheduleDialog.findViewById(R.id.rowPreferredDate);
        LinearLayout rowTime = rescheduleDialog.findViewById(R.id.rowPreferredTime);
        Button btnCancel = rescheduleDialog.findViewById(R.id.btnCancel);
        Button btnSubmit = rescheduleDialog.findViewById(R.id.btnSubmitRequest);
        ImageView ivClose = rescheduleDialog.findViewById(R.id.ivCloseDialog);

        final Calendar calendar = Calendar.getInstance();

        rowDate.setOnClickListener(v -> {
            new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                tvPreferredDate.setText(date);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        rowTime.setOnClickListener(v -> {
            new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                String time = String.format(Locale.getDefault(), "%02d:%02d:00", hourOfDay, minute);
                tvPreferredTime.setText(time);
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
        });

        btnCancel.setOnClickListener(v -> rescheduleDialog.dismiss());
        ivClose.setOnClickListener(v -> rescheduleDialog.dismiss());

        btnSubmit.setOnClickListener(v -> {
            String date = tvPreferredDate.getText().toString();
            String time = tvPreferredTime.getText().toString();
            String reason = etReason.getText().toString();

            if (date.equals("Select a date") || time.equals("Select a time") || reason.isEmpty()) {
                Helpers.showToast("Please fill all fields", requireContext());
                return;
            }

            applicationService.rescheduleInterview(applicant.getApplicationId(), date, time, reason, new ApplicationService.SimpleCallback() {
                @Override
                public void onResponse(String message) {
                    rescheduleDialog.dismiss();
                    parentDialog.dismiss();
                    Helpers.showToast("Reschedule request submitted", requireContext());
                    refreshData();
                }

                @Override
                public void onError(String message) {
                    Helpers.showToast("Error: " + message, requireContext());
                }
            });
        });

        rescheduleDialog.show();
    }

    private void refreshData() {
        int userId = SessionManager.getInstance(requireContext()).getUserId();
        applicantViewModel.fetchApplicantsForUser(userId, requireContext());
    }
}
