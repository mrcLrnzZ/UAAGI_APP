package com.example.uaagi_app.ui.users.PreEmpActvityForm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.utils.UiHelpers;

public class PreEmpFormStep2 extends Fragment {

    private Button btnPrevious, btnNext;
    private LinearLayout educationContainer;
    private Button btnAddEducation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preemp_step_2, container, false);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        educationContainer = view.findViewById(R.id.educationContainer);
        btnAddEducation = view.findViewById(R.id.btnAddEducation);

        btnAddEducation.setOnClickListener(v ->
                UiHelpers.addEntry(R.layout.item_education_entry, educationContainer, requireContext())
        );

        btnPrevious.setOnClickListener(v ->
                ((PreEmpStepperActivity) requireActivity())
                        .previousStep(new PreEmpFormStep1())
        );

        btnNext.setOnClickListener(v ->
                ((PreEmpStepperActivity) requireActivity())
                        .nextStep(new PreEmpFormStep3())
        );

        return view;
    }

}

