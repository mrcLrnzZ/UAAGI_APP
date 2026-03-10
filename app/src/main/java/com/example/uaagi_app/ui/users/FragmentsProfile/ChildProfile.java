package com.example.uaagi_app.ui.users.FragmentsProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.ProfileViewModel;

public class ChildProfile extends Fragment {

    private RelativeLayout personalInfoOption;
    private RelativeLayout professionalOption;
    private TextView profileEmail;
    private TextView profileContact;
    private TextView profileAdd;
    private ProfileViewModel viewModel;
    private ProgressBar progressBar;
    private LinearLayout profileContainer;


    public ChildProfile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        personalInfoOption = view.findViewById(R.id.PersonalInfo);
        professionalOption = view.findViewById(R.id.ProfessionalInfo);
        profileContainer = view.findViewById(R.id.profileContainer);
        progressBar = view.findViewById(R.id.profileProgressBar);
        profileEmail = view.findViewById(R.id.ProfileEmail);
        profileContact = view.findViewById(R.id.ProfileContact);
        profileAdd = view.findViewById(R.id.ProfileAdd);

        viewModel = new ViewModelProvider(
                requireParentFragment() != null ? requireParentFragment() : this
        ).get(ProfileViewModel.class);

        personalInfoOption.setOnClickListener(v ->
                loadFragment(new PersonalInfo())
        );

        professionalOption.setOnClickListener(v ->
                loadFragment(new ProfessionalInfo())
        );

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
                profileContainer.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                profileContainer.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getPreEmpData().observe(getViewLifecycleOwner(), data -> {
            if (data != null && data.getUserInfo() != null) {
                profileEmail.setText(data.getUserInfo().getEmail());
                profileContact.setText(data.getUserInfo().getCellNo());
                profileAdd.setText(data.getUserInfo().getCurrentAddress());
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.profileOptionsContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}