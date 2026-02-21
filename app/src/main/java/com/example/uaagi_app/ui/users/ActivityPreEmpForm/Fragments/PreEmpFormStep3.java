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
import com.example.uaagi_app.data.model.PreEmploymentForm.WorkExperience;
import com.example.uaagi_app.data.viewmodel.PreEmpFormViewModel;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.WorkExperienceEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.EntryHandler.EntryHandler;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.ArrayList;
import java.util.List;

public class PreEmpFormStep3 extends Fragment {
    private Button btnPrevious, btnNext, btnSubmit;
    private Button btnAddWorkExperience, btnRemoveWorkExperience;
    private RecyclerView workExperienceContainer;
    private WorkExperienceEntry adapter;
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
        EntryHandler.loadData(workExperienceList, viewModel.getValue().getWorkExperiences(), new WorkExperience());

        adapter = new WorkExperienceEntry(workExperienceList);
        workExperienceContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        workExperienceContainer.setAdapter(adapter);


        btnPrevious.setOnClickListener(v -> {
            EntryHandler.saveData(viewModel, form -> form.setWorkExperiences(workExperienceList));
            ((PreEmpForm) requireActivity()).previousStep(new PreEmpFormStep2());
        });

        btnNext.setOnClickListener(v -> {
            EntryHandler.saveData(viewModel, form -> form.setWorkExperiences(workExperienceList));
            ((PreEmpForm) requireActivity()).nextStep(new PreEmpFormStep4());
        });

        btnAddWorkExperience.setOnClickListener(v -> {
            btnRemoveWorkExperience.setVisibility(View.VISIBLE);
            EntryHandler.addEntry(workExperienceList, new WorkExperience(), workExperienceContainer, adapter, 10);
        });
        btnRemoveWorkExperience.setOnClickListener(v -> {
            if (workExperienceList.size() == 1) {
                btnRemoveWorkExperience.setVisibility(View.GONE);
            }
            EntryHandler.removeEntry(workExperienceList, workExperienceContainer, adapter, requireContext(), 1);
        });
        return view;
    }
}