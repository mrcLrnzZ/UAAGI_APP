package com.example.uaagi_app.ui.users.FragmentsProfile;

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
import com.example.uaagi_app.network.dto.PreEmpDto.Education;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PersonalInfo extends Fragment {
    private RecyclerView rvEducation;
    private List<Education> educationList;
    private GenericRecyclerAdapter<Education> adapter;
    private ProfileViewModel viewModel;
    public PersonalInfo() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireParentFragment())
                .get(ProfileViewModel.class);


        TextView tvLastName = view.findViewById(R.id.tvLastName);
        TextView tvFirstName = view.findViewById(R.id.tvFirstName);
        TextView tvMiddleName = view.findViewById(R.id.tvMiddleName);
        TextView tvDob = view.findViewById(R.id.Dob);
        TextView tvGender = view.findViewById(R.id.tvGender);
        TextView tvAge = view.findViewById(R.id.Age);
        TextView tvReligion = view.findViewById(R.id.tvReligion);
        TextView tvCivilStatus = view.findViewById(R.id.tvCivilStatus);
        TextView tvNationality = view.findViewById(R.id.tvNationality);
        TextView tvMobileNo = view.findViewById(R.id.tvMobileNo);
        TextView tvTelephoneNo = view.findViewById(R.id.tvTelephoneNo);
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        TextView tvCurrentAddr = view.findViewById(R.id.tvCurrentAddr);
        TextView tvPermanentAddr = view.findViewById(R.id.tvPermanentAddr);

        viewModel.getPreEmpData().observe(getViewLifecycleOwner(), data -> {
            if (data != null && data.getUserInfo() != null) {

                tvLastName.setText(data.getUserInfo().getLastName());
                tvFirstName.setText(data.getUserInfo().getFirstName());
                tvMiddleName.setText(data.getUserInfo().getMiddleName());
                tvDob.setText(data.getUserInfo().getDob());
                tvGender.setText(data.getUserInfo().getGender());
                tvAge.setText(String.valueOf(data.getUserInfo().getAge()));
                tvReligion.setText(data.getUserInfo().getReligion());
                tvCivilStatus.setText(data.getUserInfo().getCivilStatus());
                tvNationality.setText(data.getUserInfo().getNationality());
                tvMobileNo.setText(data.getUserInfo().getCellNo());
                tvTelephoneNo.setText(data.getUserInfo().getTelNo());
                tvEmail.setText(data.getUserInfo().getEmail());
                tvCurrentAddr.setText(data.getUserInfo().getCurrentAddress());
                tvPermanentAddr.setText(data.getUserInfo().getPermanentAddress());
            }
        });

        rvEducation = view.findViewById(R.id.rvEducation);
        rvEducation.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new GenericRecyclerAdapter<>(
                new ArrayList<>(),
                R.layout.item_profile_education,
                (itemView, item, position) -> {

                    TextView tvLevel = itemView.findViewById(R.id.tvLevel);
                    TextView tvSchoolName = itemView.findViewById(R.id.tvSchoolName);
                    TextView tvYear = itemView.findViewById(R.id.tvYear);
                    TextView tvAward = itemView.findViewById(R.id.tvAward);

                    tvLevel.setText(item.getLevel());
                    tvSchoolName.setText(item.getSchool());
                    tvYear.setText(item.getGradYear());
                    tvAward.setText(item.getAchievement());
                }
        );

        rvEducation.setAdapter(adapter);

        observeEducation();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile_personal, container, false);
    }
    private void observeEducation() {
        viewModel.getEducationList().observe(getViewLifecycleOwner(), educations -> {
            if (educations != null) {
                adapter.updateList(educations);
            }
        });
    }
}