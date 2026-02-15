package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Education;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class EducationEntry extends RecyclerView.Adapter<EducationEntry.EducationViewHolder> {
    private final List<Education> educationList;
    public EducationEntry(List<Education> educationList, int viewType ){
        this.educationList = educationList;
    }
    @NonNull
    @Override
    public EducationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_education_entry, parent, false);
        return new EducationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EducationViewHolder holder, int position){
        Education edu = educationList.get(position);

        holder.status.setText(edu.getStatus());
        holder.courseAchievments.setText(edu.getAchievement());
        holder.yearGrad.setText(edu.getGradYear());
        holder.eduLevel.setText(edu.getLevel());
        holder.schoolName.setText(edu.getSchool());

        holder.status.addTextChangedListener(new SimpleTextWatcher(edu::setStatus));
        holder.courseAchievments.addTextChangedListener(new SimpleTextWatcher(edu::setAchievement));
        holder.yearGrad.addTextChangedListener(new SimpleTextWatcher(edu::setGradYear));
        holder.eduLevel.addTextChangedListener(new SimpleTextWatcher(edu::setLevel));
        holder.schoolName.addTextChangedListener(new SimpleTextWatcher(edu::setSchool));
    }
    @Override
    public int getItemCount(){ return educationList.size(); }
    static class EducationViewHolder extends RecyclerView.ViewHolder{
        TextInputEditText   schoolName, courseAchievments, yearGrad;
        AutoCompleteTextView eduLevel, status;
        
        
        public EducationViewHolder(@NonNull View itemView){
            super(itemView);
            eduLevel = itemView.findViewById(R.id.educationLevelInput);
            schoolName = itemView.findViewById(R.id.schoolNameInput);
            yearGrad = itemView.findViewById(R.id.yearGraduatedInput);
            courseAchievments = itemView.findViewById(R.id.courseAchievementsInput);
            status = itemView.findViewById(R.id.statusSpinner);
       }
        
    }
}