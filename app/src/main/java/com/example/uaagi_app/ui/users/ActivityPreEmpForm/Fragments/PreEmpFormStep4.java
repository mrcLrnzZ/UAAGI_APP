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
import android.widget.Toast;

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
import com.example.uaagi_app.utils.InputValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
        });

        btnAddCertification.setOnClickListener(v -> {
            certificateAdapter.addItem(new Certificate());
        });

        btnAddQualification.setOnClickListener(v -> {
            qualificationAdapter.addItem(new Qualification());
        });

        btnAddSeminar.setOnClickListener(v -> {
            seminarAdapter.addItem(new Seminar());
        });

        btnPrevious.setOnClickListener(v -> {
            saveFormData();
            ((PreEmpForm) requireActivity()).previousStep();
        });

        btnNext.setOnClickListener(v -> {
            if (validateStep4()) {
                saveFormData();
                ((PreEmpForm) requireActivity()).nextStep();
            }
        });

        return view;
    }

    private boolean validateStep4() {
        boolean allValid = true;

        // Skills validation (if provided)
        for (int i = 0; i < professionalSkillList.size(); i++) {
            ProfessionalSkills skill = professionalSkillList.get(i);
            View itemView = professionalSkillsContainer.getChildAt(i);
            if (itemView != null && InputValidator.isNotEmpty(skill.getCategory())) {
                TextInputLayout levelLayout = itemView.findViewById(R.id.levelLayout);
                allValid &= InputValidator.isValid(skill.getLevel(), levelLayout, "Skill level is required");
            }
        }

        // Certifications validation (if provided)
        for (int i = 0; i < certificationList.size(); i++) {
            Certificate cert = certificationList.get(i);
            View itemView = certificationContainer.getChildAt(i);
            if (itemView != null && InputValidator.isNotEmpty(cert.getName())) {
                TextInputLayout orgLayout = itemView.findViewById(R.id.issuingOrgLayout);
                TextInputLayout dateLayout = itemView.findViewById(R.id.dateObtainedLayout);
                allValid &= InputValidator.isValid(cert.getOrganization(), orgLayout, "Issuing organization is required");
                allValid &= InputValidator.isValid(cert.getDate(), dateLayout, "Date obtained is required");
            }
        }

        // Qualifications validation (if provided)
        for (int i = 0; i < qualificationList.size(); i++) {
            Qualification qual = qualificationList.get(i);
            View itemView = qualificationContainer.getChildAt(i);
            if (itemView != null && InputValidator.isNotEmpty(qual.getTitle())) {
                TextInputLayout typeLayout = itemView.findViewById(R.id.qualificationTypeLayout);
                TextInputLayout authLayout = itemView.findViewById(R.id.issuingAuthorityLayout);
                allValid &= InputValidator.isValid(qual.getType(), typeLayout, "Type is required");
                allValid &= InputValidator.isValid(qual.getAuthority(), authLayout, "Authority is required");
            }
        }

        // Seminars validation (if provided)
        for (int i = 0; i < seminarList.size(); i++) {
            Seminar sem = seminarList.get(i);
            View itemView = seminarsContainer.getChildAt(i);
            if (itemView != null && InputValidator.isNotEmpty(sem.getTitle())) {
                TextInputLayout orgLayout = itemView.findViewById(R.id.organizerLayout);
                TextInputLayout dateLayout = itemView.findViewById(R.id.dateAttendedLayout);
                allValid &= InputValidator.isValid(sem.getOrganizer(), orgLayout, "Organizer is required");
                allValid &= InputValidator.isValid(sem.getDate(), dateLayout, "Date is required");
            }
        }

        if (!allValid) {
            Toast.makeText(requireContext(), "Please complete the details for the entries you added", Toast.LENGTH_SHORT).show();
        }
        return allValid;
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
                    Button btnRemove = view.findViewById(R.id.btnRemoveSemTrain);

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
                    btnRemove.setVisibility(seminarList.size() > 1 ? View.VISIBLE : View.GONE);
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
                    Button btnRemove = view.findViewById(R.id.btnQualificationEntry);

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
                    btnRemove.setVisibility(qualificationList.size() > 1 ? View.VISIBLE : View.GONE);
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
                    SimpleTextWatcher.bindTextWatcher(etExpiryDate, new SimpleTextWatcher(cert::setExpiryDate));
                    SimpleTextWatcher.bindTextWatcher(etDescription, new SimpleTextWatcher(cert::setDescription));

                    btnRemove.setOnClickListener(v -> {
                        if (certificationList.size() > 1) {
                            certificateAdapter.removeItem(position);
                        }
                    });
                    btnRemove.setVisibility(certificationList.size() > 1 ? View.VISIBLE : View.GONE);
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
                    TextInputEditText skillDesc = view.findViewById(R.id.descriptionInput);
                    Button btnRemove = view.findViewById(R.id.btnRemoveProfSkills);

                    skillCategory.setText(skill.getCategory() != null ? skill.getCategory() : "", false);
                    skillLevel.setText(skill.getLevel() != null ? skill.getLevel() : "", false);
                    skillDesc.setText(skill.getDescription() != null ? skill.getDescription() : "");

                    SimpleTextWatcher.bindTextWatcher(skillCategory, new SimpleTextWatcher(skill::setCategory));
                    SimpleTextWatcher.bindTextWatcher(skillLevel, new SimpleTextWatcher(skill::setLevel));
                    SimpleTextWatcher.bindTextWatcher(skillDesc, new SimpleTextWatcher(skill::setDescription));

                    String[] categories = {"Technical", "Soft Skills", "Language", "Computer", "Other"};
                    UiHelpers.dropDownMaker(categories, skillCategory, view.getContext());
                    String[] levels = {"Beginner", "Intermediate", "Advanced", "Expert"};
                    UiHelpers.dropDownMaker(levels, skillLevel, view.getContext());

                    btnRemove.setOnClickListener(v -> {
                        if (professionalSkillList.size() > 1) {
                            professionalSkillsAdapter.removeItem(position);
                        }
                    });
                    btnRemove.setVisibility(professionalSkillList.size() > 1 ? View.VISIBLE : View.GONE);
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
        EntryHandler.loadData(professionalSkillList, viewModel.getValue().getProfessionalSkills(), ProfessionalSkills::new, 1);
        EntryHandler.loadData(certificationList, viewModel.getValue().getCertificates(), Certificate::new, 1);
        EntryHandler.loadData(qualificationList, viewModel.getValue().getQualifications(), Qualification::new, 1);
        EntryHandler.loadData(seminarList, viewModel.getValue().getSeminars(), Seminar::new, 1);
        
        professionalSkillsAdapter.updateList(professionalSkillList);
        certificateAdapter.updateList(certificationList);
        qualificationAdapter.updateList(qualificationList);
        seminarAdapter.updateList(seminarList);
    }
}
