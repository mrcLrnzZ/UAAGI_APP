package com.example.uaagi_app.ui.users.PreEmpActvityForm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.utils.UiHelpers;

public class PreEmpFormStep3 extends Fragment {
    private Button btnPrevious, btnNext, btnSubmit;
    private LinearLayout workExperienceContainer;
    private Button btnAddWorkExperience;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preemp_step_3, container, false);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);

        workExperienceContainer = view.findViewById(R.id.workExperienceEntriesContainer);
        btnAddWorkExperience = view.findViewById(R.id.btnAddWorkExperience);


        btnAddWorkExperience.setOnClickListener(v ->
                UiHelpers.addEntry(R.layout.item_work_experience_entry, workExperienceContainer, requireContext())
        );

        btnPrevious.setOnClickListener(v -> {
            ((PreEmpStepperActivity) requireActivity()).previousStep(new PreEmpFormStep2());
        });

        btnNext.setOnClickListener(v -> {
            ((PreEmpStepperActivity) requireActivity()).nextStep(new PreEmpFormStep4());
        });

        return view;
    }



}