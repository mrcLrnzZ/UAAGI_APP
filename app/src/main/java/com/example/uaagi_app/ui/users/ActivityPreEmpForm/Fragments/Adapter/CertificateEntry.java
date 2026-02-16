package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Certificate;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class CertificateEntry extends RecyclerView.Adapter<CertificateEntry.CertViewHolder> {

    private final List<Certificate> certificationList;

    public CertificateEntry(List<Certificate> certificationList) {
        this.certificationList = certificationList;
    }

    @NonNull
    @Override
    public CertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_certification_entry, parent, false);
        return new CertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CertViewHolder holder, int position) {
        Certificate cert = certificationList.get(position);

        if(holder.dateObtainedWatcher != null) holder.etDateObtained.removeTextChangedListener(holder.dateObtainedWatcher);
        if(holder.nameWatcher != null) holder.etName.removeTextChangedListener(holder.nameWatcher);
        if(holder.orgWatcher != null) holder.etOrg.removeTextChangedListener(holder.orgWatcher);
        if(holder.ExpiryDateWatcher != null) holder.etExpiryDate.removeTextChangedListener(holder.ExpiryDateWatcher);
        if(holder.DescriptionWatcher != null) holder.etDescription.removeTextChangedListener(holder.DescriptionWatcher);

        holder.nameWatcher = new SimpleTextWatcher(cert::setName);
        holder.orgWatcher = new SimpleTextWatcher(cert::setOrganization);
        holder.dateObtainedWatcher = new SimpleTextWatcher(cert::setDate);
        holder.ExpiryDateWatcher = new SimpleTextWatcher(cert::setExpiryDate);
        holder.DescriptionWatcher = new SimpleTextWatcher(cert::setDescription);

        holder.etName.addTextChangedListener(holder.nameWatcher);
        holder.etOrg.addTextChangedListener(holder.orgWatcher);
        holder.etDateObtained.addTextChangedListener(holder.dateObtainedWatcher);
        holder.etExpiryDate.addTextChangedListener(holder.ExpiryDateWatcher);
        holder.etDescription.addTextChangedListener(holder.DescriptionWatcher);
        holder.etDateObtained.addTextChangedListener(holder.dateObtainedWatcher);

     }
    @Override
    public int getItemCount() {
        return certificationList.size();
    }

    static class CertViewHolder extends RecyclerView.ViewHolder {
        TextWatcher nameWatcher, orgWatcher, dateObtainedWatcher, ExpiryDateWatcher, DescriptionWatcher;
        TextInputEditText etName, etOrg, etDateObtained, etExpiryDate, etDescription;

        public CertViewHolder(@NonNull View itemView) {
            super(itemView);
            etName = itemView.findViewById(R.id.etCertificationName);
            etOrg = itemView.findViewById(R.id.etIssuingOrg);
            etDateObtained = itemView.findViewById(R.id.etDateObtained);
            etExpiryDate = itemView.findViewById(R.id.etExpiryDate);
            etDescription = itemView.findViewById(R.id.etDescription);
        }
    }

}
