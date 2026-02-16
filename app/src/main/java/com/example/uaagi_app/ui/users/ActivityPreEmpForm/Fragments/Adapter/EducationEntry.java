package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Education;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class EducationEntry extends RecyclerView.Adapter<EducationEntry.EducationViewHolder> {
    private static final String TAG = "EducationEntry";
    private final List<Education> educationList;

    public EducationEntry(List<Education> educationList, int viewType) {
        this.educationList = educationList;
    }

    @NonNull
    @Override
    public EducationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_education_entry, parent, false);
        return new EducationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EducationViewHolder holder, int position) {
        try {
            Education edu = educationList.get(position);

            if (holder.schoolNameWatcher != null) holder.schoolName.removeTextChangedListener(holder.schoolNameWatcher);
            if (holder.courseAchievementWatcher != null) holder.courseAchievement.removeTextChangedListener(holder.courseAchievementWatcher);
            if (holder.yearGradWatcher != null) holder.yearGrad.removeTextChangedListener(holder.yearGradWatcher);
            if (holder.eduLevelWatcher != null) holder.eduLevel.removeTextChangedListener(holder.eduLevelWatcher);
            if (holder.statusWatcher != null) holder.status.removeTextChangedListener(holder.statusWatcher);

            holder.schoolNameWatcher = new SimpleTextWatcher(edu::setSchool);
            holder.courseAchievementWatcher = new SimpleTextWatcher(edu::setAchievement);
            holder.yearGradWatcher = new SimpleTextWatcher(edu::setGradYear);
            holder.eduLevelWatcher = new SimpleTextWatcher(edu::setLevel);
            holder.statusWatcher = new SimpleTextWatcher(edu::setStatus);

            holder.schoolName.addTextChangedListener(holder.schoolNameWatcher);
            holder.courseAchievement.addTextChangedListener(holder.courseAchievementWatcher);
            holder.yearGrad.addTextChangedListener(holder.yearGradWatcher);
            holder.eduLevel.addTextChangedListener(holder.eduLevelWatcher);
            holder.status.addTextChangedListener(holder.statusWatcher);

            Log.d(TAG, "Bound education at position " + position + ": " + (edu.getSchool() != null ? edu.getSchool() : "empty"));

        } catch (Exception e) {
            Log.e(TAG, "Error binding education at position " + position, e);
        }
    }

    @Override
    public int getItemCount() {
        return educationList.size();
    }

    static class EducationViewHolder extends RecyclerView.ViewHolder {
        TextWatcher schoolNameWatcher, courseAchievementWatcher, yearGradWatcher, eduLevelWatcher, statusWatcher;
        TextInputEditText schoolName, courseAchievement, yearGrad;
        AutoCompleteTextView eduLevel, status;
        public EducationViewHolder(@NonNull View itemView) {
            super(itemView);
            eduLevel = itemView.findViewById(R.id.educationLevelInput);
            schoolName = itemView.findViewById(R.id.schoolNameInput);
            yearGrad = itemView.findViewById(R.id.yearGraduatedInput);
            courseAchievement = itemView.findViewById(R.id.courseAchievementsInput);
            status = itemView.findViewById(R.id.statusSpinner);

            String[] levels = {"High School", "College", "Vocational", "Postgraduate"};
            UiHelpers.dropDownMaker(levels, eduLevel, itemView.getContext());

            String[] statuses = {"Graduated", "Ongoing", "Dropped"};
            UiHelpers.dropDownMaker(statuses, status, itemView.getContext());
        }
    }

}
