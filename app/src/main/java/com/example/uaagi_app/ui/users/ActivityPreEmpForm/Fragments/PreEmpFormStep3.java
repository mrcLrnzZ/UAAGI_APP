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
import android.widget.Button;
import android.widget.Toast;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.WorkExperience;
import com.example.uaagi_app.data.viewmodel.PreEmpFormViewModel;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.EntryHandler.EntryHandler;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.utils.InputValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class PreEmpFormStep3 extends BaseFormStepFragment {
    private Button btnPrevious, btnNext;
    private Button btnAddWorkExperience;
    private RecyclerView workExperienceContainer;
    private GenericRecyclerAdapter<WorkExperience> adapter;
    private final List<WorkExperience> workExperienceList = new ArrayList<>();
    private PreEmpFormViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preemp_step_3, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(PreEmpFormViewModel.class);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnAddWorkExperience = view.findViewById(R.id.btnAddWorkExperience);

        workExperienceContainer = view.findViewById(R.id.workExperienceEntriesContainer);

        EntryHandler.loadData(workExperienceList, viewModel.getValue().getWorkExperiences(), WorkExperience::new, 1);
        adapter = createWorkExperienceAdapter();

        workExperienceContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        workExperienceContainer.setAdapter(adapter);

        btnPrevious.setOnClickListener(v -> {
            saveFormData();
            ((PreEmpForm) requireActivity()).previousStep();
        });

        btnNext.setOnClickListener(v -> {
            if (validateStep3()) {
                saveFormData();
                ((PreEmpForm) requireActivity()).nextStep();
            }
        });

        btnAddWorkExperience.setOnClickListener(v -> {
            adapter.addItem(new WorkExperience());
        });

        return view;
    }

    private boolean validateStep3() {
        boolean allValid = true;
        for (int i = 0; i < workExperienceList.size(); i++) {
            WorkExperience work = workExperienceList.get(i);
            // Validation: if any field is filled, or if it's the first entry, require company and position.
            // Actually, many pre-emp forms allow skipping work experience if the person is a fresh grad.
            // But if they started filling it, we should validate.
            
            View itemView = workExperienceContainer.getChildAt(i);
            if (itemView != null) {
                TextInputLayout companyLayout = itemView.findViewById(R.id.companyNameLayout);
                TextInputLayout positionLayout = itemView.findViewById(R.id.positionLayout);
                TextInputLayout startDateLayout = itemView.findViewById(R.id.startDateLayout);

                // If any field is not empty, then company and position are required.
                boolean hasData = InputValidator.isNotEmpty(work.getCompany()) || 
                                 InputValidator.isNotEmpty(work.getPosition()) ||
                                 InputValidator.isNotEmpty(work.getStartDate()) ||
                                 InputValidator.isNotEmpty(work.getEndDate()) ||
                                 InputValidator.isNotEmpty(work.getDescription());

                if (hasData) {
                    allValid &= InputValidator.isValid(work.getCompany(), companyLayout, "Company is required");
                    allValid &= InputValidator.isValid(work.getPosition(), positionLayout, "Position is required");
                    allValid &= InputValidator.isValid(work.getStartDate(), startDateLayout, "Start date is required");
                } else {
                    // Clear errors if it's empty and valid to be empty
                    companyLayout.setError(null);
                    positionLayout.setError(null);
                    startDateLayout.setError(null);
                }
            }
        }
        if (!allValid) {
            Toast.makeText(requireContext(), "Please complete the work experience details", Toast.LENGTH_SHORT).show();
        }
        return allValid;
    }

    private GenericRecyclerAdapter<WorkExperience> createWorkExperienceAdapter() {
        return new GenericRecyclerAdapter<>(
                workExperienceList,
                R.layout.item_work_experience_entry,
                (view, work, position) -> {

                    TextInputEditText companyName = view.findViewById(R.id.companyNameInput);
                    TextInputEditText positionInput = view.findViewById(R.id.positionInput);
                    TextInputEditText startDate = view.findViewById(R.id.startDateInput);
                    TextInputEditText endDate = view.findViewById(R.id.endDateInput);
                    TextInputEditText description = view.findViewById(R.id.jobDescriptionInput);
                    Button btnRemove = view.findViewById(R.id.btnRemoveWorkExperience);

                    companyName.setText(work.getCompany() != null ? work.getCompany() : "");
                    positionInput.setText(work.getPosition() != null ? work.getPosition() : "");
                    startDate.setText(work.getStartDate() != null ? work.getStartDate() : "");
                    endDate.setText(work.getEndDate() != null ? work.getEndDate() : "");
                    description.setText(work.getDescription() != null ? work.getDescription() : "");

                    SimpleTextWatcher.bindTextWatcher(companyName, new SimpleTextWatcher(work::setCompany));
                    SimpleTextWatcher.bindTextWatcher(positionInput, new SimpleTextWatcher(work::setPosition));
                    SimpleTextWatcher.bindTextWatcher(startDate, new SimpleTextWatcher(work::setStartDate));
                    SimpleTextWatcher.bindTextWatcher(endDate, new SimpleTextWatcher(work::setEndDate));
                    SimpleTextWatcher.bindTextWatcher(description, new SimpleTextWatcher(work::setDescription));

                    btnRemove.setOnClickListener(v -> {
                        if (workExperienceList.size() > 1) {
                            adapter.removeItem(position);
                        }
                    });

                    btnRemove.setVisibility(
                            workExperienceList.size() > 1 ? View.VISIBLE : View.GONE
                    );
                }
        );
    }
    @Override
    public void saveFormData() {
        EntryHandler.saveData(viewModel, form -> form.setWorkExperiences(workExperienceList));
    }

    @Override
    public void onResume() {
        super.onResume();
        EntryHandler.loadData(workExperienceList, viewModel.getValue().getWorkExperiences(), WorkExperience::new, 1);
        adapter.notifyDataSetChanged();
    }
}
