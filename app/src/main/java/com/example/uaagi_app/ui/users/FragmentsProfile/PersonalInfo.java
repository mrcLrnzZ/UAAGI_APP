package com.example.uaagi_app.ui.users.FragmentsProfile;

import android.os.Bundle;
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
import com.example.uaagi_app.network.dto.PreEmpDto.Education;
import com.example.uaagi_app.network.dto.PreEmpFetchResponse;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.utils.SimpleTextWatcher;

import java.util.ArrayList;

public class PersonalInfo extends Fragment {
    private RecyclerView rvEducation;
    private GenericRecyclerAdapter<Education> adapter;
    private ProfileViewModel viewModel;
    private boolean isEditEnabled = false;

    private EditText etLastName, etFirstName, etMiddleName, etDob, etGender, etAge, etReligion,
            etCivilStatus, etNationality, etMobileNo, etTelephoneNo, etEmail, etCurrentAddr, etPermanentAddr;

    public PersonalInfo() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_personal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireParentFragment()).get(ProfileViewModel.class);

        etLastName = view.findViewById(R.id.tvLastName);
        etFirstName = view.findViewById(R.id.tvFirstName);
        etMiddleName = view.findViewById(R.id.tvMiddleName);
        etDob = view.findViewById(R.id.Dob);
        etGender = view.findViewById(R.id.tvGender);
        etAge = view.findViewById(R.id.Age);
        etReligion = view.findViewById(R.id.tvReligion);
        etCivilStatus = view.findViewById(R.id.tvCivilStatus);
        etNationality = view.findViewById(R.id.tvNationality);
        etMobileNo = view.findViewById(R.id.tvMobileNo);
        etTelephoneNo = view.findViewById(R.id.tvTelephoneNo);
        etEmail = view.findViewById(R.id.tvEmail);
        etCurrentAddr = view.findViewById(R.id.tvCurrentAddr);
        etPermanentAddr = view.findViewById(R.id.tvPermanentAddr);

        viewModel.getPreEmpData().observe(getViewLifecycleOwner(), data -> {
            if (data != null && data.getUserInfo() != null) {
                // Set text only if not currently editing or if it's the first load
                // To avoid cursor jumping and overwriting user input while they type
                if (!isEditEnabled) {
                    etLastName.setText(data.getUserInfo().getLastName());
                    etFirstName.setText(data.getUserInfo().getFirstName());
                    etMiddleName.setText(data.getUserInfo().getMiddleName());
                    etDob.setText(data.getUserInfo().getDob());
                    etGender.setText(data.getUserInfo().getGender());
                    etAge.setText(data.getUserInfo().getAge());
                    etReligion.setText(data.getUserInfo().getReligion());
                    etCivilStatus.setText(data.getUserInfo().getCivilStatus());
                    etNationality.setText(data.getUserInfo().getNationality());
                    etMobileNo.setText(data.getUserInfo().getCellNo());
                    etTelephoneNo.setText(data.getUserInfo().getTelNo());
                    etEmail.setText(data.getUserInfo().getEmail());
                    etCurrentAddr.setText(data.getUserInfo().getCurrentAddress());
                    etPermanentAddr.setText(data.getUserInfo().getPermanentAddress());
                }
                
                // Always bind listeners to ensure the underlying data object is updated
                bindListeners(data);
            }
        });

        rvEducation = view.findViewById(R.id.rvEducation);
        rvEducation.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new GenericRecyclerAdapter<>(
                new ArrayList<>(),
                R.layout.item_profile_education,
                (itemView, item, position) -> {
                    EditText etSchoolName = itemView.findViewById(R.id.tvSchoolName);
                    EditText etYear = itemView.findViewById(R.id.tvYear);
                    EditText etAward = itemView.findViewById(R.id.tvAward);


                    SimpleTextWatcher.bindTextWatcher(etSchoolName, new SimpleTextWatcher(item::setSchool));
                    SimpleTextWatcher.bindTextWatcher(etYear, new SimpleTextWatcher(item::setGradYear));
                    SimpleTextWatcher.bindTextWatcher(etAward, new SimpleTextWatcher(item::setAchievement));

                    etSchoolName.setText(item.getSchool());
                    etYear.setText(item.getGradYear());
                    etAward.setText(item.getAchievement());

                    etSchoolName.setEnabled(isEditEnabled);
                    etYear.setEnabled(isEditEnabled);
                    etAward.setEnabled(isEditEnabled);
                }
        );

        rvEducation.setAdapter(adapter);
        observeEducation();
    }

    private void bindListeners(PreEmpFetchResponse data) {
        SimpleTextWatcher.bindTextWatcher(etLastName, new SimpleTextWatcher(s -> data.getUserInfo().setLastName(s)));
        SimpleTextWatcher.bindTextWatcher(etFirstName, new SimpleTextWatcher(s -> data.getUserInfo().setFirstName(s)));
        SimpleTextWatcher.bindTextWatcher(etMiddleName, new SimpleTextWatcher(s -> data.getUserInfo().setMiddleName(s)));
        SimpleTextWatcher.bindTextWatcher(etDob, new SimpleTextWatcher(s -> data.getUserInfo().setDob(s)));
        SimpleTextWatcher.bindTextWatcher(etGender, new SimpleTextWatcher(s -> data.getUserInfo().setGender(s)));
        SimpleTextWatcher.bindTextWatcher(etAge, new SimpleTextWatcher(s -> data.getUserInfo().setAge(s)));
        SimpleTextWatcher.bindTextWatcher(etReligion, new SimpleTextWatcher(s -> data.getUserInfo().setReligion(s)));
        SimpleTextWatcher.bindTextWatcher(etCivilStatus, new SimpleTextWatcher(s -> data.getUserInfo().setCivilStatus(s)));
        SimpleTextWatcher.bindTextWatcher(etNationality, new SimpleTextWatcher(s -> data.getUserInfo().setNationality(s)));
        SimpleTextWatcher.bindTextWatcher(etMobileNo, new SimpleTextWatcher(s -> data.getUserInfo().setCellNo(s)));
        SimpleTextWatcher.bindTextWatcher(etTelephoneNo, new SimpleTextWatcher(s -> data.getUserInfo().setTelNo(s)));
        SimpleTextWatcher.bindTextWatcher(etEmail, new SimpleTextWatcher(s -> data.getUserInfo().setEmail(s)));
        SimpleTextWatcher.bindTextWatcher(etCurrentAddr, new SimpleTextWatcher(s -> data.getUserInfo().setCurrentAddress(s)));
        SimpleTextWatcher.bindTextWatcher(etPermanentAddr, new SimpleTextWatcher(s -> data.getUserInfo().setPermanentAddress(s)));
    }

    public void setEditEnabled(boolean enabled) {
        this.isEditEnabled = enabled;
        EditText[] fields = {etLastName, etFirstName, etMiddleName, etDob, etGender, etAge, etReligion,
                etCivilStatus, etNationality, etMobileNo, etTelephoneNo, etEmail, etCurrentAddr, etPermanentAddr};
        for (EditText field : fields) {
            if (field != null) field.setEnabled(enabled);
        }
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    public void syncData() {
        PreEmpFetchResponse data = viewModel.getPreEmpData().getValue();
        if (data == null || data.getUserInfo() == null) return;

        data.getUserInfo().setLastName(etLastName.getText().toString());
        data.getUserInfo().setFirstName(etFirstName.getText().toString());
        data.getUserInfo().setMiddleName(etMiddleName.getText().toString());
        data.getUserInfo().setDob(etDob.getText().toString());
        data.getUserInfo().setGender(etGender.getText().toString());
        data.getUserInfo().setAge(etAge.getText().toString());
        data.getUserInfo().setReligion(etReligion.getText().toString());
        data.getUserInfo().setCivilStatus(etCivilStatus.getText().toString());
        data.getUserInfo().setNationality(etNationality.getText().toString());
        data.getUserInfo().setCellNo(etMobileNo.getText().toString());
        data.getUserInfo().setTelNo(etTelephoneNo.getText().toString());
        data.getUserInfo().setEmail(etEmail.getText().toString());
        data.getUserInfo().setCurrentAddress(etCurrentAddr.getText().toString());
        data.getUserInfo().setPermanentAddress(etPermanentAddr.getText().toString());
    }

    private void observeEducation() {
        viewModel.getEducationList().observe(getViewLifecycleOwner(), educations -> {
            if (educations != null) adapter.updateList(educations);
        });
    }
}
