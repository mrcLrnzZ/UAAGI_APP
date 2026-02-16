package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.WorkExperience;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class WorkExperienceEntry extends RecyclerView.Adapter<WorkExperienceEntry.WorkExperienceViewHolder> {

    private final List<WorkExperience> workExperienceList;

    public WorkExperienceEntry(List<WorkExperience> workExperienceList) {
        this.workExperienceList = workExperienceList;
    }

    @NonNull
    @Override
    public WorkExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_work_experience_entry, parent, false);
        return new WorkExperienceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkExperienceViewHolder holder, int position) {
        WorkExperience work = workExperienceList.get(position);

        if (holder.companyNameWatcher != null) holder.companyName.removeTextChangedListener(holder.companyNameWatcher);
        if (holder.positionWatcher != null) holder.position.removeTextChangedListener(holder.positionWatcher);
        if (holder.startDateWatcher != null) holder.startDate.removeTextChangedListener(holder.startDateWatcher);
        if (holder.endDateWatcher != null) holder.endDate.removeTextChangedListener(holder.endDateWatcher);
        if (holder.descriptionWatcher != null) holder.description.removeTextChangedListener(holder.descriptionWatcher);

        holder.companyNameWatcher = new SimpleTextWatcher(work::setCompany);
        holder.positionWatcher = new SimpleTextWatcher(work::setPosition);
        holder.startDateWatcher = new SimpleTextWatcher(work::setStartDate);
        holder.endDateWatcher = new SimpleTextWatcher(work::setEndDate);
        holder.descriptionWatcher = new SimpleTextWatcher(work::setDescription);

        holder.companyName.addTextChangedListener(holder.companyNameWatcher);
        holder.position.addTextChangedListener(holder.positionWatcher);
        holder.startDate.addTextChangedListener(holder.startDateWatcher);
        holder.endDate.addTextChangedListener(holder.endDateWatcher);
        holder.description.addTextChangedListener(holder.descriptionWatcher);
    }

    @Override
    public int getItemCount() {
        return workExperienceList.size();
    }

    static class WorkExperienceViewHolder extends RecyclerView.ViewHolder {

        TextWatcher companyNameWatcher, positionWatcher, startDateWatcher, endDateWatcher, descriptionWatcher;
        TextInputEditText companyName;
        TextInputEditText position;
        TextInputEditText startDate;
        TextInputEditText endDate;
        TextInputEditText description;

        public WorkExperienceViewHolder(@NonNull View itemView) {
            super(itemView);

            companyName = itemView.findViewById(R.id.companyNameInput);
            position = itemView.findViewById(R.id.positionInput);
            startDate = itemView.findViewById(R.id.startDateInput);
            endDate = itemView.findViewById(R.id.endDateInput);
            description = itemView.findViewById(R.id.jobDescriptionInput);
        }
    }
}
