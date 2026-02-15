package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Certificate;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;

import java.util.function.Consumer;
import java.util.List;

public class CertificateEntryAdapter extends RecyclerView.Adapter<CertificateEntryAdapter.CertViewHolder> {

    private final List<Certificate> certificationList;

    public CertificateEntryAdapter(List<Certificate> certificationList) {
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

        holder.etName.setText(cert.getName());
        holder.etOrg.setText(cert.getOrganization());
        holder.etDateObtained.setText(cert.getDate());
        holder.etExpiryDate.setText(cert.getExpiry_date());
        holder.etDescription.setText(cert.getDescription());

        holder.etName.addTextChangedListener(new SimpleTextWatcher(cert::setName));
        holder.etOrg.addTextChangedListener(new SimpleTextWatcher(cert::setOrganization));
        holder.etDateObtained.addTextChangedListener(new SimpleTextWatcher(cert::setDate));
        holder.etExpiryDate.addTextChangedListener(new SimpleTextWatcher(cert::setExpiry_date));
        holder.etDescription.addTextChangedListener(new SimpleTextWatcher(cert::setDescription));
    }

    @Override
    public int getItemCount() {
        return certificationList.size();
    }

    static class CertViewHolder extends RecyclerView.ViewHolder {
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

    private static class SimpleTextWatcher implements TextWatcher {
        private final Consumer<String> onTextChanged;

        public SimpleTextWatcher(Consumer<String> onTextChanged) {
            this.onTextChanged = onTextChanged;
        }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            onTextChanged.accept(s.toString());
        }
        @Override public void afterTextChanged(Editable s) {}
    }
}
