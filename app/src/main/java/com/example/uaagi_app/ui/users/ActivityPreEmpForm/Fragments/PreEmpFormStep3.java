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

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Education;
import com.example.uaagi_app.data.model.PreEmploymentForm.Seminar;
import com.example.uaagi_app.data.model.PreEmploymentForm.WorkExperience;
import com.example.uaagi_app.data.viewmodel.PreEmpFormViewModel;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.WorkExperienceEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.EntryHandler.EntryHandler;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class PreEmpFormStep3 extends BaseFormStepFragment {
    private Button btnPrevious, btnNext, btnSubmit;
    private Button btnAddWorkExperience, btnRemoveWorkExperience;
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
        btnRemoveWorkExperience = view.findViewById(R.id.btnRemoveWorkExperience);

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
            saveFormData();
            ((PreEmpForm) requireActivity()).nextStep();
        });

        btnAddWorkExperience.setOnClickListener(v -> {
            adapter.addItem(new WorkExperience());
            adapter.notifyDataSetChanged();
        });

        return view;
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

                    if (companyName.getTag() instanceof SimpleTextWatcher)
                        companyName.removeTextChangedListener((SimpleTextWatcher) companyName.getTag());

                    if (positionInput.getTag() instanceof SimpleTextWatcher)
                        positionInput.removeTextChangedListener((SimpleTextWatcher) positionInput.getTag());

                    if (startDate.getTag() instanceof SimpleTextWatcher)
                        startDate.removeTextChangedListener((SimpleTextWatcher) startDate.getTag());

                    if (endDate.getTag() instanceof SimpleTextWatcher)
                        endDate.removeTextChangedListener((SimpleTextWatcher) endDate.getTag());

                    if (description.getTag() instanceof SimpleTextWatcher)
                        description.removeTextChangedListener((SimpleTextWatcher) description.getTag());

                    SimpleTextWatcher companyWatcher = new SimpleTextWatcher(work::setCompany);
                    SimpleTextWatcher positionWatcher = new SimpleTextWatcher(work::setPosition);
                    SimpleTextWatcher startWatcher = new SimpleTextWatcher(work::setStartDate);
                    SimpleTextWatcher endWatcher = new SimpleTextWatcher(work::setEndDate);
                    SimpleTextWatcher descWatcher = new SimpleTextWatcher(work::setDescription);

                    companyName.addTextChangedListener(companyWatcher);
                    positionInput.addTextChangedListener(positionWatcher);
                    startDate.addTextChangedListener(startWatcher);
                    endDate.addTextChangedListener(endWatcher);
                    description.addTextChangedListener(descWatcher);

                    companyName.setTag(companyWatcher);
                    positionInput.setTag(positionWatcher);
                    startDate.setTag(startWatcher);
                    endDate.setTag(endWatcher);
                    description.setTag(descWatcher);

                    btnRemove.setOnClickListener(v -> {
                        if (workExperienceList.size() > 1) {
                            adapter.removeItem(position);
                            adapter.notifyDataSetChanged();
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EntryHandler.saveData(viewModel, form -> form.setWorkExperiences(new ArrayList<>(workExperienceList)));
    }
}