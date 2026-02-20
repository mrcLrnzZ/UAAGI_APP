package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Certificate;

import java.util.List;

public class CertificatePreviewAdapter extends RecyclerView.Adapter<CertificatePreviewAdapter.ViewHolder> {
    private final List<Certificate> certificateList;

    public CertificatePreviewAdapter(List<Certificate> certificateList) {
        this.certificateList = certificateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_certificate_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Certificate cert = certificateList.get(position);

        holder.name.setText(TextUtils.isEmpty(cert.getName()) ? "—" : cert.getName());
        holder.organization.setText(TextUtils.isEmpty(cert.getOrganization()) ? "—" : cert.getOrganization());
        holder.date.setText(TextUtils.isEmpty(cert.getDate()) ? "—" : cert.getDate());
        holder.expiry.setText(TextUtils.isEmpty(cert.getExpiryDate()) ? "—" : cert.getExpiryDate());
        holder.description.setText(TextUtils.isEmpty(cert.getDescription()) ? "—" : cert.getDescription());
    }

    @Override
    public int getItemCount() {
        return certificateList != null ? certificateList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, organization, date, expiry, description;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvPreviewCertName);
            organization = itemView.findViewById(R.id.tvPreviewCertOrganization);
            date = itemView.findViewById(R.id.tvPreviewCertDate);
            expiry = itemView.findViewById(R.id.tvPreviewCertExpiry);
            description = itemView.findViewById(R.id.tvPreviewCertDescription);
        }
    }
}
