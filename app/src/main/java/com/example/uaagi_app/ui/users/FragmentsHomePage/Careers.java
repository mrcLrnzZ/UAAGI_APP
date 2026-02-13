package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.users.FragmentsCareers.JobDesc;
import com.example.uaagi_app.ui.utils.UiHelpers;

public class Careers extends Fragment {

    public Careers() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigate_careers, container, false);

        // Find CardView
        View firstJob = view.findViewById(R.id.fotonjob);

        // Click listener
        firstJob.setOnClickListener(v -> {
            UiHelpers.switchFragment(requireActivity().getSupportFragmentManager(), new JobDesc());
        });

        return view;
    }
}