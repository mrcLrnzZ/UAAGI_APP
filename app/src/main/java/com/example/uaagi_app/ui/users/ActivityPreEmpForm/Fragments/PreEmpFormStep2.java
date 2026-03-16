package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Education;
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

public class PreEmpFormStep2 extends BaseFormStepFragment {
    private static final String TAG = "PreEmpStep2Lifecycle";
    private Button btnPrevious, btnNext, btnAddEducation;
    private RecyclerView educationRecyclerView;
    private GenericRecyclerAdapter<Education> adapter;
    private final List<Education> educationList = new ArrayList<>();
    private PreEmpFormViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preemp_step_2, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(PreEmpFormViewModel.class);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnAddEducation = view.findViewById(R.id.btnAddEducation);

        educationRecyclerView = view.findViewById(R.id.educationEntriesContainer);
        EntryHandler.loadData(educationList, viewModel.getValue().getEducations(), Education::new, 3);
        educationRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = createEducationAdapter();
        educationRecyclerView.setAdapter(adapter);

        btnPrevious.setOnClickListener(v -> {
            saveFormData();
            ((PreEmpForm) requireActivity()).previousStep();
        });

        btnNext.setOnClickListener(v -> {
            if (validateStep2()) {
                saveFormData();
                ((PreEmpForm) requireActivity()).nextStep();
            }
        });

        btnAddEducation.setOnClickListener(v -> {
            if (educationList.size() < 10) {
                adapter.addItem(new Education());
            }
        });

        return view;
    }

    private boolean validateStep2() {
        boolean allValid = true;
        for (int i = 0; i < educationList.size(); i++) {
            Education edu = educationList.get(i);
            View itemView = educationRecyclerView.getChildAt(i);
            if (itemView != null) {
                TextInputLayout schoolNameLayout = itemView.findViewById(R.id.schoolNameLayout);
                TextInputLayout educationLevelLayout = itemView.findViewById(R.id.educationLevelLayout);
                TextInputLayout statusLayout = itemView.findViewById(R.id.statusLayout);
                TextInputLayout yearGraduatedLayout = itemView.findViewById(R.id.yearGraduatedLayout);

                allValid &= InputValidator.isValid(edu.getSchool(), schoolNameLayout, "School name is required");
                allValid &= InputValidator.isValid(edu.getLevel(), educationLevelLayout, "Education level is required");
                allValid &= InputValidator.isValid(edu.getStatus(), statusLayout, "Status is required");
                allValid &= InputValidator.isValid(edu.getGradYear(), yearGraduatedLayout, "Year is required");
            }
        }
        if (!allValid) {
            Toast.makeText(requireContext(), "Please complete all education entries", Toast.LENGTH_SHORT).show();
        }
        return allValid;
    }

    private GenericRecyclerAdapter<Education> createEducationAdapter(){
        return new GenericRecyclerAdapter<>(
                educationList,
                R.layout.item_education_entry,
                (view, edu, position) -> {
                    TextInputEditText schoolName = view.findViewById(R.id.schoolNameInput);
                    TextInputEditText courseAchievement = view.findViewById(R.id.courseAchievementsInput);
                    TextInputEditText yearGrad = view.findViewById(R.id.yearGraduatedInput);
                    AutoCompleteTextView eduLevel = view.findViewById(R.id.educationLevelInput);
                    AutoCompleteTextView status = view.findViewById(R.id.statusSpinner);
                    Button btnRemove = view.findViewById(R.id.btnRemoveEduc);

                    schoolName.setText(edu.getSchool() != null ? edu.getSchool() : "");
                    courseAchievement.setText(edu.getAchievement() != null ? edu.getAchievement() : "");
                    yearGrad.setText(edu.getGradYear() != null ? edu.getGradYear() : "");
                    eduLevel.setText(edu.getLevel() != null ? edu.getLevel() : "", false);
                    status.setText(edu.getStatus() != null ? edu.getStatus() : "", false);

                    SimpleTextWatcher.bindTextWatcher(schoolName, new SimpleTextWatcher(edu::setSchool));
                    SimpleTextWatcher.bindTextWatcher(courseAchievement, new SimpleTextWatcher(edu::setAchievement));
                    SimpleTextWatcher.bindTextWatcher(yearGrad, new SimpleTextWatcher(edu::setGradYear));
                    SimpleTextWatcher.bindTextWatcher(eduLevel, new SimpleTextWatcher(edu::setLevel));
                    SimpleTextWatcher.bindTextWatcher(status, new SimpleTextWatcher(edu::setStatus));

                    String[] levels = {"Primary Education", "Secondary Education", "Secondary Education (Lower)", "Tertiary Education", "Vocational/Technical", "Master's Degree", "Doctoral Degree", "Post-Graduate Degree", "Associate Degree", "Other"};
                    UiHelpers.dropDownMaker(levels, eduLevel, view.getContext());

                    String[] statuses = {"Graduated", "Undergraduate"};
                    UiHelpers.dropDownMaker(statuses, status, view.getContext());

                    btnRemove.setOnClickListener(v -> {
                        if (educationList.size() > 1) {
                            adapter.removeItem(position);
                        }
                    });
                    btnRemove.setVisibility(educationList.size() > 1 ? View.VISIBLE : View.GONE);
                }
        );
    }
    @Override
    public void saveFormData() {
        EntryHandler.saveData(viewModel, form -> form.setEducations(educationList));
    }

    @Override
    public void onResume() {
        super.onResume();
        EntryHandler.loadData(educationList, viewModel.getValue().getEducations(), Education::new, 3);
        adapter.updateList(educationList);
    }
}
