package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.ProfessionalSkills;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ProfessionalSkillEntry extends RecyclerView.Adapter<ProfessionalSkillEntry.ProfessionalSkillViewHolder> {

    private final List<ProfessionalSkills> skillList;

    public ProfessionalSkillEntry(List<ProfessionalSkills> skillList) {
        this.skillList = skillList;
    }

    @NonNull
    @Override
    public ProfessionalSkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_professional_skill_entry, parent, false);
        return new ProfessionalSkillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessionalSkillViewHolder holder, int position) {
        ProfessionalSkills skill = skillList.get(position);
        if (holder.skillCategoryWatcher != null) holder.skillCategory.removeTextChangedListener(holder.skillCategoryWatcher);
        if (holder.skillLevelWatcher != null) holder.skillLevel.removeTextChangedListener(holder.skillLevelWatcher);
        if (holder.skillDescWatcher != null) holder.skillDesc.removeTextChangedListener(holder.skillDescWatcher);

        holder.skillCategoryWatcher = new SimpleTextWatcher(skill::setCategory);
        holder.skillLevelWatcher = new SimpleTextWatcher(skill::setLevel);
        holder.skillDescWatcher = new SimpleTextWatcher(skill::setDescription);

        holder.skillCategory.addTextChangedListener(holder.skillCategoryWatcher);
        holder.skillLevel.addTextChangedListener(holder.skillLevelWatcher);
        holder.skillDesc.addTextChangedListener(holder.skillDescWatcher);
    }

    @Override
    public int getItemCount() {
        return skillList.size();
    }

    static class ProfessionalSkillViewHolder extends RecyclerView.ViewHolder {
        SimpleTextWatcher skillCategoryWatcher, skillLevelWatcher, skillDescWatcher;

        AutoCompleteTextView skillCategory;
        AutoCompleteTextView skillLevel;
        TextInputEditText skillDesc;

        public ProfessionalSkillViewHolder(@NonNull View itemView) {
            super(itemView);
            skillCategory = itemView.findViewById(R.id.categoryInput);
            skillLevel = itemView.findViewById(R.id.levelInput);
            skillDesc = itemView.findViewById(R.id.descriptionInput);
            String[] categories = {"Technical", "Soft", "Management"};
            UiHelpers.dropDownMaker(categories, skillCategory, itemView.getContext());
            String[] levels = {"Beginner", "Intermediate", "Advanced"};
            UiHelpers.dropDownMaker(levels, skillLevel, itemView.getContext());
        }
    }
}
