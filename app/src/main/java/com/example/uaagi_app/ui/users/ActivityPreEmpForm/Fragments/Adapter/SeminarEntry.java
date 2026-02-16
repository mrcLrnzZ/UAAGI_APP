package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Seminar;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class SeminarEntry extends RecyclerView.Adapter<SeminarEntry.SeminarViewHolder> {

    private final List<Seminar> seminarList;

    public SeminarEntry(List<Seminar> seminarList) {
        this.seminarList = seminarList;
    }

    @NonNull
    @Override
    public SeminarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_seminar_training_entry, parent, false);
        return new SeminarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeminarViewHolder holder, int position) {
        Seminar seminar = seminarList.get(position);

        if (holder.titleWatcher != null) holder.seminarTitle.removeTextChangedListener(holder.titleWatcher);
        if (holder.descWatcher != null) holder.seminarDesc.removeTextChangedListener(holder.descWatcher);
        if (holder.dateWatcher != null) holder.seminarDate.removeTextChangedListener(holder.dateWatcher);
        if (holder.organizerWatcher != null) holder.seminarOrganizer.removeTextChangedListener(holder.organizerWatcher);
        if (holder.typeWatcher != null) holder.seminarType.removeTextChangedListener(holder.typeWatcher);

        holder.titleWatcher = new SimpleTextWatcher(seminar::setTitle);
        holder.descWatcher = new SimpleTextWatcher(seminar::setDescription);
        holder.dateWatcher = new SimpleTextWatcher(seminar::setDate);
        holder.organizerWatcher = new SimpleTextWatcher(seminar::setOrganizer);
        holder.typeWatcher = new SimpleTextWatcher(seminar::setType);

        holder.seminarTitle.addTextChangedListener(holder.titleWatcher);
        holder.seminarDesc.addTextChangedListener(holder.descWatcher);
        holder.seminarDate.addTextChangedListener(holder.dateWatcher);
        holder.seminarOrganizer.addTextChangedListener(holder.organizerWatcher);
        holder.seminarType.addTextChangedListener(holder.typeWatcher);
    }

    @Override
    public int getItemCount() {
        return seminarList.size();
    }

    static class SeminarViewHolder extends RecyclerView.ViewHolder {
        TextWatcher titleWatcher, descWatcher, dateWatcher, organizerWatcher, typeWatcher;

        AutoCompleteTextView seminarType;
        TextInputEditText seminarDesc;
        TextInputEditText seminarTitle;
        TextInputEditText seminarOrganizer;
        TextInputEditText seminarDate;

        public SeminarViewHolder(@NonNull View itemView) {
            super(itemView);
            seminarTitle = itemView.findViewById(R.id.seminarTitleInput);
            seminarDesc = itemView.findViewById(R.id.seminarDescriptionInput);
            seminarType = itemView.findViewById(R.id.seminarTypeInput);
            seminarOrganizer = itemView.findViewById(R.id.organizerInput);
            seminarDate = itemView.findViewById(R.id.dateAttendedInput);
            String[] types = {"Workshop", "Training", "Conference"};
            UiHelpers.dropDownMaker(types, seminarType, itemView.getContext());
        }
    }
}
