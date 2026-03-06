package com.example.uaagi_app.ui.users.FragmentsAppliedJobs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.ApplicantViewModel;
import com.example.uaagi_app.network.dto.Applicant;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class SectionInterview extends Fragment {

    private RecyclerView rvInterview;
    private GenericRecyclerAdapter<Applicant> adapter;
    private List<Applicant> interviewList = new ArrayList<>();
    private ApplicantViewModel applicantViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_applied_jobs_section_interview, container, false);

        rvInterview = view.findViewById(R.id.rvInterview);
        rvInterview.setLayoutManager(new LinearLayoutManager(getContext()));

        setupAdapter();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        applicantViewModel = new ViewModelProvider(requireActivity()).get(ApplicantViewModel.class);

        int userId = SessionManager.getInstance(requireContext()).getUserId();
        applicantViewModel.fetchApplicantsForUser(userId, requireContext());

        applicantViewModel.getApplicants().observe(getViewLifecycleOwner(), applicants -> {
            interviewList.clear();

            for (Applicant applicant : applicants) {
                Log.d("Interview Record", "Job: " + applicant.getJobTitle() +
                        ", Company: " + applicant.getCompany() +
                        ", Status: " + applicant.getInterviewStatus());

                if (applicant.getInterviewStatus().equalsIgnoreCase("Scheduled")) {
                    interviewList.add(applicant);
                }
            }

            adapter.updateList(interviewList);
        });
    }

    private void setupAdapter() {
        adapter = new GenericRecyclerAdapter<>(interviewList, R.layout.item_interview_card,
                (view, applicant, position) -> {

                    TextView tvJobTitle = view.findViewById(R.id.tvJobTitle);
                    TextView tvCompany = view.findViewById(R.id.tvCompanyName);
                    TextView tvInterviewDate = view.findViewById(R.id.tvInterviewDate);
                    TextView tvLocation = view.findViewById(R.id.tvLocation);

                    tvJobTitle.setText(safeText(applicant.getJobTitle()));
                    tvCompany.setText(safeText(applicant.getCompany()));
                    tvInterviewDate.setText(safeText(applicant.getInterviewDate()));
                    tvLocation.setText(safeText(applicant.getLocation()));
                });

        adapter.setOnItemClickListener((item, position) -> {
            Toast.makeText(getContext(),
                    "Clicked: " + item.getJobTitle(),
                    Toast.LENGTH_SHORT).show();
        });

        rvInterview.setAdapter(adapter);
    }
    private String safeText(String value) {
        return value != null && !value.isEmpty() ? value : "_";
    }
}