package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.PreEmploymentForm.Education;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.EducationEntry;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.ArrayList;
import java.util.List;

public class PreEmpFormStep2 extends Fragment {

    private Button btnPrevious, btnNext, btnAddEducation, btnRemoveEducation;
    private RecyclerView educationRecyclerView;
    private EducationEntry adapter;
    private final List<Education> educationList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("PreEmpFormStep2", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_preemp_step_2, container, false);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnAddEducation = view.findViewById(R.id.btnAddEducation);
        btnRemoveEducation = view.findViewById(R.id.btnRemoveEducation);

        educationRecyclerView = view.findViewById(R.id.educationEntriesContainer);

        educationList.add(new Education());
        educationList.add(new Education());
        educationList.add(new Education());

        adapter = new EducationEntry(educationList, 0);
        educationRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        educationRecyclerView.setAdapter(adapter);

        btnPrevious.setOnClickListener(v ->
                ((PreEmpForm) requireActivity()).previousStep(new PreEmpFormStep1())
        );

        btnNext.setOnClickListener(v ->
                ((PreEmpForm) requireActivity()).nextStep(new PreEmpFormStep3())
        );

        btnAddEducation.setOnClickListener(v -> {
            AddEducationEntry();
        });
        btnRemoveEducation.setOnClickListener(v -> {
            RemoveEducationEntry();
        });
        return view;
    }
    private void AddEducationEntry() {
        Education newEdu = new Education();
        educationList.add(newEdu);
        adapter.notifyItemInserted(educationList.size() - 1);
        educationRecyclerView.scrollToPosition(educationList.size() - 1);
        btnRemoveEducation.setVisibility(View.VISIBLE);
    }
    private void RemoveEducationEntry() {
        if (educationList.size() > 3) {
            int removeIndex = educationList.size() - 1;
            educationList.remove(removeIndex);
            adapter.notifyItemRemoved(removeIndex);
            if (educationList.size() <= 3) btnRemoveEducation.setVisibility(View.GONE);
        }else{
            UiHelpers.showToast("Cannot remove more entries", requireContext());
        }
    }
}