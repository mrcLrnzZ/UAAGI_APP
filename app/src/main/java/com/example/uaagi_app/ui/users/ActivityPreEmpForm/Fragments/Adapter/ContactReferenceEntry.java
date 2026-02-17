package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.ContactReference;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ContactReferenceEntry extends RecyclerView.Adapter<ContactReferenceEntry.ContactReferenceViewHolder> {

    private static final String TAG = "ContactReferenceEntry";
    private final List<ContactReference> referenceList;

    public ContactReferenceEntry(List<ContactReference> referenceList) {
        this.referenceList = referenceList;
    }

    @NonNull
    @Override
    public ContactReferenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reference_entry, parent, false);
        return new ContactReferenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactReferenceViewHolder holder, int position) {
        try {
            ContactReference ref = referenceList.get(position);

            if (holder.nameWatcher != null) holder.name.removeTextChangedListener(holder.nameWatcher);
            if (holder.occupationWatcher != null) holder.occupation.removeTextChangedListener(holder.occupationWatcher);
            if (holder.companyWatcher != null) holder.company.removeTextChangedListener(holder.companyWatcher);
            if (holder.contactNumberWatcher != null) holder.contactNumber.removeTextChangedListener(holder.contactNumberWatcher);

            holder.nameWatcher = new SimpleTextWatcher(ref::setName);
            holder.occupationWatcher = new SimpleTextWatcher(ref::setOccupation);
            holder.companyWatcher = new SimpleTextWatcher(ref::setCompany);
            holder.contactNumberWatcher = new SimpleTextWatcher(ref::setPhone);

            holder.name.addTextChangedListener(holder.nameWatcher);
            holder.occupation.addTextChangedListener(holder.occupationWatcher);
            holder.company.addTextChangedListener(holder.companyWatcher);
            holder.contactNumber.addTextChangedListener(holder.contactNumberWatcher);

            Log.d(TAG, "Bound reference at position " + position);

        } catch (Exception e) {
            Log.e(TAG, "Error binding reference at position " + position, e);
        }
    }

    @Override
    public int getItemCount() {
        return referenceList.size();
    }

    static class ContactReferenceViewHolder extends RecyclerView.ViewHolder {

        TextWatcher nameWatcher, occupationWatcher, companyWatcher, contactNumberWatcher;
        TextInputEditText name, occupation, company, contactNumber;

        public ContactReferenceViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tiet_contact_ref);
            occupation = itemView.findViewById(R.id.tiet_occupation);
            company = itemView.findViewById(R.id.tiet_company);
            contactNumber = itemView.findViewById(R.id.tiet_contact_no);
        }
    }
}
