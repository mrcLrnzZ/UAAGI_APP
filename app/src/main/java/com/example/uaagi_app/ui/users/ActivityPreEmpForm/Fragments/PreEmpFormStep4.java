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
import com.example.uaagi_app.data.model.PreEmploymentForm.Certificate;
import com.example.uaagi_app.data.model.PreEmploymentForm.Education;
import com.example.uaagi_app.data.model.PreEmploymentForm.Qualification;
import com.example.uaagi_app.data.model.PreEmploymentForm.Seminar;
import com.example.uaagi_app.data.model.PreEmploymentForm.ProfessionalSkills;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.CertificateEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.ProfessionalSkillEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.QualificationEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.SeminarEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.ArrayList;
import java.util.List;

public class PreEmpFormStep4 extends Fragment {
    private Button btnPrevious, btnNext;
    private RecyclerView professionalSkillsContainer;
    private RecyclerView certificationContainer;
    private RecyclerView qualificationContainer;
    private RecyclerView seminarsContainer;
    private final List<ProfessionalSkills> professionalSkillList = new ArrayList<>();
    private final List<Certificate> certificationList = new ArrayList<>();
    private final List<Qualification> qualificationList = new ArrayList<>();
    private final List<Seminar> seminarList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preemp_step_4, container, false);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        professionalSkillsContainer = view.findViewById(R.id.ProfessionalSkillEntryContainer);
        certificationContainer = view.findViewById(R.id.certificationsContainer);
        qualificationContainer = view.findViewById(R.id.qualificationContainer);
        seminarsContainer = view.findViewById(R.id.seminarContainer);

        professionalSkillList.add(new ProfessionalSkills());
        certificationList.add(new Certificate());
        qualificationList.add(new Qualification());
        seminarList.add(new Seminar());

        Button btnAddProfessionalSkill = view.findViewById(R.id.btnAddWorkExperience);
        Button btnAddCertification = view.findViewById(R.id.btnAddCert);
        Button btnAddQualification = view.findViewById(R.id.btnAddQual);
        Button btnAddSeminar = view.findViewById(R.id.btnAddSeminar);

        ProfessionalSkillEntry professionalSkillEntryAdapter = new ProfessionalSkillEntry(professionalSkillList);
        CertificateEntry certificateEntryAdapter = new CertificateEntry(certificationList);
        QualificationEntry qualificationEntryAdapter = new QualificationEntry(qualificationList);
        SeminarEntry seminarEntryAdapter = new SeminarEntry(seminarList);

        professionalSkillsContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        certificationContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        qualificationContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        seminarsContainer.setLayoutManager(new LinearLayoutManager(requireContext()));

        professionalSkillsContainer.setAdapter(professionalSkillEntryAdapter);
        certificationContainer.setAdapter(certificateEntryAdapter);
        qualificationContainer.setAdapter(qualificationEntryAdapter);
        seminarsContainer.setAdapter(seminarEntryAdapter);

        btnAddProfessionalSkill.setOnClickListener(v -> {
            Log.d("PreEmpFormStep4", "Adding new education entry");

            ProfessionalSkills newSkill = new ProfessionalSkills();
            professionalSkillList.add(newSkill);

            professionalSkillEntryAdapter.notifyItemInserted(professionalSkillList.size() - 1);
            professionalSkillsContainer.scrollToPosition(professionalSkillList.size() - 1);
        });

        btnAddCertification.setOnClickListener(v -> {
            Log.d("PreEmpFormStep4", "Adding new certification entry");

            Certificate newCert = new Certificate();
            certificationList.add(newCert);

            certificateEntryAdapter.notifyItemInserted(certificationList.size() - 1);
            certificationContainer.scrollToPosition(certificationList.size() - 1);
        });

        btnAddQualification.setOnClickListener(v -> {
            Log.d("PreEmpFormStep4", "Adding new qualification entry");

            Qualification newQual = new Qualification();
            qualificationList.add(newQual);

            qualificationEntryAdapter.notifyItemInserted(qualificationList.size() - 1);
            qualificationContainer.scrollToPosition(qualificationList.size() - 1);
        });

        btnAddSeminar.setOnClickListener(v -> {
            Log.d("PreEmpFormStep4", "Adding new seminar entry");

            Seminar newSem = new Seminar();
            seminarList.add(newSem);

            seminarEntryAdapter.notifyItemInserted(seminarList.size() - 1);
            seminarsContainer.scrollToPosition(seminarList.size() - 1);
        });

        btnPrevious.setOnClickListener(v -> {
            ((PreEmpForm) requireActivity()).previousStep(new PreEmpFormStep3());
        });

        btnNext.setOnClickListener(v -> {
            ((PreEmpForm) requireActivity()).nextStep(new PreEmpFormStep5());
        });

        return view;
    }
}