package com.example.uaagi_app.ui.users.FragmentProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.uaagi_app.R;

public class ChildProfile extends Fragment {

    private RelativeLayout personalInfoOption;
    private RelativeLayout professionalOption;

    public ChildProfile() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        personalInfoOption = view.findViewById(R.id.PersonalInfo);
        professionalOption = view.findViewById(R.id.ProfessionalInfo);

        personalInfoOption.setOnClickListener(v ->
                loadFragment(new PersonalInfo())
        );

        professionalOption.setOnClickListener(v ->
                loadFragment(new ProfessionalInfo())
        );

        return view;
    }

    private void loadFragment(Fragment fragment) {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.profileOptionsContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}