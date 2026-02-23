package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.EmergencyContact;
import com.example.uaagi_app.data.model.PreEmploymentForm.OfficeSkills;
import com.example.uaagi_app.data.model.PreEmploymentForm.PreEmpFormDataModel;
import com.example.uaagi_app.data.model.PreEmploymentForm.UserInfo;
import com.example.uaagi_app.data.viewmodel.PreEmpFormViewModel;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.CertificatePreviewAdapter;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.CharacterReferencePreviewAdapter;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.EducationPreviewAdapter;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GovIdPreviewAdapter;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.ProfessionalSkillsPreviewAdapter;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.QualificationPreviewAdapter;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.SeminarPreviewAdapter;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.WorkExperiencePreviewAdapter;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;

public class PreEmpFormStep6 extends BaseFormStepFragment {

    private PreEmpFormViewModel viewModel;

    // Personal Information TextViews
    private TextView previewFirstName, previewMiddleName, previewLastName;
    private TextView previewDateOfBirth, previewAge, previewGender;
    private TextView previewReligion, previewCivilStatus, previewNationality;
    private TextView previewHeight, previewWeight, previewBloodType;

    // Contact Information TextViews
    private TextView previewPhoneNumber, previewAlternateContact, previewLandline;
    private TextView previewCurrentAddress, previewPermanentAddress;

    // Emergency Contact TextViews
    private TextView previewEmergencyContactName, previewEmergencyRelationship, previewEmergencyContactNumber;

    // Microsoft Office Skills RadioButtons
    private RadioButton msWordBeginner, msWordIntermediate, msWordAdvanced;
    private RadioButton msExcelBeginner, msExcelIntermediate, msExcelAdvanced;
    private RadioButton msPowerPointBeginner, msPowerPointIntermediate, msPowerPointAdvanced;
    private RadioButton msOutlookBeginner, msOutlookIntermediate, msOutlookAdvanced;

    // RecyclerViews
    private RecyclerView educationPreviewList, workExperiencePreviewList;
    private RecyclerView professionalSkillsPreviewList, certificationsPreviewList;
    private RecyclerView qualificationsPreviewList, seminarsPreviewList;
    private RecyclerView governmentIssuedNumbersPreviewList, characterReferencesPreviewList;

    // Buttons
    private Button btnPrevious, btnSubmit;

    public PreEmpFormStep6() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(PreEmpFormViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preemp_step_6, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        setupRecyclerViews();
        populatePreviewData();
        setupButtons();
    }

    private void initializeViews(View view) {
        // Personal Information
        previewFirstName = view.findViewById(R.id.previewFirstName);
        previewMiddleName = view.findViewById(R.id.previewMiddleName);
        previewLastName = view.findViewById(R.id.previewLastName);
        previewDateOfBirth = view.findViewById(R.id.previewDateOfBirth);
        previewAge = view.findViewById(R.id.previewAge);
        previewGender = view.findViewById(R.id.previewGender);
        previewReligion = view.findViewById(R.id.previewReligion);
        previewCivilStatus = view.findViewById(R.id.previewCivilStatus);
        previewNationality = view.findViewById(R.id.previewNationality);
        previewHeight = view.findViewById(R.id.previewHeight);
        previewWeight = view.findViewById(R.id.previewWeight);
        previewBloodType = view.findViewById(R.id.previewBloodType);

        // Contact Information
        previewPhoneNumber = view.findViewById(R.id.previewPhoneNumber);
        previewAlternateContact = view.findViewById(R.id.previewAlternateContact);
        previewLandline = view.findViewById(R.id.previewLandline);
        previewCurrentAddress = view.findViewById(R.id.previewCurrentAddress);
        previewPermanentAddress = view.findViewById(R.id.previewPermanentAddress);

        // Emergency Contact
        previewEmergencyContactName = view.findViewById(R.id.previewEmergencyContactName);
        previewEmergencyRelationship = view.findViewById(R.id.previewEmergencyRelationship);
        previewEmergencyContactNumber = view.findViewById(R.id.previewEmergencyContactNumber);

        // Microsoft Office Skills
        msWordBeginner = view.findViewById(R.id.msWordBeginner);
        msWordIntermediate = view.findViewById(R.id.msWordIntermediate);
        msWordAdvanced = view.findViewById(R.id.msWordAdvanced);

        msExcelBeginner = view.findViewById(R.id.msExcelBeginner);
        msExcelIntermediate = view.findViewById(R.id.msExcelIntermediate);
        msExcelAdvanced = view.findViewById(R.id.msExcelAdvanced);

        msPowerPointBeginner = view.findViewById(R.id.msPowerPointBeginner);
        msPowerPointIntermediate = view.findViewById(R.id.msPowerPointIntermediate);
        msPowerPointAdvanced = view.findViewById(R.id.msPowerPointAdvanced);

        msOutlookBeginner = view.findViewById(R.id.msOutlookBeginner);
        msOutlookIntermediate = view.findViewById(R.id.msOutlookIntermediate);
        msOutlookAdvanced = view.findViewById(R.id.msOutlookAdvanced);

        // RecyclerViews
        educationPreviewList = view.findViewById(R.id.educationPreviewList);
        workExperiencePreviewList = view.findViewById(R.id.workExperiencePreviewList);
        professionalSkillsPreviewList = view.findViewById(R.id.professionalSkillsPreviewList);
        certificationsPreviewList = view.findViewById(R.id.certificationsPreviewList);
        qualificationsPreviewList = view.findViewById(R.id.qualificationsPreviewList);
        seminarsPreviewList = view.findViewById(R.id.seminarsPreviewList);
        governmentIssuedNumbersPreviewList = view.findViewById(R.id.governmentIssuedNumbersPreviewList);
        characterReferencesPreviewList = view.findViewById(R.id.characterReferencesPreviewList);

        // Buttons
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnSubmit = view.findViewById(R.id.btnSubmit);
    }

    private void setupRecyclerViews() {
        educationPreviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        workExperiencePreviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        professionalSkillsPreviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        certificationsPreviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        qualificationsPreviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        seminarsPreviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        governmentIssuedNumbersPreviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        characterReferencesPreviewList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void populatePreviewData() {
        PreEmpFormDataModel formData = viewModel.getValue();
        if (formData == null) return;

        // Populate Personal Information
        populatePersonalInfo(formData.getUserInfo());

        // Populate Emergency Contact
        populateEmergencyContact(formData.getEmergencyContact());

        // Populate Microsoft Office Skills
        populateOfficeSkills(formData.getOfficeSkills());

        // Populate RecyclerViews
        if (formData.getEducations() != null && !formData.getEducations().isEmpty()) {
            educationPreviewList.setAdapter(new EducationPreviewAdapter(formData.getEducations()));
        }

        if (formData.getWorkExperiences() != null && !formData.getWorkExperiences().isEmpty()) {
            workExperiencePreviewList.setAdapter(new WorkExperiencePreviewAdapter(formData.getWorkExperiences()));
        }

        if (formData.getProfessionalSkills() != null && !formData.getProfessionalSkills().isEmpty()) {
            professionalSkillsPreviewList.setAdapter(new ProfessionalSkillsPreviewAdapter(formData.getProfessionalSkills()));
        }

        if (formData.getCertificates() != null && !formData.getCertificates().isEmpty()) {
            certificationsPreviewList.setAdapter(new CertificatePreviewAdapter(formData.getCertificates()));
        }

        if (formData.getQualifications() != null && !formData.getQualifications().isEmpty()) {
            qualificationsPreviewList.setAdapter(new QualificationPreviewAdapter(formData.getQualifications()));
        }

        if (formData.getSeminars() != null && !formData.getSeminars().isEmpty()) {
            seminarsPreviewList.setAdapter(new SeminarPreviewAdapter(formData.getSeminars()));
        }

        if (formData.getGovIds() != null && !formData.getGovIds().isEmpty()) {
            governmentIssuedNumbersPreviewList.setAdapter(new GovIdPreviewAdapter(formData.getGovIds()));
        }

        if (formData.getContactReferences() != null && !formData.getContactReferences().isEmpty()) {
            characterReferencesPreviewList.setAdapter(new CharacterReferencePreviewAdapter(formData.getContactReferences()));
        }
    }

    private void populatePersonalInfo(UserInfo userInfo) {
        if (userInfo == null) return;

        previewFirstName.setText(TextUtils.isEmpty(userInfo.getFirstName()) ? "—" : userInfo.getFirstName());
        previewMiddleName.setText(TextUtils.isEmpty(userInfo.getMiddleName()) ? "—" : userInfo.getMiddleName());
        previewLastName.setText(TextUtils.isEmpty(userInfo.getLastName()) ? "—" : userInfo.getLastName());
        previewDateOfBirth.setText(TextUtils.isEmpty(userInfo.getDob()) ? "—" : userInfo.getDob());
        previewAge.setText(TextUtils.isEmpty(userInfo.getAge()) ? "—" : userInfo.getAge());
        previewGender.setText(TextUtils.isEmpty(userInfo.getGender()) ? "—" : userInfo.getGender());
        previewReligion.setText(TextUtils.isEmpty(userInfo.getReligion()) ? "—" : userInfo.getReligion());
        previewCivilStatus.setText(TextUtils.isEmpty(userInfo.getCivilStatus()) ? "—" : userInfo.getCivilStatus());
        previewNationality.setText(TextUtils.isEmpty(userInfo.getNationality()) ? "—" : userInfo.getNationality());
        previewHeight.setText(TextUtils.isEmpty(userInfo.getHeight()) ? "—" : userInfo.getHeight());
        previewWeight.setText(TextUtils.isEmpty(userInfo.getWeight()) ? "—" : userInfo.getWeight());
        previewBloodType.setText(TextUtils.isEmpty(userInfo.getBloodType()) ? "—" : userInfo.getBloodType());

        // Contact Information
        previewPhoneNumber.setText(TextUtils.isEmpty(userInfo.getCellNo()) ? "—" : userInfo.getCellNo());
        previewAlternateContact.setText("—"); // Not in UserInfo model
        previewLandline.setText(TextUtils.isEmpty(userInfo.getTelNo()) ? "—" : userInfo.getTelNo());
        previewCurrentAddress.setText(TextUtils.isEmpty(userInfo.getCurrentAddress()) ? "—" : userInfo.getCurrentAddress());
        previewPermanentAddress.setText(TextUtils.isEmpty(userInfo.getPermanentAddress()) ? "—" : userInfo.getPermanentAddress());
    }

    private void populateEmergencyContact(EmergencyContact emergency) {
        if (emergency == null) {
            previewEmergencyContactName.setText("—");
            previewEmergencyRelationship.setText("—");
            previewEmergencyContactNumber.setText("—");
            return;
        }

        previewEmergencyContactName.setText(TextUtils.isEmpty(emergency.getName()) ? "—" : emergency.getName());
        previewEmergencyRelationship.setText(TextUtils.isEmpty(emergency.getRelationship()) ? "—" : emergency.getRelationship());
        previewEmergencyContactNumber.setText(TextUtils.isEmpty(emergency.getContact()) ? "—" : emergency.getContact());
    }

    private void populateOfficeSkills(OfficeSkills officeSkills) {
        if (officeSkills == null) return;

        // MS Word
        setOfficeSkillLevel(officeSkills.getMsword(), msWordBeginner, msWordIntermediate, msWordAdvanced);

        // MS Excel
        setOfficeSkillLevel(officeSkills.getMsexcel(), msExcelBeginner, msExcelIntermediate, msExcelAdvanced);

        // MS PowerPoint
        setOfficeSkillLevel(officeSkills.getMsppt(), msPowerPointBeginner, msPowerPointIntermediate, msPowerPointAdvanced);

        // MS Outlook
        setOfficeSkillLevel(officeSkills.getMsoutlook(), msOutlookBeginner, msOutlookIntermediate, msOutlookAdvanced);
    }

    private void setOfficeSkillLevel(String level, RadioButton beginner, RadioButton intermediate, RadioButton advanced) {
        // Clear all selections first
        beginner.setChecked(false);
        intermediate.setChecked(false);
        advanced.setChecked(false);

        if (TextUtils.isEmpty(level)) return;

        switch (level.toLowerCase()) {
            case "beginner":
                beginner.setChecked(true);
                break;
            case "intermediate":
                intermediate.setChecked(true);
                break;
            case "advanced":
                advanced.setChecked(true);
                break;
        }
    }

    private void setupButtons() {
        btnPrevious.setOnClickListener(v -> {
            saveFormData();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        btnSubmit.setOnClickListener(v -> {
            // Submit the form
            ((PreEmpForm) requireActivity()).submitForm();
        });
    }

    @Override
    public void saveFormData() {
        // Step 6 is a preview/summary step, no data to save
        // All data has already been saved in previous steps
    }
}