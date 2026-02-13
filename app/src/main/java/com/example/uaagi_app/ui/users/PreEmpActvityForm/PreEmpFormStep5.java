package com.example.uaagi_app.ui.users.PreEmpActvityForm;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.users.HomePage;
import com.example.uaagi_app.ui.utils.UiHelpers;


public class PreEmpFormStep5 extends Fragment {
    private Button btnPrevious, btnNext;
    private LinearLayout governmentIdContainer;
    private LinearLayout referenceContainer;
    private Button btnAddGovernmentId;
    private Button btnAddReference;

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
        btnAddGovernmentId.setOnClickListener(v ->
                UiHelpers.addEntry(R.layout.item_government_id_entry, governmentIdContainer, getContext())
        );

        btnAddReference.setOnClickListener(v ->
                UiHelpers.addEntry(R.layout.item_reference_entry, referenceContainer, getContext())
        );

        btnPrevious.setOnClickListener(v -> {
            ((PreEmpStepperActivity) requireActivity()).previousStep(new PreEmpFormStep4());
        });

        btnNext.setOnClickListener(v -> {
            ((PreEmpStepperActivity) requireActivity()).submitForm();
        });



        return view;
    }

}