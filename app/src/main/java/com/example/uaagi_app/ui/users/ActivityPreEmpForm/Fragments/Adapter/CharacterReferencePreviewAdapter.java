package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.ContactReference;

import java.util.List;

public class CharacterReferencePreviewAdapter extends RecyclerView.Adapter<CharacterReferencePreviewAdapter.ViewHolder> {
    private final List<ContactReference> referenceList;

    public CharacterReferencePreviewAdapter(List<ContactReference> referenceList) {
        this.referenceList = referenceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character_reference_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactReference ref = referenceList.get(position);

        holder.name.setText(TextUtils.isEmpty(ref.getName()) ? "—" : ref.getName());
        holder.occupation.setText(TextUtils.isEmpty(ref.getOccupation()) ? "—" : ref.getOccupation());
        holder.company.setText(TextUtils.isEmpty(ref.getCompany()) ? "—" : ref.getCompany());
        holder.phone.setText(TextUtils.isEmpty(ref.getPhone()) ? "—" : ref.getPhone());
    }

    @Override
    public int getItemCount() {
        return referenceList != null ? referenceList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, occupation, company, phone;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvPreviewRefName);
            occupation = itemView.findViewById(R.id.tvPreviewRefOccupation);
            company = itemView.findViewById(R.id.tvPreviewRefCompany);
            phone = itemView.findViewById(R.id.tvPreviewRefPhone);
        }
    }
}
