package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.WorkExperience;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.WorkExperienceEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.ArrayList;
import java.util.List;

public class PreEmpFormStep3 extends Fragment {
    private Button btnPrevious, btnNext, btnSubmit;
    private Button btnAddWorkExperience;
    private RecyclerView workExperienceContainer;
    private WorkExperienceEntry adapter;
    private final List<WorkExperience> workExperienceList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preemp_step_3, container, false);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnAddWorkExperience = view.findViewById(R.id.btnAddWorkExperience);

        workExperienceContainer = view.findViewById(R.id.workExperienceEntriesContainer);
        workExperienceList.add(new WorkExperience());
        workExperienceList.add(new WorkExperience());
        workExperienceList.add(new WorkExperience());

        adapter = new WorkExperienceEntry(workExperienceList);
        workExperienceContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        workExperienceContainer.setAdapter(adapter);

        btnAddWorkExperience.setOnClickListener(v -> {
            Log.d("PreEmpFormStep3", "Adding new work experience entry");
            WorkExperience newWorkExp = new WorkExperience();
            workExperienceList.add(newWorkExp);
            adapter.notifyItemInserted(workExperienceList.size() - 1);
            workExperienceContainer.scrollToPosition(workExperienceList.size() - 1);
        });

        btnPrevious.setOnClickListener(v -> {
            ((PreEmpForm) requireActivity()).previousStep(new PreEmpFormStep2());
        });

        btnNext.setOnClickListener(v -> {
            ((PreEmpForm) requireActivity()).nextStep(new PreEmpFormStep4());
        });

        return view;
    }



}