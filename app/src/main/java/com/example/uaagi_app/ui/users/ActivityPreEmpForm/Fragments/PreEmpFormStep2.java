package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

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
import com.google.android.material.textfield.TextInputEditText;

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
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_preemp_step_2, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(PreEmpFormViewModel.class);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnAddEducation = view.findViewById(R.id.btnAddEducation);

        educationRecyclerView = view.findViewById(R.id.educationEntriesContainer);
        EntryHandler.loadData(educationList, viewModel.getValue().getEducations(), Education::new, 3);
        educationRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        educationRecyclerView = view.findViewById(R.id.educationEntriesContainer);

        EntryHandler.loadData(
                educationList,
                viewModel.getValue().getEducations(),
                Education::new,
                3
        );

        educationRecyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        adapter = createEducationAdapter();
        educationRecyclerView.setAdapter(adapter);

        btnPrevious.setOnClickListener(v -> {
            saveFormData();
            ((PreEmpForm) requireActivity()).previousStep();
        });

        btnNext.setOnClickListener(v -> {
            saveFormData();
            ((PreEmpForm) requireActivity()).nextStep();
        });

        btnAddEducation.setOnClickListener(v -> {
            if (educationList.size() < 10) {
                adapter.addItem(new Education());
                adapter.notifyDataSetChanged();
            }
        });

        return view;
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

                    if (schoolName.getTag() instanceof SimpleTextWatcher)
                        schoolName.removeTextChangedListener((SimpleTextWatcher) schoolName.getTag());

                    if (courseAchievement.getTag() instanceof SimpleTextWatcher)
                        courseAchievement.removeTextChangedListener((SimpleTextWatcher) courseAchievement.getTag());

                    if (yearGrad.getTag() instanceof SimpleTextWatcher)
                        yearGrad.removeTextChangedListener((SimpleTextWatcher) yearGrad.getTag());

                    if (eduLevel.getTag() instanceof SimpleTextWatcher)
                        eduLevel.removeTextChangedListener((SimpleTextWatcher) eduLevel.getTag());

                    if (status.getTag() instanceof SimpleTextWatcher)
                        status.removeTextChangedListener((SimpleTextWatcher) status.getTag());

                    SimpleTextWatcher schoolNameWatcher = new SimpleTextWatcher(edu::setSchool);
                    SimpleTextWatcher courseAchievementWatcher = new SimpleTextWatcher(edu::setAchievement);
                    SimpleTextWatcher yearGradWatcher = new SimpleTextWatcher(edu::setGradYear);
                    SimpleTextWatcher statusWatcher = new SimpleTextWatcher(edu::setStatus);

                    schoolName.addTextChangedListener(schoolNameWatcher);
                    courseAchievement.addTextChangedListener(courseAchievementWatcher);
                    yearGrad.addTextChangedListener(yearGradWatcher);
                    status.addTextChangedListener(statusWatcher);

                    schoolName.setTag(schoolNameWatcher);
                    courseAchievement.setTag(courseAchievementWatcher);
                    yearGrad.setTag(yearGradWatcher);
                    status.setTag(statusWatcher);

                    String[] levels = {
                            "Primary Education",
                            "Secondary Education",
                            "Secondary Education (Lower)",
                            "Tertiary Education",
                            "Vocational/Technical",
                            "Master's Degree",
                            "Doctoral Degree",
                            "Post-Graduate Degree",
                            "Associate Degree",
                            "Other"
                    };

                    UiHelpers.dropDownMaker(levels, eduLevel, view.getContext());

                    String[] statuses = {"Graduated", "Undergraduate"};
                    UiHelpers.dropDownMaker(statuses, status, view.getContext());

                    btnRemove.setOnClickListener(v -> {
                        if (educationList.size() > 3) {
                            adapter.removeItem(position);
                            adapter.notifyDataSetChanged();
                        }
                    });

                    btnRemove.setVisibility(
                            educationList.size() > 3 ? View.VISIBLE : View.GONE
                    );
                }
        );
    }
    @Override
    public void saveFormData() {
        EntryHandler.saveData(viewModel, form -> form.setEducations(educationList));
        Log.d(TAG, "saveFormDataStep2: "+viewModel.getValue().getEducations().toString());
        Log.d(TAG, "saveFormData: "+ educationList.toString());
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }
    @Override
    public void onResume() {
        super.onResume();
        if (viewModel.getValue().getEducations() == null ||
                viewModel.getValue().getEducations().isEmpty()) {

            EntryHandler.loadData(educationList, null, Education::new, 3);
        } else {
            EntryHandler.loadData(
                    educationList,
                    viewModel.getValue().getEducations(),
                    Education::new,
                    3
            );
        }
        adapter.updateList(educationList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EntryHandler.saveData(viewModel, form -> form.setEducations(new ArrayList<>(educationList)));
        Log.d(TAG, "onDestroyView");
    }
}