package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Qualification;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class QualificationEntry extends RecyclerView.Adapter<QualificationEntry.QualificationViewHolder> {

    private final List<Qualification> qualificationList;

    public QualificationEntry(List<Qualification> qualificationList) {
        this.qualificationList = qualificationList;
    }

    @NonNull
    @Override
    public QualificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_qualification_entry, parent, false);
        return new QualificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QualificationViewHolder holder, int position) {
        Qualification qualification = qualificationList.get(position);

        if (holder.titleWatcher != null) holder.qualificationTitleInput.removeTextChangedListener(holder.titleWatcher);
        if (holder.descriptionWatcher != null) holder.qualificationDescriptionInput.removeTextChangedListener(holder.descriptionWatcher);
        if (holder.dateWatcher != null) holder.dateReceiveInput.removeTextChangedListener(holder.dateWatcher);
        if (holder.authorityWatcher != null) holder.issuingAuthorityInput.removeTextChangedListener(holder.authorityWatcher);
        if (holder.typeWatcher != null) holder.qualificationTypeInput.removeTextChangedListener(holder.typeWatcher);

        holder.titleWatcher = new SimpleTextWatcher(qualification::setTitle);
        holder.descriptionWatcher = new SimpleTextWatcher(qualification::setDescription);
        holder.dateWatcher = new SimpleTextWatcher(qualification::setDate);
        holder.authorityWatcher = new SimpleTextWatcher(qualification::setAuthority);
        holder.typeWatcher = new SimpleTextWatcher(qualification::setType);

        holder.qualificationTitleInput.addTextChangedListener(holder.titleWatcher);
        holder.qualificationDescriptionInput.addTextChangedListener(holder.descriptionWatcher);
        holder.dateReceiveInput.addTextChangedListener(holder.dateWatcher);
        holder.issuingAuthorityInput.addTextChangedListener(holder.authorityWatcher);
        holder.qualificationTypeInput.addTextChangedListener(holder.typeWatcher);
    }

    @Override
    public int getItemCount() {
        return qualificationList.size();
    }

    static class QualificationViewHolder extends RecyclerView.ViewHolder {
        TextWatcher titleWatcher, descriptionWatcher, dateWatcher, authorityWatcher, typeWatcher;
        AutoCompleteTextView qualificationTypeInput;
        TextInputEditText qualificationTitleInput,
                qualificationDescriptionInput,
                issuingAuthorityInput,
                dateReceiveInput;

        public QualificationViewHolder(@NonNull View itemView) {
            super(itemView);
            qualificationTitleInput = itemView.findViewById(R.id.qualificationTitleInput);
            issuingAuthorityInput = itemView.findViewById(R.id.issuingAuthorityInput);
            dateReceiveInput = itemView.findViewById(R.id.dateReceivedInput);
            qualificationTypeInput = itemView.findViewById(R.id.qualificationTypeInput);
            qualificationDescriptionInput = itemView.findViewById(R.id.qualificationDescriptionInput);
            String[] types = {"Certification", "License", "Degree"};
            UiHelpers.dropDownMaker(types, qualificationTypeInput, itemView.getContext());
        }
    }
}
