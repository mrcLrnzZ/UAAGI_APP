package com.example.uaagi_app.ui.users.FragmentsProfile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.ProfileViewModel;
import com.example.uaagi_app.network.dto.PreEmpDto.Certificate;
import com.example.uaagi_app.network.dto.PreEmpDto.ProfessionalSkills;
import com.example.uaagi_app.network.dto.PreEmpDto.WorkExperience;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;

import java.util.ArrayList;

public class ProfessionalInfo extends Fragment {
    private RecyclerView rvWorkExperience;
    private RecyclerView rvCertificates;
    private RecyclerView rvSkills;
    private GenericRecyclerAdapter<WorkExperience> adapterWorkExperience;
    private GenericRecyclerAdapter<Certificate> adapterCertificates;
    private GenericRecyclerAdapter<ProfessionalSkills> adapterSkills;
    private ProfileViewModel viewModel;
    private boolean isEditEnabled = false;

    public ProfessionalInfo() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_professional, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireParentFragment()).get(ProfileViewModel.class);
        setupWorkExperience(view);
        setupCertificates(view);
        setupSkills(view);
        observeProfessionalData();
    }

    private void setupWorkExperience(View view) {
        rvWorkExperience = view.findViewById(R.id.rvWorkExperience);
        rvWorkExperience.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterWorkExperience = new GenericRecyclerAdapter<>(new ArrayList<>(), R.layout.item_profile_workexp, (itemView, item, position) -> {
            EditText etCompany = itemView.findViewById(R.id.tvWorkCompany);
            EditText etPosition = itemView.findViewById(R.id.tvWorkPosition);
            EditText etDesc = itemView.findViewById(R.id.tvWorkDescription);

            etCompany.setText(item.getCompany());
            etPosition.setText(item.getPosition());
            etDesc.setText(item.getDescription());

            etCompany.setEnabled(isEditEnabled);
            etPosition.setEnabled(isEditEnabled);
            etDesc.setEnabled(isEditEnabled);

            etCompany.addTextChangedListener(new SimpleTextWatcher(s -> item.setCompany(s)));
            etPosition.addTextChangedListener(new SimpleTextWatcher(s -> item.setPosition(s)));
            etDesc.addTextChangedListener(new SimpleTextWatcher(s -> item.setDescription(s)));
        });
        rvWorkExperience.setAdapter(adapterWorkExperience);
    }

    private void setupCertificates(View view) {
        rvCertificates = view.findViewById(R.id.rvCertificates);
        rvCertificates.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCertificates = new GenericRecyclerAdapter<>(new ArrayList<>(), R.layout.item_profile_cert, (itemView, item, position) -> {
            EditText etTitle = itemView.findViewById(R.id.tvCertTitle);
            EditText etOrg = itemView.findViewById(R.id.tvCertOrganization);

            etTitle.setText(item.getName());
            etOrg.setText(item.getOrganization());

            etTitle.setEnabled(isEditEnabled);
            etOrg.setEnabled(isEditEnabled);

            etTitle.addTextChangedListener(new SimpleTextWatcher(s -> item.setName(s)));
            etOrg.addTextChangedListener(new SimpleTextWatcher(s -> item.setOrganization(s)));
        });
        rvCertificates.setAdapter(adapterCertificates);
    }

    private void setupSkills(View view) {
        rvSkills = view.findViewById(R.id.rvSkills);
        rvSkills.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterSkills = new GenericRecyclerAdapter<>(new ArrayList<>(), R.layout.item_profile_skill, (itemView, item, position) -> {
            EditText etType = itemView.findViewById(R.id.tvSkillType);
            EditText etDesc = itemView.findViewById(R.id.tvSkillDescription);

            etType.setText(item.getCategory());
            etDesc.setText(item.getDescription());

            etType.setEnabled(isEditEnabled);
            etDesc.setEnabled(isEditEnabled);

            etType.addTextChangedListener(new SimpleTextWatcher(s -> item.setCategory(s)));
            etDesc.addTextChangedListener(new SimpleTextWatcher(s -> item.setDescription(s)));
        });
        rvSkills.setAdapter(adapterSkills);
    }

    public void setEditEnabled(boolean enabled) {
        this.isEditEnabled = enabled;
        if (adapterWorkExperience != null) adapterWorkExperience.notifyDataSetChanged();
        if (adapterCertificates != null) adapterCertificates.notifyDataSetChanged();
        if (adapterSkills != null) adapterSkills.notifyDataSetChanged();
    }

    private void observeProfessionalData() {
        viewModel.getPreEmpData().observe(getViewLifecycleOwner(), data -> {
            if (data == null) return;
            if (data.getWorkExperience() != null) adapterWorkExperience.updateList(data.getWorkExperience());
            if (data.getCertificate() != null) adapterCertificates.updateList(data.getCertificate());
            if (data.getProfessionalSkills() != null) adapterSkills.updateList(data.getProfessionalSkills());
        });
    }

}
