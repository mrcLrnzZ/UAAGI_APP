package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.GovId;

import java.util.List;
@Deprecated
public class GovIdPreviewAdapter extends RecyclerView.Adapter<GovIdPreviewAdapter.ViewHolder> {
    private final List<GovId> govIdList;

    public GovIdPreviewAdapter(List<GovId> govIdList) {
        this.govIdList = govIdList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gov_id_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GovId govId = govIdList.get(position);

        holder.type.setText(TextUtils.isEmpty(govId.getType()) ? "—" : govId.getType());
        holder.number.setText(TextUtils.isEmpty(govId.getNumber()) ? "—" : govId.getNumber());
    }

    @Override
    public int getItemCount() {
        return govIdList != null ? govIdList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView type, number;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tvPreviewGovIdType);
            number = itemView.findViewById(R.id.tvPreviewGovIdNumber);
        }
    }
}
