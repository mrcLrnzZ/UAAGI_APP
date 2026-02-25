package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.WorkExperience;

import java.util.List;
@Deprecated
public class WorkExperiencePreviewAdapter extends RecyclerView.Adapter<WorkExperiencePreviewAdapter.ViewHolder> {
    private final List<WorkExperience> workExperienceList;

    public WorkExperiencePreviewAdapter(List<WorkExperience> workExperienceList) {
        this.workExperienceList = workExperienceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_work_experience_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkExperience work = workExperienceList.get(position);

        holder.company.setText(TextUtils.isEmpty(work.getCompany()) ? "—" : work.getCompany());
        holder.position.setText(TextUtils.isEmpty(work.getPosition()) ? "—" : work.getPosition());
        holder.description.setText(TextUtils.isEmpty(work.getDescription()) ? "—" : work.getDescription());

        // Format duration
        String duration = "";
        if (!TextUtils.isEmpty(work.getStartDate()) && !TextUtils.isEmpty(work.getEndDate())) {
            duration = work.getStartDate() + " - " + work.getEndDate();
        } else if (!TextUtils.isEmpty(work.getStartDate())) {
            duration = work.getStartDate() + " - Present";
        } else {
            duration = "—";
        }
        holder.duration.setText(duration);
    }

    @Override
    public int getItemCount() {
        return workExperienceList != null ? workExperienceList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView company, position, duration, description;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            company = itemView.findViewById(R.id.tvPreviewWorkCompany);
            position = itemView.findViewById(R.id.tvPreviewWorkPosition);
            duration = itemView.findViewById(R.id.tvPreviewWorkDuration);
            description = itemView.findViewById(R.id.tvPreviewWorkDescription);
        }
    }
}
