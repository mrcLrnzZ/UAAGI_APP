package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.ProfessionalSkills;

import java.util.List;

public class ProfessionalSkillsPreviewAdapter extends RecyclerView.Adapter<ProfessionalSkillsPreviewAdapter.ViewHolder> {
    private final List<ProfessionalSkills> skillsList;

    public ProfessionalSkillsPreviewAdapter(List<ProfessionalSkills> skillsList) {
        this.skillsList = skillsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_professional_skill_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProfessionalSkills skill = skillsList.get(position);

        holder.category.setText(TextUtils.isEmpty(skill.getCategory()) ? "—" : skill.getCategory());
        holder.level.setText(TextUtils.isEmpty(skill.getLevel()) ? "—" : skill.getLevel());
        holder.description.setText(TextUtils.isEmpty(skill.getDescription()) ? "—" : skill.getDescription());
    }

    @Override
    public int getItemCount() {
        return skillsList != null ? skillsList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView category, level, description;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.tvPreviewSkillCategory);
            level = itemView.findViewById(R.id.tvPreviewSkillLevel);
            description = itemView.findViewById(R.id.tvPreviewSkillDescription);
        }
    }
}
