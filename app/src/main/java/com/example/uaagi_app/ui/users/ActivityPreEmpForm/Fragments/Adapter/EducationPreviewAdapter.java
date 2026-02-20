package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Education;

import java.util.List;

public class EducationPreviewAdapter extends RecyclerView.Adapter<EducationPreviewAdapter.ViewHolder> {
    private final List<Education> educationList;

    public EducationPreviewAdapter(List<Education> educationList) {
        this.educationList = educationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_education_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Education education = educationList.get(position);

        holder.level.setText(TextUtils.isEmpty(education.getLevel()) ? "—" : education.getLevel());
        holder.school.setText(TextUtils.isEmpty(education.getSchool()) ? "—" : education.getSchool());
        holder.year.setText(TextUtils.isEmpty(education.getGradYear()) ? "—" : education.getGradYear());
        holder.status.setText(TextUtils.isEmpty(education.getStatus()) ? "—" : education.getStatus());
        holder.achievement.setText(TextUtils.isEmpty(education.getAchievement()) ? "—" : education.getAchievement());
    }

    @Override
    public int getItemCount() {
        return educationList != null ? educationList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView level, school, year, status, achievement;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            level = itemView.findViewById(R.id.tvPreviewEducationLevel);
            school = itemView.findViewById(R.id.tvPreviewEducationSchool);
            year = itemView.findViewById(R.id.tvPreviewEducationYear);
            status = itemView.findViewById(R.id.tvPreviewEducationStatus);
            achievement = itemView.findViewById(R.id.tvPreviewEducationAchievement);
        }
    }
}
