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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.AppliedJob;

import java.util.List;

public class AppliedJobsAdapter extends RecyclerView.Adapter<AppliedJobsAdapter.JobViewHolder> {

    private final Context context;
    private final List<AppliedJob> jobList;
    private final OnJobActionListener listener;

    public interface OnJobActionListener {
        void onStatusUpdated(AppliedJob job, String newStatus);
        void onJobArchived(AppliedJob job);
        void onApplicationWithdrawn(AppliedJob job);
        void onViewDetails(AppliedJob job);
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

        if (holder.tvJobTitle != null) holder.tvJobTitle.setText(job.getJobTitle());
        if (holder.tvCompanyName != null) holder.tvCompanyName.setText(job.getCompanyName());
        if (holder.tvLocation != null) holder.tvLocation.setText(job.getLocation());
        if (holder.tvAppliedDate != null) holder.tvAppliedDate.setText("Applied on " + job.getAppliedPlatform() + " on " + job.getAppliedDate());
        if (holder.tvEmployerResponse != null) holder.tvEmployerResponse.setText(job.getEmployerResponseTime());

        // Show/hide warning badge
        if (holder.layoutResponseWarning != null) {
            if (job.isResponseUnlikely()) {
                holder.layoutResponseWarning.setVisibility(View.VISIBLE);
            } else {
                holder.layoutResponseWarning.setVisibility(View.GONE);
            }
        }

        // Hide update status button as it is currently not supported by the shared dialog layout
        if (holder.btnUpdateStatus != null) {
            holder.btnUpdateStatus.setVisibility(View.GONE);
        }

        // Menu Dots Click
        if (holder.ivMenuDots != null) {
            holder.ivMenuDots.setOnClickListener(v -> showManageJobDialog(job));
        }
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onViewDetails(job);
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    // Show Manage Job Dialog (3-dot menu)
    private void showManageJobDialog(AppliedJob job) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_manage_job);
        
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.BOTTOM);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }

        ImageView ivCloseManageDialog = dialog.findViewById(R.id.ivCloseManageDialog);
        if (ivCloseManageDialog != null) ivCloseManageDialog.setOnClickListener(v -> dialog.dismiss());

        LinearLayout optionViewDetails = dialog.findViewById(R.id.optionViewDetails);
        LinearLayout optionArchive = dialog.findViewById(R.id.optionArchive);
        LinearLayout optionWithdraw = dialog.findViewById(R.id.optionWithdraw);

        if (optionViewDetails != null) {
            optionViewDetails.setOnClickListener(v -> {
                if (listener != null) listener.onViewDetails(job);
                dialog.dismiss();
            });
        }

        if (optionArchive != null) {
            optionArchive.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onJobArchived(job);
                }
                dialog.dismiss();
            });
        }

        if (optionWithdraw != null) {
            optionWithdraw.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onApplicationWithdrawn(job);
                }
                dialog.dismiss();
            });
        }

        dialog.show();
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
