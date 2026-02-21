package com.example.uaagi_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.model.AppliedJob;

import java.util.List;

public class SavedJobsAdapter extends RecyclerView.Adapter<SavedJobsAdapter.VH> {

    public interface OnUnsaveListener { void onUnsave(AppliedJob job); }

    private final Context context;
    private final List<AppliedJob> jobs;
    private final OnUnsaveListener listener;

    public SavedJobsAdapter(Context ctx, List<AppliedJob> jobs, OnUnsaveListener listener) {
        this.context = ctx; this.jobs = jobs; this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_saved_job, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        AppliedJob job = jobs.get(position);
        h.tvJobTitle.setText(job.getJobTitle());
        h.tvCompanyName.setText(job.getCompanyName());
        h.tvLocation.setText(job.getLocation());
        h.btnUnsave.setOnClickListener(v -> { if (listener != null) listener.onUnsave(job); });
    }

    @Override public int getItemCount() { return jobs != null ? jobs.size() : 0; }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvJobTitle, tvCompanyName, tvLocation;
        Button btnUnsave;
        VH(@NonNull View v) {
            super(v);
            tvJobTitle = v.findViewById(R.id.tvJobTitle);
            tvCompanyName = v.findViewById(R.id.tvCompanyName);
            tvLocation = v.findViewById(R.id.tvLocation);
            btnUnsave = v.findViewById(R.id.btnUnsave);
        }
    }
}