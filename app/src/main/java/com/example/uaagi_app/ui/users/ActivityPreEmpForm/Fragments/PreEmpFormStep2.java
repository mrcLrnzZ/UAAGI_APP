package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Education;
import com.example.uaagi_app.data.viewmodel.PreEmpFormViewModel;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.EducationEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.EntryHandler.EntryHandler;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.ArrayList;
import java.util.List;

public class PreEmpFormStep2 extends Fragment {

    private Button btnPrevious, btnNext, btnAddEducation, btnRemoveEducation;
    private RecyclerView educationRecyclerView;
    private EducationEntry adapter;
    private final List<Education> educationList = new ArrayList<>();
    private PreEmpFormViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("PreEmpFormStep2", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_preemp_step_2, container, false);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(PreEmpFormViewModel.class);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnAddEducation = view.findViewById(R.id.btnAddEducation);
        btnRemoveEducation = view.findViewById(R.id.btnRemoveEducation);

        educationRecyclerView = view.findViewById(R.id.educationEntriesContainer);
        EntryHandler.loadData(educationList, viewModel.getValue().getEducations(), new Education());

        adapter = new EducationEntry(educationList, 0);
        educationRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        educationRecyclerView.setAdapter(adapter);

        btnPrevious.setOnClickListener(v -> {
            EntryHandler.saveData(viewModel, form -> form.setEducations(educationList));
            ((PreEmpForm) requireActivity()).previousStep(new PreEmpFormStep1());
        });

        btnNext.setOnClickListener(v -> {
            EntryHandler.saveData(viewModel, form -> form.setEducations(educationList));
            ((PreEmpForm) requireActivity()).nextStep(new PreEmpFormStep3());
        });

        btnAddEducation.setOnClickListener(v -> {
            btnRemoveEducation.setVisibility(View.VISIBLE);
            EntryHandler.addEntry(educationList, new Education(), educationRecyclerView, adapter, 10);
        });
        btnRemoveEducation.setOnClickListener(v -> {
            if (educationList.size() == 3) {
                btnRemoveEducation.setVisibility(View.GONE);
            }
            EntryHandler.removeEntry(educationList, educationRecyclerView, adapter, requireContext(), 3);
        });
        return view;
    }

}