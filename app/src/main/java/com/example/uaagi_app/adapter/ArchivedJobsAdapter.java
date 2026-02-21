package com.example.uaagi_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.model.AppliedJob;

import java.util.List;

public class ArchivedJobsAdapter extends RecyclerView.Adapter<ArchivedJobsAdapter.VH> {

    private final Context context;
    private final List<AppliedJob> jobs;

    public ArchivedJobsAdapter(Context ctx, List<AppliedJob> jobs) {
        this.context = ctx; this.jobs = jobs;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_archived_job, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        AppliedJob job = jobs.get(position);
        h.tvJobTitle.setText(job.getJobTitle());
        h.tvCompanyName.setText(job.getCompanyName());
        h.tvArchivedDate.setText("Archived on " + job.getAppliedDate());
    }

    @Override public int getItemCount() { return jobs != null ? jobs.size() : 0; }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvJobTitle, tvCompanyName, tvArchivedDate;
        VH(@NonNull View v) {
            super(v);
            tvJobTitle = v.findViewById(R.id.tvJobTitle);
            tvCompanyName = v.findViewById(R.id.tvCompanyName);
            tvArchivedDate = v.findViewById(R.id.tvArchivedDate);
        }
    }
}