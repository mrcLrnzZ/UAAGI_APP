package com.example.uaagi_app.ui.users.FragmentProfile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.ProfileViewModel;
import com.example.uaagi_app.network.dto.PreEmpDto.Certificate;
import com.example.uaagi_app.network.dto.PreEmpDto.ProfessionalSkills;
import com.example.uaagi_app.network.dto.PreEmpDto.WorkExperience;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfessionalInfo extends Fragment {
    private RecyclerView rvWorkExperience;
    private RecyclerView rvCertificates;
    private RecyclerView rvSkills;
    private GenericRecyclerAdapter<WorkExperience> adapterWorkExperience;
    private GenericRecyclerAdapter<Certificate> adapterCertificates;
    private GenericRecyclerAdapter<ProfessionalSkills> adapterSkills;
    private List<WorkExperience> workExperienceList;
    private List<Certificate> certificateList;
    private List<ProfessionalSkills> skillsList;
    private ProfileViewModel viewModel;

    public ProfessionalInfo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_professional, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireParentFragment())
                .get(ProfileViewModel.class);

        rvWorkExperience = view.findViewById(R.id.rvWorkExperience);
        rvWorkExperience.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterWorkExperience = new GenericRecyclerAdapter<>(
                new ArrayList<>(),
                R.layout.item_profile_workexp,
                (itemView, item, position) -> {

                    TextView tvPosition = itemView.findViewById(R.id.tvWorkPosition);
                    TextView tvCompany = itemView.findViewById(R.id.tvWorkCompany);
                    TextView tvWorkDesc = itemView.findViewById(R.id.tvWorkDescription);

                    tvPosition.setText(item.getPosition());
                    tvCompany.setText(item.getCompany());
                    tvWorkDesc.setText(item.getDescription());
                }
        );

        rvWorkExperience.setAdapter(adapterWorkExperience);

        rvCertificates = view.findViewById(R.id.rvCertificates);
        rvCertificates.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterCertificates = new GenericRecyclerAdapter<>(
                new ArrayList<>(),
                R.layout.item_profile_cert,
                (itemView, item, position) -> {

                    TextView tvCertName = itemView.findViewById(R.id.tvCertTitle);
                    TextView tvCertOrg = itemView.findViewById(R.id.tvCertOrganization);


                    tvCertName.setText(item.getName());
                    tvCertOrg.setText(item.getOrganization());
                }
        );

        rvCertificates.setAdapter(adapterCertificates);

        rvSkills = view.findViewById(R.id.rvSkills);
        rvSkills.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterSkills = new GenericRecyclerAdapter<>(
                new ArrayList<>(),
                R.layout.item_profile_skill,
                (itemView, item, position) -> {

                    TextView tvSkill = itemView.findViewById(R.id.tvSkillType);
                    TextView tvSkillDesc = itemView.findViewById(R.id.tvSkillDescription);

                    tvSkill.setText(item.getCategory());
                    tvSkillDesc.setText(item.getDescription());
                }
        );

        rvSkills.setAdapter(adapterSkills);

        observeProfessionalData();
    }
    private void observeProfessionalData() {

        viewModel.getPreEmpData().observe(getViewLifecycleOwner(), data -> {

            if (data == null) return;
            if (data.getWorkExperience() != null) {
                adapterWorkExperience.updateList(data.getWorkExperience());
            }
            if (data.getCertificate() != null) {
                adapterCertificates.updateList(data.getCertificate());
            }
            if (data.getProfessionalSkills() != null) {
                adapterSkills.updateList(data.getProfessionalSkills());
            }
        });
    }
}