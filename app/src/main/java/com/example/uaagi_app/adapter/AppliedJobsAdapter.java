package com.example.uaagi_app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.model.AppliedJob;

import java.util.List;

public class AppliedJobsAdapter extends RecyclerView.Adapter<AppliedJobsAdapter.JobViewHolder> {

    private Context context;
    private List<AppliedJob> jobList;
    private OnJobActionListener listener;

    public interface OnJobActionListener {
        void onStatusUpdated(AppliedJob job, String newStatus);
        void onJobArchived(AppliedJob job);
        void onApplicationWithdrawn(AppliedJob job);
        void onViewDetails(AppliedJob job);
    }

    public void updateJobs(List<AppliedJob> newJobs) {
        this.jobList = newJobs;
        notifyDataSetChanged();
    }
    public AppliedJobsAdapter(Context context, List<AppliedJob> jobList, OnJobActionListener listener) {
        this.context = context;
        this.jobList = jobList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_applied_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        AppliedJob job = jobList.get(position);

        holder.tvJobTitle.setText(job.getJobTitle());
        holder.tvCompanyName.setText(job.getCompanyName());
        holder.tvLocation.setText(job.getLocation());
        holder.tvAppliedDate.setText("Applied on " + job.getAppliedPlatform() + " on " + job.getAppliedDate());
        holder.tvEmployerResponse.setText(job.getEmployerResponseTime());

        // Show/hide warning badge
        if (job.isResponseUnlikely()) {
            holder.layoutResponseWarning.setVisibility(View.VISIBLE);
        } else {
            holder.layoutResponseWarning.setVisibility(View.GONE);
        }

        // Update Status Button Click
        holder.btnUpdateStatus.setOnClickListener(v -> showUpdateStatusDialog(job));

        // Menu Dots Click
        holder.ivMenuDots.setOnClickListener(v -> showManageJobDialog(job));
    }

    @Override
    public int getItemCount() {
        return jobList != null ? jobList.size() : 0;
    }

    // Show Update Status Dialog
    private void showUpdateStatusDialog(AppliedJob job) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_status);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);

        // Close button
        ImageView ivCloseDialog = dialog.findViewById(R.id.ivCloseDialog);
        ivCloseDialog.setOnClickListener(v -> dialog.dismiss());

        // Status options
        LinearLayout statusInterviewing = dialog.findViewById(R.id.statusInterviewing);
        LinearLayout statusOfferReceived = dialog.findViewById(R.id.statusOfferReceived);
        LinearLayout statusHired = dialog.findViewById(R.id.statusHired);
        LinearLayout statusNotSelected = dialog.findViewById(R.id.statusNotSelected);
        LinearLayout statusNoLongerInterested = dialog.findViewById(R.id.statusNoLongerInterested);

        statusInterviewing.setOnClickListener(v -> {
            updateJobStatus(job, "interviewing", "Interviewing");
            dialog.dismiss();
        });

        statusOfferReceived.setOnClickListener(v -> {
            updateJobStatus(job, "offer_received", "Offer received");
            dialog.dismiss();
        });

        statusHired.setOnClickListener(v -> {
            updateJobStatus(job, "hired", "Hired");
            dialog.dismiss();
        });

        statusNotSelected.setOnClickListener(v -> {
            updateJobStatus(job, "not_selected", "Not selected by employer");
            dialog.dismiss();
        });

        statusNoLongerInterested.setOnClickListener(v -> {
            updateJobStatus(job, "no_longer_interested", "No longer interested");
            dialog.dismiss();
        });

        dialog.show();
    }

    // Show Manage Job Dialog (3-dot menu)
    private void showManageJobDialog(AppliedJob job) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_manage_job);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);

        // Close button
        ImageView ivCloseManageDialog = dialog.findViewById(R.id.ivCloseManageDialog);
        ivCloseManageDialog.setOnClickListener(v -> dialog.dismiss());

        // Management options
        LinearLayout optionViewDetails = dialog.findViewById(R.id.optionViewDetails);
        LinearLayout optionArchive = dialog.findViewById(R.id.optionArchive);
        LinearLayout optionWithdraw = dialog.findViewById(R.id.optionWithdraw);

        optionViewDetails.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewDetails(job);
            }
            dialog.dismiss();
        });

        optionArchive.setOnClickListener(v -> {
            if (listener != null) {
                listener.onJobArchived(job);
                Toast.makeText(context, "Job archived", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        optionWithdraw.setOnClickListener(v -> {
            if (listener != null) {
                listener.onApplicationWithdrawn(job);
                Toast.makeText(context, "Application withdrawn", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    // Update job status
    private void updateJobStatus(AppliedJob job, String status, String statusName) {
        job.setStatus(status);
        if (listener != null) {
            listener.onStatusUpdated(job, status);
        }
        Toast.makeText(context, "Status updated to: " + statusName, Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView tvJobTitle, tvCompanyName, tvLocation, tvAppliedDate, tvEmployerResponse;
        Button btnUpdateStatus;
        ImageView ivMenuDots;
        LinearLayout layoutResponseWarning, layoutEmployerResponse;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJobTitle = itemView.findViewById(R.id.tvJobTitle);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvAppliedDate = itemView.findViewById(R.id.tvAppliedDate);
            tvEmployerResponse = itemView.findViewById(R.id.tvEmployerResponse);
            btnUpdateStatus = itemView.findViewById(R.id.btnUpdateStatus);
            ivMenuDots = itemView.findViewById(R.id.ivMenuDots);
            layoutResponseWarning = itemView.findViewById(R.id.layoutResponseWarning);
            layoutEmployerResponse = itemView.findViewById(R.id.layoutEmployerResponse);
        }
    }
}