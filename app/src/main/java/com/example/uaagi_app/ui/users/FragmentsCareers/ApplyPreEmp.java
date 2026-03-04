package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.JobViewModel;
import com.example.uaagi_app.data.viewmodel.ProfileViewModel;
import com.example.uaagi_app.network.dto.PreEmpDto.Education;
import com.example.uaagi_app.network.dto.PreEmpDto.ProfessionalSkills;
import com.example.uaagi_app.network.dto.PreEmpDto.Seminar;
import com.example.uaagi_app.network.dto.PreEmpDto.WorkExperience;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.Helpers;

public class ApplyPreEmp extends Fragment {
    private TextView jobTitle, jobCompanyLocation, fullName, email, tvPhone, tvAddress;
    private TextView summaryFullName, summaryEmail, summaryPhone, summaryCurrAddr;
    private RecyclerView rvSeminars, rvSkillsQualifications, rvEducation, rvAppliedJobs;
    private GenericRecyclerAdapter<Seminar> adapterSeminars;
    private GenericRecyclerAdapter<ProfessionalSkills> adapterSkillsQualifications;
    private GenericRecyclerAdapter<Education> adapterEducation;
    private GenericRecyclerAdapter<WorkExperience> adapterWorkExperience;
    private Button btnSubmitApplication;
    private ImageButton btnBackToDesc;
    private JobViewModel jobViewModel;
    private ProfileViewModel profileViewModel;

    public static ApplyPreEmp newInstance(int jobId) {
        ApplyPreEmp fragment = new ApplyPreEmp();
        Bundle args = new Bundle();
        args.putInt("jobId", jobId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_careers_applypreview, container, false);
        initializeViews(view);

        int jobId = getArguments() != null ? getArguments().getInt("jobId") : -1;
        jobViewModel = new ViewModelProvider(requireActivity()).get(JobViewModel.class);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        setupRecyclerViews();

        observeJobData();
        observeProfileData();

        setupButtons();
        if (jobId != -1) {
            jobViewModel.fetchJob(jobId, requireContext());
        }

        profileViewModel.fetchContent(requireContext());

        return view;
    }
    private void observeJobData() {

        jobViewModel.getJobData().observe(getViewLifecycleOwner(), job -> {

            if (job != null) {

                jobTitle.setText(job.getJobTitle());
                jobCompanyLocation.setText(job.getCompany().getDisplayName() + " • " + job.getLocation());

            }
        });

        jobViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void observeProfileData() {

        profileViewModel.getPreEmpData().observe(getViewLifecycleOwner(), data -> {

            if (data != null) {

                String fullNameTxt =
                        data.getUserInfo().getFirstName() + " " +
                                data.getUserInfo().getMiddleName() + " " +
                                data.getUserInfo().getLastName();

                fullName.setText(fullNameTxt);
                email.setText(data.getUserInfo().getEmail());
                tvPhone.setText(data.getUserInfo().getCellNo());
                tvAddress.setText(data.getUserInfo().getCurrentAddress());
                summaryFullName.setText(fullNameTxt);
                summaryEmail.setText(data.getUserInfo().getEmail());
                summaryPhone.setText(data.getUserInfo().getCellNo());
                summaryCurrAddr.setText(data.getUserInfo().getCurrentAddress());

                adapterEducation.updateList(data.getEducation());
                adapterSeminars.updateList(data.getSeminar());
                adapterSkillsQualifications.updateList(data.getProfessionalSkills());
                adapterWorkExperience.updateList(data.getWorkExperience());
            }
        });

        profileViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupRecyclerViews() {

        rvEducation.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvSeminars.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvSkillsQualifications.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvAppliedJobs.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapterEducation = new GenericRecyclerAdapter<>(
                null,
                R.layout.item_education_block,
                (view, item, position) -> {

                    TextView school = view.findViewById(R.id.tvEducationSchool);
                    TextView year = view.findViewById(R.id.tvEducationYear);
                    TextView achievement = view.findViewById(R.id.tvEducationAchievement);

                    school.setText(Helpers.safeText(item.getSchool()));
                    year.setText(Helpers.safeText(item.getGradYear()));
                    achievement.setText(Helpers.safeText(item.getAchievement()));
                });

        adapterSeminars = new GenericRecyclerAdapter<>(
                null,
                R.layout.item_seminar_block,
                (view, item, position) -> {

                    TextView title = view.findViewById(R.id.tvSeminarTitle);
                    TextView date = view.findViewById(R.id.tvSeminarDate);

                    title.setText(item.getTitle());
                    date.setText(item.getDate());
                });

        adapterSkillsQualifications = new GenericRecyclerAdapter<>(
                null,
                R.layout.item_skill_qualification_block,
                (view, item, position) -> {

                    TextView skill = view.findViewById(R.id.tvSkillType);
                    TextView description = view.findViewById(R.id.tvSkillDescription);
                    TextView qualification = view.findViewById(R.id.tvQualification);

                    skill.setText(Helpers.safeText(item.getCategory()));
                    description.setText(Helpers.safeText(item.getDescription()));
                    qualification.setText(Helpers.safeText(item.getCategory()));
                });

        adapterWorkExperience = new GenericRecyclerAdapter<>(
                null,
                R.layout.item_work_exp_block,
                (view, item, position) -> {

                    TextView jobPosition = view.findViewById(R.id.tvWorkPositionValue);
                    TextView company = view.findViewById(R.id.tvCompanyValue);
                    TextView achievement = view.findViewById(R.id.tvWorkAchievement);

                    company.setText(Helpers.safeText(item.getCompany()));
                    jobPosition.setText(Helpers.safeText(item.getPosition()));
                    achievement.setText(Helpers.safeText(item.getDescription()));
                });

        rvEducation.setAdapter(adapterEducation);
        rvSeminars.setAdapter(adapterSeminars);
        rvSkillsQualifications.setAdapter(adapterSkillsQualifications);
        rvAppliedJobs.setAdapter(adapterWorkExperience);
    }
    private void initializeViews(View view) {
        jobTitle = view.findViewById(R.id.jobTitle);
        jobCompanyLocation = view.findViewById(R.id.jobCompany_jobLocation);
        fullName = view.findViewById(R.id.FullName);
        email = view.findViewById(R.id.Email);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvAddress = view.findViewById(R.id.tvAddress);
        summaryFullName = view.findViewById(R.id.summaryFullName);
        summaryEmail = view.findViewById(R.id.summaryEmail);
        summaryPhone = view.findViewById(R.id.summaryPhone);
        summaryCurrAddr = view.findViewById(R.id.summaryCurrAddr);

        rvSeminars = view.findViewById(R.id.rvSeminars);
        rvSkillsQualifications = view.findViewById(R.id.rvSkillsQualifications);
        rvEducation = view.findViewById(R.id.rvEducation);
        rvAppliedJobs = view.findViewById(R.id.rvAppliedJobs);

        btnSubmitApplication = view.findViewById(R.id.btnSubmitApplication);
        btnBackToDesc = view.findViewById(R.id.btnBackToDesc);
    }
    private void setupButtons() {

        btnBackToDesc.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .popBackStack();
        });

        btnSubmitApplication.setOnClickListener(v -> {

            if (jobViewModel.getJobData().getValue() == null) {
                Toast.makeText(requireContext(),
                        "Job data not loaded yet.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(requireContext(),
                    "Application Submitted Successfully!",
                    Toast.LENGTH_LONG).show();
        });
    }
}