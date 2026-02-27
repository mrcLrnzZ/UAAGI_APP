package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.ContactReference;
import com.example.uaagi_app.data.model.PreEmploymentForm.EmergencyContact;
import com.example.uaagi_app.data.model.PreEmploymentForm.GovId;
import com.example.uaagi_app.data.model.PreEmploymentForm.OfficeSkills;
import com.example.uaagi_app.data.model.PreEmploymentForm.ProfessionalSkills;
import com.example.uaagi_app.data.viewmodel.PreEmpFormViewModel;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.ContactReferenceEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GovIdEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.EntryHandler.EntryHandler;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class PreEmpFormStep5 extends BaseFormStepFragment {
    private RecyclerView governmentIdContainer;
    private RecyclerView referenceContainer;
    private final List<GovId> governmentIdList = new ArrayList<>();
    private final List<ContactReference> contactReferenceList = new ArrayList<>();
    private GenericRecyclerAdapter<GovId> governmentIdAdapter;
    private GenericRecyclerAdapter<ContactReference> contactReferenceAdapter;
    private PreEmpFormViewModel viewModel;
    private TextInputEditText emergencyContactNameInput;
    private AutoCompleteTextView emergencyRelationshipInput;
    private TextInputEditText emergencyContactNumberInput;
    private RadioGroup msWordRadioGroup;
    private RadioGroup msExcelRadioGroup;
    private RadioGroup msPowerPointRadioGroup;
    private RadioGroup msOutlookRadioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preemp_step_5, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(PreEmpFormViewModel.class);
        Button btnPrevious = view.findViewById(R.id.btnPrevious);
        Button btnNext = view.findViewById(R.id.btnNext);
        governmentIdContainer = view.findViewById(R.id.governmentIdContainer);
        referenceContainer = view.findViewById(R.id.referenceContainer);

        Button btnAddGovernmentId = view.findViewById(R.id.btnAddGovernmentId);
        Button btnAddReference = view.findViewById(R.id.btnAddReference);
        Button btnRemoveGovernmentId = view.findViewById(R.id.btnRemoveGovernmentId);
        Button btnRemoveReference = view.findViewById(R.id.btnRemoveReference);

        emergencyContactNameInput = view.findViewById(R.id.emergencyContactNameInput);
        emergencyRelationshipInput = view.findViewById(R.id.emergencyRelationshipInput);
        emergencyContactNumberInput = view.findViewById(R.id.emergencyContactNumberInput);

        msWordRadioGroup = view.findViewById(R.id.msWordRadioGroup);
        msExcelRadioGroup = view.findViewById(R.id.msExcelRadioGroup);
        msPowerPointRadioGroup = view.findViewById(R.id.msPowerPointRadioGroup);
        msOutlookRadioGroup = view.findViewById(R.id.msOutlookRadioGroup);

        setupRelationshipDropdown();

        governmentIdList.add(new GovId());
        contactReferenceList.add(new ContactReference());

        EntryHandler.loadData(governmentIdList, viewModel.getValue().getGovIds(), GovId::new, 1);
        EntryHandler.loadData(contactReferenceList, viewModel.getValue().getContactReferences(), ContactReference::new, 1);
        loadExistingData();


        governmentIdAdapter = createGovIdAdapter();
        contactReferenceAdapter = createReferenceAdapter();

        governmentIdContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        referenceContainer.setLayoutManager(new LinearLayoutManager(requireContext()));

        governmentIdContainer.setAdapter(governmentIdAdapter);
        referenceContainer.setAdapter(contactReferenceAdapter);

        btnAddGovernmentId.setOnClickListener(v ->{
            governmentIdAdapter.addItem(new GovId());
            governmentIdAdapter.notifyDataSetChanged();
        });

        btnAddReference.setOnClickListener(v ->{
            contactReferenceAdapter.addItem(new ContactReference());
            contactReferenceAdapter.notifyDataSetChanged();
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
    private GenericRecyclerAdapter<GovId> createGovIdAdapter() {
        return new GenericRecyclerAdapter<>(
                governmentIdList,
                R.layout.item_government_id_entry,
                (view, govId, position) -> {

                    AutoCompleteTextView idType = view.findViewById(R.id.idTypeInput);
                    TextInputEditText idNumber = view.findViewById(R.id.idNumberInput);
                    Button btnRemove = view.findViewById(R.id.btnRemoveGovernmentId);

                    idType.setText(govId.getType() != null ? govId.getType() : "", false);
                    idNumber.setText(govId.getNumber() != null ? govId.getNumber() : "");

                    SimpleTextWatcher.bindTextWatcher(idType, new SimpleTextWatcher(govId::setType));
                    SimpleTextWatcher.bindTextWatcher(idNumber, new SimpleTextWatcher(govId::setNumber));

                    String[] idTypes = {"SSS", "TIN", "PAGIBIG", "PhilHealth","Passport"};
                    UiHelpers.dropDownMaker(idTypes, idType, view.getContext());

                    btnRemove.setOnClickListener(v -> {
                        if (governmentIdList.size() > 1) {
                            governmentIdAdapter.removeItem(position);
                        }
                    });

                    btnRemove.setVisibility(
                            governmentIdList.size() > 1 ? View.VISIBLE : View.GONE
                    );
                }
        );
    }
    private GenericRecyclerAdapter<ContactReference> createReferenceAdapter() {
        return new GenericRecyclerAdapter<>(
                contactReferenceList,
                R.layout.item_reference_entry,
                (view, ref, position) -> {

                    TextInputEditText name = view.findViewById(R.id.tiet_contact_ref);
                    TextInputEditText occupation = view.findViewById(R.id.tiet_occupation);
                    TextInputEditText company = view.findViewById(R.id.tiet_company);
                    TextInputEditText contactNumber = view.findViewById(R.id.tiet_contact_no);
                    Button btnRemove = view.findViewById(R.id.btnRemoveReference);

                    name.setText(ref.getName() != null ? ref.getName() : "");
                    occupation.setText(ref.getOccupation() != null ? ref.getOccupation() : "");
                    company.setText(ref.getCompany() != null ? ref.getCompany() : "");
                    contactNumber.setText(ref.getPhone() != null ? ref.getPhone() : "");

                    SimpleTextWatcher.bindTextWatcher(name, new SimpleTextWatcher(ref::setName));
                    SimpleTextWatcher.bindTextWatcher(occupation, new SimpleTextWatcher(ref::setOccupation));
                    SimpleTextWatcher.bindTextWatcher(company, new SimpleTextWatcher(ref::setCompany));
                    SimpleTextWatcher.bindTextWatcher(contactNumber, new SimpleTextWatcher(ref::setPhone));

                    btnRemove.setOnClickListener(v -> {
                        if (contactReferenceList.size() > 1) {
                            contactReferenceAdapter.removeItem(position);
                        }
                    });

                    btnRemove.setVisibility(
                            contactReferenceList.size() > 1 ? View.VISIBLE : View.GONE
                    );
                }
        );
    }
    private void setupRelationshipDropdown() {
        String[] relationships = new String[]{
                "Spouse", "Parent", "Sibling", "Child", "Friend",
                "Relative", "Colleague", "Neighbor", "Other"
        };
        UiHelpers.dropDownMaker(relationships, emergencyRelationshipInput, requireContext());
    }

    private void loadExistingData() {
        EmergencyContact emergencyContact = viewModel.getValue().getEmergencyContact();
        if (emergencyContact != null) {
            emergencyContactNameInput.setText(emergencyContact.getName());
            emergencyRelationshipInput.setText(emergencyContact.getRelationship());
            emergencyContactNumberInput.setText(emergencyContact.getContact());
        }

        OfficeSkills officeSkills = viewModel.getValue().getOfficeSkills();
        if (officeSkills != null) {
            setRadioGroupSelection(msWordRadioGroup, officeSkills.getMsword());
            setRadioGroupSelection(msExcelRadioGroup, officeSkills.getMsexcel());
            setRadioGroupSelection(msPowerPointRadioGroup, officeSkills.getMsppt());
            setRadioGroupSelection(msOutlookRadioGroup, officeSkills.getMsoutlook());
        }
    }

    private void setRadioGroupSelection(RadioGroup radioGroup, String level) {
        if (level == null || level.isEmpty()) {
            radioGroup.clearCheck();
            return;
        }

        int radioButtonId = -1;
        int groupId = radioGroup.getId();

        if (groupId == R.id.msWordRadioGroup) {
            radioButtonId = getRadioButtonIdForLevel(level, R.id.msWordBeginner, R.id.msWordIntermediate, R.id.msWordAdvanced);
        } else if (groupId == R.id.msExcelRadioGroup) {
            radioButtonId = getRadioButtonIdForLevel(level, R.id.msExcelBeginner, R.id.msExcelIntermediate, R.id.msExcelAdvanced);
        } else if (groupId == R.id.msPowerPointRadioGroup) {
            radioButtonId = getRadioButtonIdForLevel(level, R.id.msPowerPointBeginner, R.id.msPowerPointIntermediate, R.id.msPowerPointAdvanced);
        } else if (groupId == R.id.msOutlookRadioGroup) {
            radioButtonId = getRadioButtonIdForLevel(level, R.id.msOutlookBeginner, R.id.msOutlookIntermediate, R.id.msOutlookAdvanced);
        }

        if (radioButtonId != -1) {
            radioGroup.check(radioButtonId);
        }
    }

    private int getRadioButtonIdForLevel(String level, int beginnerId, int intermediateId, int advancedId) {
        if (level.equalsIgnoreCase("Beginner")) {
            return beginnerId;
        } else if (level.equalsIgnoreCase("Intermediate")) {
            return intermediateId;
        } else if (level.equalsIgnoreCase("Advanced")) {
            return advancedId;
        }
        return -1;
    }

    private String getSelectedRadioButtonText(RadioGroup radioGroup, int beginnerId, int intermediateId, int advancedId) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            return "";
        }

        if (selectedId == beginnerId) {
            return "beginner";
        } else if (selectedId == intermediateId) {
            return "intermediate";
        } else if (selectedId == advancedId) {
            return "advanced";
        }
        return "";
    }

    @Override
    public void saveFormData() {
        EntryHandler.saveData(viewModel, form -> form.setGovIds(governmentIdList));

        EntryHandler.saveData(viewModel, form -> form.setContactReferences(contactReferenceList));

        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.setName(emergencyContactNameInput.getText().toString().trim());
        emergencyContact.setRelationship(emergencyRelationshipInput.getText().toString().trim());
        emergencyContact.setContact(emergencyContactNumberInput.getText().toString().trim());
        EntryHandler.saveData(viewModel, form -> form.setEmergencyContact(emergencyContact));

        OfficeSkills officeSkills = new OfficeSkills();
        officeSkills.setMsword(getSelectedRadioButtonText(msWordRadioGroup, R.id.msWordBeginner, R.id.msWordIntermediate, R.id.msWordAdvanced));
        officeSkills.setMsexcel(getSelectedRadioButtonText(msExcelRadioGroup, R.id.msExcelBeginner, R.id.msExcelIntermediate, R.id.msExcelAdvanced));
        officeSkills.setMsppt(getSelectedRadioButtonText(msPowerPointRadioGroup, R.id.msPowerPointBeginner, R.id.msPowerPointIntermediate, R.id.msPowerPointAdvanced));
        officeSkills.setMsoutlook(getSelectedRadioButtonText(msOutlookRadioGroup, R.id.msOutlookBeginner, R.id.msOutlookIntermediate, R.id.msOutlookAdvanced));
        EntryHandler.saveData(viewModel, form -> form.setOfficeSkills(officeSkills));
    }

    @Override
    public void onResume() {
        super.onResume();
        governmentIdList.clear();
        contactReferenceList.clear();

        EntryHandler.loadData(governmentIdList, viewModel.getValue().getGovIds(), GovId::new, 1);
        EntryHandler.loadData(contactReferenceList, viewModel.getValue().getContactReferences(), ContactReference::new, 1);

        loadExistingData();

        if (governmentIdContainer.getAdapter() != null) {
            governmentIdContainer.getAdapter().notifyDataSetChanged();
        }
        if (referenceContainer.getAdapter() != null) {
            referenceContainer.getAdapter().notifyDataSetChanged();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EntryHandler.saveData(viewModel, form -> form.setGovIds(new ArrayList<>(governmentIdList)));
        EntryHandler.saveData(viewModel, form -> form.setContactReferences(new ArrayList<>(contactReferenceList)));
    }
}