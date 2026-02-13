package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.ui.utils.UiHelpers;

public class PreEmpFormStep4 extends Fragment {
    private Button btnPrevious, btnNext;
    private LinearLayout professionalSkillsContainer;
    private LinearLayout certificationContainer;
    private LinearLayout qualificationContainer;
    private LinearLayout seminarsTrainingContainer;

    private Button btnAddProfessionalSkill;
    private Button btnAddCertification;
    private Button btnAddQualification;
    private Button btnAddSeminarTraining;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preemp_step_4, container, false);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        professionalSkillsContainer = view.findViewById(R.id.professionalEntryContainer);
        certificationContainer = view.findViewById(R.id.certificationsContainer);
        qualificationContainer = view.findViewById(R.id.qualificationContainer);
        seminarsTrainingContainer = view.findViewById(R.id.seminarContainer);

        btnAddProfessionalSkill = view.findViewById(R.id.btnAddWorkExperience);
        btnAddCertification = view.findViewById(R.id.btnAddCert);
        btnAddQualification = view.findViewById(R.id.btnAddQual);
        btnAddSeminarTraining = view.findViewById(R.id.btnAddSeminar);

        btnAddProfessionalSkill.setOnClickListener(v ->
                UiHelpers.addEntry(R.layout.item_professional_skill_entry, professionalSkillsContainer, getContext())
        );

        btnAddCertification.setOnClickListener(v ->
                UiHelpers.addEntry(R.layout.item_certification_entry, certificationContainer, getContext())
        );

        btnAddQualification.setOnClickListener(v ->
                UiHelpers.addEntry(R.layout.item_qualification_entry, qualificationContainer, getContext())
        );

        btnAddSeminarTraining.setOnClickListener(v ->
                UiHelpers.addEntry(R.layout.item_seminar_training_entry, seminarsTrainingContainer, getContext())
        );

        btnPrevious.setOnClickListener(v -> {
            ((PreEmpForm) requireActivity()).previousStep(new PreEmpFormStep3());
        });

        btnNext.setOnClickListener(v -> {
            ((PreEmpForm) requireActivity()).nextStep(new PreEmpFormStep5());
        });

        return view;
    }
}