package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Certificate;
import com.example.uaagi_app.data.model.PreEmploymentForm.Qualification;
import com.example.uaagi_app.data.model.PreEmploymentForm.Seminar;
import com.example.uaagi_app.data.model.PreEmploymentForm.ProfessionalSkills;
import com.example.uaagi_app.data.viewmodel.PreEmpFormViewModel;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.EntryHandler.EntryHandler;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.google.android.material.textfield.TextInputEditText;

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
    private GenericRecyclerAdapter<ProfessionalSkills> professionalSkillsAdapter;
    private GenericRecyclerAdapter<Certificate> certificateAdapter;
    private GenericRecyclerAdapter<Qualification> qualificationAdapter;
    private GenericRecyclerAdapter<Seminar> seminarAdapter;
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

        EntryHandler.loadData(professionalSkillList, viewModel.getValue().getProfessionalSkills(), ProfessionalSkills::new, 1);
        EntryHandler.loadData(certificationList, viewModel.getValue().getCertificates(), Certificate::new, 1);
        EntryHandler.loadData(qualificationList, viewModel.getValue().getQualifications(), Qualification::new,1);
        EntryHandler.loadData(seminarList, viewModel.getValue().getSeminars(), Seminar::new,1);

        professionalSkillsAdapter = createProfessionalSkillsAdapter();
        certificateAdapter = createCertificateAdapter();
        qualificationAdapter = createQualificationAdapter();
        seminarAdapter = createSeminarAdapter();

        professionalSkillsContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        certificationContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        qualificationContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        seminarsContainer.setLayoutManager(new LinearLayoutManager(requireContext()));

        professionalSkillsContainer.setAdapter(professionalSkillsAdapter);
        certificationContainer.setAdapter(certificateAdapter);
        qualificationContainer.setAdapter(qualificationAdapter);
        seminarsContainer.setAdapter(seminarAdapter);

        btnAddProfessionalSkill.setOnClickListener(v -> {
            professionalSkillsAdapter.addItem(new ProfessionalSkills());
            professionalSkillsAdapter.notifyDataSetChanged();
        });

        btnAddCertification.setOnClickListener(v -> {
            certificateAdapter.addItem(new Certificate());
            certificateAdapter.notifyDataSetChanged();
        });

        btnAddQualification.setOnClickListener(v -> {
            qualificationAdapter.addItem(new Qualification());
            qualificationAdapter.notifyDataSetChanged();
        });

        btnAddSeminar.setOnClickListener(v -> {
            seminarAdapter.addItem(new Seminar());
            seminarAdapter.notifyDataSetChanged();
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
    private GenericRecyclerAdapter<Seminar> createSeminarAdapter() {
        return new GenericRecyclerAdapter<>(
                seminarList,
                R.layout.item_seminar_training_entry,
                (view, seminar, position) -> {

                    AutoCompleteTextView seminarType = view.findViewById(R.id.seminarTypeInput);
                    TextInputEditText seminarTitle = view.findViewById(R.id.seminarTitleInput);
                    TextInputEditText seminarDesc = view.findViewById(R.id.seminarDescriptionInput);
                    TextInputEditText seminarOrganizer = view.findViewById(R.id.organizerInput);
                    TextInputEditText seminarDate = view.findViewById(R.id.dateAttendedInput);
                    Button btnRemove = view.findViewById(R.id.btnRemoveSeminar);

                    seminarTitle.setText(seminar.getTitle() != null ? seminar.getTitle() : "");
                    seminarDesc.setText(seminar.getDescription() != null ? seminar.getDescription() : "");
                    seminarOrganizer.setText(seminar.getOrganizer() != null ? seminar.getOrganizer() : "");
                    seminarDate.setText(seminar.getDate() != null ? seminar.getDate() : "");
                    seminarType.setText(seminar.getType() != null ? seminar.getType() : "", false);

                    SimpleTextWatcher.bindTextWatcher(seminarTitle, new SimpleTextWatcher(seminar::setTitle));
                    SimpleTextWatcher.bindTextWatcher(seminarDesc, new SimpleTextWatcher(seminar::setDescription));
                    SimpleTextWatcher.bindTextWatcher(seminarOrganizer, new SimpleTextWatcher(seminar::setOrganizer));
                    SimpleTextWatcher.bindTextWatcher(seminarDate, new SimpleTextWatcher(seminar::setDate));
                    SimpleTextWatcher.bindTextWatcher(seminarType, new SimpleTextWatcher(seminar::setType));

                    btnRemove.setOnClickListener(v -> {
                        if (seminarList.size() > 1) {
                            seminarAdapter.removeItem(position);
                        }
                    });

                    btnRemove.setVisibility(
                            seminarList.size() > 1 ? View.VISIBLE : View.GONE
                    );
                }
        );
    }
    private GenericRecyclerAdapter<Qualification> createQualificationAdapter() {
        return new GenericRecyclerAdapter<>(
                qualificationList,
                R.layout.item_qualification_entry,
                (view, qualification, position) -> {

                    AutoCompleteTextView qualificationType = view.findViewById(R.id.qualificationTypeInput);
                    TextInputEditText qualificationTitle = view.findViewById(R.id.qualificationTitleInput);
                    TextInputEditText qualificationDesc = view.findViewById(R.id.qualificationDescriptionInput);
                    TextInputEditText issuingAuthority = view.findViewById(R.id.issuingAuthorityInput);
                    TextInputEditText dateReceived = view.findViewById(R.id.dateReceivedInput);
                    Button btnRemove = view.findViewById(R.id.btnRemoveQual);

                    qualificationTitle.setText(qualification.getTitle() != null ? qualification.getTitle() : "");
                    qualificationDesc.setText(qualification.getDescription() != null ? qualification.getDescription() : "");
                    issuingAuthority.setText(qualification.getAuthority() != null ? qualification.getAuthority() : "");
                    dateReceived.setText(qualification.getDate() != null ? qualification.getDate() : "");
                    qualificationType.setText(qualification.getType() != null ? qualification.getType() : "", false);

                    SimpleTextWatcher.bindTextWatcher(qualificationTitle, new SimpleTextWatcher(qualification::setTitle));
                    SimpleTextWatcher.bindTextWatcher(qualificationDesc, new SimpleTextWatcher(qualification::setDescription));
                    SimpleTextWatcher.bindTextWatcher(issuingAuthority, new SimpleTextWatcher(qualification::setAuthority));
                    SimpleTextWatcher.bindTextWatcher(dateReceived, new SimpleTextWatcher(qualification::setDate));
                    SimpleTextWatcher.bindTextWatcher(qualificationType, new SimpleTextWatcher(qualification::setType));

                    String[] types = {"Professional License", "Award/Recognition", "Professional Membership","Achievement","Other"};
                    UiHelpers.dropDownMaker(types, qualificationType, view.getContext());

                    btnRemove.setOnClickListener(v -> {
                        if (qualificationList.size() > 1) {
                            qualificationAdapter.removeItem(position);
                        }
                    });

                    btnRemove.setVisibility(
                            qualificationList.size() > 1 ? View.VISIBLE : View.GONE
                    );
                }
        );
    }
    private GenericRecyclerAdapter<Certificate> createCertificateAdapter() {
        return new GenericRecyclerAdapter<>(
                certificationList,
                R.layout.item_certification_entry,
                (view, cert, position) -> {

                    TextInputEditText etName = view.findViewById(R.id.etCertificationName);
                    TextInputEditText etOrg = view.findViewById(R.id.etIssuingOrg);
                    TextInputEditText etDateObtained = view.findViewById(R.id.etDateObtained);
                    TextInputEditText etExpiryDate = view.findViewById(R.id.etExpiryDate);
                    TextInputEditText etDescription = view.findViewById(R.id.etDescription);
                    Button btnRemove = view.findViewById(R.id.btnRemoveCert);

                    etName.setText(cert.getName() != null ? cert.getName() : "");
                    etOrg.setText(cert.getOrganization() != null ? cert.getOrganization() : "");
                    etDateObtained.setText(cert.getDate() != null ? cert.getDate() : "");
                    etExpiryDate.setText(cert.getExpiryDate() != null ? cert.getExpiryDate() : "");
                    etDescription.setText(cert.getDescription() != null ? cert.getDescription() : "");

                    SimpleTextWatcher.bindTextWatcher(etName, new SimpleTextWatcher(cert::setName));
                    SimpleTextWatcher.bindTextWatcher(etOrg, new SimpleTextWatcher(cert::setOrganization));
                    SimpleTextWatcher.bindTextWatcher(etDateObtained, new SimpleTextWatcher(cert::setDate));
                    SimpleTextWatcher.bindTextWatcher(etDescription, new SimpleTextWatcher(cert::setDescription));

                    btnRemove.setOnClickListener(v -> {
                        if (certificationList.size() > 1) {
                            certificateAdapter.removeItem(position);
                            certificateAdapter.notifyDataSetChanged();
                        }
                    });

                    btnRemove.setVisibility(
                            certificationList.size() > 1 ? View.VISIBLE : View.GONE
                    );
                }
        );
    }
    private GenericRecyclerAdapter<ProfessionalSkills> createProfessionalSkillsAdapter(){
        return new GenericRecyclerAdapter<>(
                professionalSkillList,
                R.layout.item_professional_skill_entry,
                (view, skill, position) -> {
                    AutoCompleteTextView skillCategory = view.findViewById(R.id.categoryInput);
                    AutoCompleteTextView skillLevel = view.findViewById(R.id.levelInput);
                    TextView skillDesc = view.findViewById(R.id.descriptionInput);
                    Button btnRemove = view.findViewById(R.id.btnRemoveWorkExperience);

                    skillCategory.setText(skill.getCategory() != null ? skill.getCategory() : "");
                    skillLevel.setText(skill.getLevel() != null ? skill.getLevel() : "");
                    skillDesc.setText(skill.getDescription() != null ? skill.getDescription() : "");

                    if (skillCategory.getTag() instanceof SimpleTextWatcher)
                        skillCategory.removeTextChangedListener((SimpleTextWatcher) skillCategory.getTag());

                    if (skillLevel.getTag() instanceof SimpleTextWatcher)
                        skillLevel.removeTextChangedListener((SimpleTextWatcher) skillLevel.getTag());

                    if (skillDesc.getText() instanceof SimpleTextWatcher)
                        skillDesc.removeTextChangedListener((SimpleTextWatcher) skillDesc.getTag());

                    SimpleTextWatcher skillCategoryWatcher = new SimpleTextWatcher(skill::setCategory);
                    SimpleTextWatcher skillLevelWatcher = new SimpleTextWatcher(skill::setLevel);
                    SimpleTextWatcher skillDescWatcher = new SimpleTextWatcher(skill::setDescription);

                    skillCategory.addTextChangedListener(skillCategoryWatcher);
                    skillLevel.addTextChangedListener(skillLevelWatcher);
                    skillDesc.addTextChangedListener(skillDescWatcher);

                    skillCategory.setTag(skillCategoryWatcher);
                    skillLevel.setTag(skillLevelWatcher);
                    skillDesc.setTag(skillDescWatcher);

                    btnRemove.setOnClickListener(v -> {
                        if (professionalSkillList.size() > 1) {
                            professionalSkillsAdapter.removeItem(position);
                            professionalSkillsAdapter.notifyDataSetChanged();
                        }
                    });

                    btnRemove.setVisibility(
                            professionalSkillList.size() > 1 ? View.VISIBLE : View.GONE
                    );
                }
        );
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