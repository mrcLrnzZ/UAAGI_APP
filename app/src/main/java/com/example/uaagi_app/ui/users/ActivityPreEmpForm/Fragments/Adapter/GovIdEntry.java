package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.GovId;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class GovIdEntry extends RecyclerView.Adapter<GovIdEntry.GovIdViewHolder> {

    private final List<GovId> govIdList;

    public GovIdEntry(List<GovId> govIdList) {
        this.govIdList = govIdList;
    }

    @NonNull
    @Override
    public GovIdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_government_id_entry, parent, false);
        return new GovIdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GovIdViewHolder holder, int position) {
        GovId govId = govIdList.get(position);

        if (holder.idTypeWatcher != null) holder.idType.removeTextChangedListener(holder.idTypeWatcher);
        if (holder.idNumberWatcher != null) holder.idNumber.removeTextChangedListener(holder.idNumberWatcher);

        holder.idTypeWatcher = new SimpleTextWatcher(govId::setType);
        holder.idNumberWatcher = new SimpleTextWatcher(govId::setNumber);

        holder.idType.addTextChangedListener(holder.idTypeWatcher);
        holder.idNumber.addTextChangedListener(holder.idNumberWatcher);
    }

    @Override
    public int getItemCount() {
        return govIdList.size();
    }

    static class GovIdViewHolder extends RecyclerView.ViewHolder {
        TextWatcher idTypeWatcher, idNumberWatcher;
        TextInputEditText  idNumber;
        AutoCompleteTextView idType;

        public GovIdViewHolder(@NonNull View itemView) {
            super(itemView);

            idNumber = itemView.findViewById(R.id.idNumberInput);
            idType = itemView.findViewById(R.id.idTypeInput);
            String[] idTypes = {"SSS", "TIN", "PAGIBIG", "PhilHealth"};
            UiHelpers.dropDownMaker(idTypes, idType, itemView.getContext());
        }
    }
}
