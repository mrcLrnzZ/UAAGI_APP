package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.ContactReference;
import com.example.uaagi_app.data.model.PreEmploymentForm.GovId;
import com.example.uaagi_app.data.model.PreEmploymentForm.ProfessionalSkills;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.ContactReferenceEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GovIdEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.ArrayList;
import java.util.List;


public class PreEmpFormStep5 extends Fragment {
    private Button btnPrevious, btnNext;
    private Button btnAddGovernmentId;
    private Button btnAddReference;
    private RecyclerView governmentIdContainer;
    private RecyclerView referenceContainer;
    private final List<GovId> governmentIdList = new ArrayList<>();
    private final List<ContactReference> contactReferenceList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preemp_step_5, container, false);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        governmentIdContainer = view.findViewById(R.id.governmentIdContainer);
        referenceContainer = view.findViewById(R.id.referenceContainer);

        btnAddGovernmentId = view.findViewById(R.id.btnAddGovernmentId);
        btnAddReference = view.findViewById(R.id.btnAddReference);

        governmentIdList.add(new GovId());
        contactReferenceList.add(new ContactReference());

        GovIdEntry governmentIdEntryAdapter = new GovIdEntry(governmentIdList);
        ContactReferenceEntry contactReferenceEntryAdapter = new ContactReferenceEntry(contactReferenceList);

        governmentIdContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        referenceContainer.setLayoutManager(new LinearLayoutManager(requireContext()));

        governmentIdContainer.setAdapter(governmentIdEntryAdapter);
        referenceContainer.setAdapter(contactReferenceEntryAdapter);

        btnAddGovernmentId.setOnClickListener(v ->{
            Log.d("PreEmpFormStep5", "Adding new government ID entry");

            GovId newGovId = new GovId();
            governmentIdList.add(newGovId);

            governmentIdEntryAdapter.notifyItemInserted(governmentIdList.size() - 1);
            governmentIdContainer.scrollToPosition(governmentIdList.size() - 1);

        });

        btnAddReference.setOnClickListener(v ->{
            Log.d("PreEmpFormStep5", "Adding new reference entry");

            ContactReference newRef = new ContactReference();
            contactReferenceList.add(newRef);

            contactReferenceEntryAdapter.notifyItemInserted(contactReferenceList.size() - 1);
            referenceContainer.scrollToPosition(contactReferenceList.size() - 1);
        });

        btnPrevious.setOnClickListener(v -> {
            ((PreEmpForm) requireActivity()).previousStep(new PreEmpFormStep4());
        });

        btnNext.setOnClickListener(v -> {
            ((PreEmpForm) requireActivity()).submitForm();
        });



        return view;
    }

}