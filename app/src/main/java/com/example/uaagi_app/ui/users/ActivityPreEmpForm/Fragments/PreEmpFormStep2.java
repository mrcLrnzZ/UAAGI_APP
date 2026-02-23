package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
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

public class PreEmpFormStep2 extends BaseFormStepFragment {
    private static final String TAG = "PreEmpStep2Lifecycle";
    private Button btnPrevious, btnNext, btnAddEducation, btnRemoveEducation;
    private RecyclerView educationRecyclerView;
    private EducationEntry adapter;
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
        btnRemoveEducation = view.findViewById(R.id.btnRemoveEducation);

        educationRecyclerView = view.findViewById(R.id.educationEntriesContainer);
        adapter = new EducationEntry(educationList, 0);
        EntryHandler.loadData(educationList, viewModel.getValue().getEducations(), Education::new, 3);
        UiHelpers.updateRemoveButtonVisibility(educationList, btnRemoveEducation, 3);
        educationRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
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
        Log.d(TAG, "onResume");

        if(viewModel.getValue().getEducations() == null || viewModel.getValue().getEducations().isEmpty()){
            Log.d(TAG, "loadFormDataStep2: Empty");
            EntryHandler.loadData(educationList, null, Education::new, 3);
        } else {
            Log.d(TAG, "loadFormDataStep2: "+ viewModel.getValue().getEducations().toString());
            EntryHandler.loadData(educationList, viewModel.getValue().getEducations(), Education::new, 3);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EntryHandler.saveData(viewModel, form -> form.setEducations(new ArrayList<>(educationList)));
        Log.d(TAG, "onDestroyView");
    }
}