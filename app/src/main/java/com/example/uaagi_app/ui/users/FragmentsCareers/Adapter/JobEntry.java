package com.example.uaagi_app.ui.users.FragmentsCareers.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.dto.JobFetchResponse;

import java.util.List;

public class JobEntry extends RecyclerView.Adapter<JobEntry.JobViewHolder> {
    private static final String TAG = "JobEntry";
    private final List<JobFetchResponse> jobFetchResponseList;
    private final int limit;
    private OnJobClickListener listener;
    public JobEntry(List<JobFetchResponse> jobFetchResponseList, int viewType, int limit, OnJobClickListener listener){
        this.jobFetchResponseList = jobFetchResponseList;
        this.limit = limit;
        this.listener = listener;
    }
    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job_card, parent, false);
        return new JobViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position){
        try{
            JobFetchResponse job = jobFetchResponseList.get(position);
            String jobTitle = job.getJobTitle() != null ? job.getJobTitle() : "N/A";
            String company = (job.getCompany() != null && job.getCompany().getDisplayName() != null)
                    ? job.getCompany().getDisplayName() : "N/A";
            String location = job.getLocation() != null ? job.getLocation() : "N/A";
            String salary = "₱" + job.getMinSalary() + " – ₱" + job.getMaxSalary();
            String jobType = job.getJobType() != null ? job.getJobType().toString() : "N/A";
            String experienceLevel = job.getExperienceLevel() != null ? job.getExperienceLevel().toString() : "N/A";
            String shift = job.getRemoteOption() != null ? job.getRemoteOption().toString() : "N/A";
            String payTag = "✓ 13th Month Pay";

            holder.tvJobTitle.setText(jobTitle);
            holder.tvCompany.setText(company);
            holder.tvLocation.setText(location);
            holder.tvSalary.setText(salary);
            holder.tvJobType.setText(jobType);
            holder.tvExperienceLevel.setText(experienceLevel);
            holder.tvShift.setText(shift);
            holder.tvPayTag.setText(payTag);

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onJobClick(job);
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Error binding job at position " + position, e);
        }
    }
    @Override
    public int getItemCount() {
        return limit > 0 ? Math.min(jobFetchResponseList.size(), limit)
                : jobFetchResponseList.size();
    }

    public interface OnJobClickListener {
        void onJobClick(JobFetchResponse job);
    }

    static class JobViewHolder extends RecyclerView.ViewHolder{
        TextView tvJobTitle, tvCompany, tvLocation, tvSalary,
                 tvJobType, tvExperienceLevel, tvShift, tvPayTag;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
             tvJobTitle = itemView.findViewById(R.id.tvJobTitle);
             tvCompany = itemView.findViewById(R.id.tvCompany);
             tvLocation = itemView.findViewById(R.id.tvLocation);
             tvSalary = itemView.findViewById(R.id.tvSalary);
             tvJobType = itemView.findViewById(R.id.tvJobType);
             tvExperienceLevel = itemView.findViewById(R.id.tvExperienceLevel);
             tvShift = itemView.findViewById(R.id.tvShift);
             tvPayTag = itemView.findViewById(R.id.tvPayTag);
        }
    }
}
