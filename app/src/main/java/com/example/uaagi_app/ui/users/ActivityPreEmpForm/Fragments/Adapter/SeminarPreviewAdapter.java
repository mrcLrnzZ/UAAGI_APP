package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Seminar;

import java.util.List;

public class SeminarPreviewAdapter extends RecyclerView.Adapter<SeminarPreviewAdapter.ViewHolder> {
    private final List<Seminar> seminarList;

    public SeminarPreviewAdapter(List<Seminar> seminarList) {
        this.seminarList = seminarList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_seminar_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Seminar seminar = seminarList.get(position);

        holder.type.setText(TextUtils.isEmpty(seminar.getType()) ? "—" : seminar.getType());
        holder.title.setText(TextUtils.isEmpty(seminar.getTitle()) ? "—" : seminar.getTitle());
        holder.organizer.setText(TextUtils.isEmpty(seminar.getOrganizer()) ? "—" : seminar.getOrganizer());
        holder.date.setText(TextUtils.isEmpty(seminar.getDate()) ? "—" : seminar.getDate());
        holder.description.setText(TextUtils.isEmpty(seminar.getDescription()) ? "—" : seminar.getDescription());
    }

    @Override
    public int getItemCount() {
        return seminarList != null ? seminarList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView type, title, organizer, date, description;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tvPreviewSeminarType);
            title = itemView.findViewById(R.id.tvPreviewSeminarTitle);
            organizer = itemView.findViewById(R.id.tvPreviewSeminarOrganizer);
            date = itemView.findViewById(R.id.tvPreviewSeminarDate);
            description = itemView.findViewById(R.id.tvPreviewSeminarDescription);
        }
    }
}
