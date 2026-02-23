package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Certificate;
import com.example.uaagi_app.data.model.PreEmploymentForm.Qualification;
import com.example.uaagi_app.data.model.PreEmploymentForm.Seminar;
import com.example.uaagi_app.data.model.PreEmploymentForm.ProfessionalSkills;
import com.example.uaagi_app.data.viewmodel.PreEmpFormViewModel;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.CertificateEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.ProfessionalSkillEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.QualificationEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.SeminarEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.EntryHandler.EntryHandler;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.ArrayList;
import java.util.List;

public class PreEmpFormStep4 extends BaseFormStepFragment {
    private Button btnPrevious, btnNext;
    private RecyclerView professionalSkillsContainer;
    private RecyclerView certificationContainer;
    private RecyclerView qualificationContainer;
    private RecyclerView seminarsContainer;
    private final List<ProfessionalSkills> professionalSkillList = new ArrayList<>();
    private final List<Certificate> certificationList = new ArrayList<>();
    private final List<Qualification> qualificationList = new ArrayList<>();
    private final List<Seminar> seminarList = new ArrayList<>();
    private PreEmpFormViewModel viewModel;

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

        viewModel = new ViewModelProvider(requireActivity()).get(PreEmpFormViewModel.class);

        professionalSkillList.add(new ProfessionalSkills());
        certificationList.add(new Certificate());
        qualificationList.add(new Qualification());
        seminarList.add(new Seminar());

        Button btnAddProfessionalSkill = view.findViewById(R.id.btnAddWorkExperience);
        Button btnAddCertification = view.findViewById(R.id.btnAddCert);
        Button btnAddQualification = view.findViewById(R.id.btnAddQual);
        Button btnAddSeminar = view.findViewById(R.id.btnAddSeminar);
        Button btnRemoveProfessionalSkill = view.findViewById(R.id.btnRemoveWorkExperience);
        Button btnRemoveCertification = view.findViewById(R.id.btnRemoveCert);
        Button btnRemoveQualification = view.findViewById(R.id.btnRemoveQual);
        Button btnRemoveSeminar = view.findViewById(R.id.btnRemoveSeminar);

        EntryHandler.loadData(professionalSkillList, viewModel.getValue().getProfessionalSkills(), ProfessionalSkills::new, 1);
        EntryHandler.loadData(certificationList, viewModel.getValue().getCertificates(), Certificate::new, 1);
        EntryHandler.loadData(qualificationList, viewModel.getValue().getQualifications(), Qualification::new,1);
        EntryHandler.loadData(seminarList, viewModel.getValue().getSeminars(), Seminar::new,1);

        UiHelpers.updateRemoveButtonVisibility(professionalSkillList, btnRemoveProfessionalSkill, 1);
        UiHelpers.updateRemoveButtonVisibility(certificationList, btnRemoveCertification, 1);
        UiHelpers.updateRemoveButtonVisibility(qualificationList, btnRemoveQualification, 1);
        UiHelpers.updateRemoveButtonVisibility(seminarList, btnRemoveSeminar, 1);

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
            EntryHandler.addEntry(professionalSkillList, new ProfessionalSkills(), professionalSkillsContainer, professionalSkillEntryAdapter, 10);
        });

        btnAddCertification.setOnClickListener(v -> {
            EntryHandler.addEntry(certificationList, new Certificate(), certificationContainer, certificateEntryAdapter, 10);
        });

        btnAddQualification.setOnClickListener(v -> {
            EntryHandler.addEntry(qualificationList, new Qualification(), qualificationContainer, qualificationEntryAdapter, 10);
        });

        btnAddSeminar.setOnClickListener(v -> {
            EntryHandler.addEntry(seminarList, new Seminar(), seminarsContainer, seminarEntryAdapter, 10);
        });

        btnRemoveProfessionalSkill.setOnClickListener(v -> {
            EntryHandler.removeEntry(professionalSkillList, professionalSkillsContainer, professionalSkillEntryAdapter, requireContext(), 1);
        });

        btnRemoveCertification.setOnClickListener(v -> {
            EntryHandler.removeEntry(certificationList, certificationContainer, certificateEntryAdapter, requireContext(), 1);
        });

        btnRemoveQualification.setOnClickListener(v -> {
            EntryHandler.removeEntry(qualificationList, qualificationContainer, qualificationEntryAdapter, requireContext(), 1);
        });

        btnRemoveSeminar.setOnClickListener(v -> {
            EntryHandler.removeEntry(seminarList, seminarsContainer, seminarEntryAdapter, requireContext(), 1);
        });


        btnPrevious.setOnClickListener(v -> {
            saveFormData();
            ((PreEmpForm) requireActivity()).previousStep();
        });

        btnNext.setOnClickListener(v -> {
            saveFormData();
            ((PreEmpForm) requireActivity()).nextStep();
        });

        return view;
    }

    @Override
    public void saveFormData() {
        EntryHandler.saveData(viewModel, form -> form.setProfessionalSkills(professionalSkillList));
        EntryHandler.saveData(viewModel, form -> form.setCertificates(certificationList));
        EntryHandler.saveData(viewModel, form -> form.setQualifications(qualificationList));
        EntryHandler.saveData(viewModel, form -> form.setSeminars(seminarList));
    }

    @Override
    public void onResume() {
        super.onResume();
        professionalSkillList.clear();
        certificationList.clear();
        qualificationList.clear();
        seminarList.clear();

        EntryHandler.loadData(professionalSkillList, viewModel.getValue().getProfessionalSkills(), ProfessionalSkills::new, 1);
        EntryHandler.loadData(certificationList, viewModel.getValue().getCertificates(), Certificate::new, 1);
        EntryHandler.loadData(qualificationList, viewModel.getValue().getQualifications(), Qualification::new, 1);
        EntryHandler.loadData(seminarList, viewModel.getValue().getSeminars(), Seminar::new, 1);

        if (professionalSkillsContainer.getAdapter() != null) {
            professionalSkillsContainer.getAdapter().notifyDataSetChanged();
        }
        if (certificationContainer.getAdapter() != null) {
            certificationContainer.getAdapter().notifyDataSetChanged();
        }
        if (qualificationContainer.getAdapter() != null) {
            qualificationContainer.getAdapter().notifyDataSetChanged();
        }
        if (seminarsContainer.getAdapter() != null) {
            seminarsContainer.getAdapter().notifyDataSetChanged();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EntryHandler.saveData(viewModel, form -> form.setProfessionalSkills(new ArrayList<>(professionalSkillList)));
        EntryHandler.saveData(viewModel, form -> form.setCertificates(new ArrayList<>(certificationList)));
        EntryHandler.saveData(viewModel, form -> form.setQualifications(new ArrayList<>(qualificationList)));
        EntryHandler.saveData(viewModel, form -> form.setSeminars(new ArrayList<>(seminarList)));
    }

}