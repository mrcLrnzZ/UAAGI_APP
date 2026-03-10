package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Qualification;

import java.util.List;
@Deprecated
public class QualificationPreviewAdapter extends RecyclerView.Adapter<QualificationPreviewAdapter.ViewHolder> {
    private final List<Qualification> qualificationList;

    public QualificationPreviewAdapter(List<Qualification> qualificationList) {
        this.qualificationList = qualificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_qualification_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Qualification qual = qualificationList.get(position);

        holder.type.setText(TextUtils.isEmpty(qual.getType()) ? "—" : qual.getType());
        holder.title.setText(TextUtils.isEmpty(qual.getTitle()) ? "—" : qual.getTitle());
        holder.authority.setText(TextUtils.isEmpty(qual.getAuthority()) ? "—" : qual.getAuthority());
        holder.date.setText(TextUtils.isEmpty(qual.getDate()) ? "—" : qual.getDate());
        holder.description.setText(TextUtils.isEmpty(qual.getDescription()) ? "—" : qual.getDescription());
    }

    @Override
    public int getItemCount() {
        return qualificationList != null ? qualificationList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView type, title, authority, date, description;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tvPreviewQualType);
            title = itemView.findViewById(R.id.tvPreviewQualTitle);
            authority = itemView.findViewById(R.id.tvPreviewQualAuthority);
            date = itemView.findViewById(R.id.tvPreviewQualDate);
            description = itemView.findViewById(R.id.tvPreviewQualDescription);
        }
    }
}
