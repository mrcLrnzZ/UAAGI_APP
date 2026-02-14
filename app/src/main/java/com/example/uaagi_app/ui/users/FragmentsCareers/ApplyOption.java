package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.utils.UiHelpers;

public class ApplyOption extends Fragment {

    public ApplyOption() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_careers_apply_options, container, false);

        // Find CardView
        View btnBackToDesc = view.findViewById(R.id.btnBackToDesc);

        // Click listener
        btnBackToDesc.setOnClickListener(v -> {
            UiHelpers.switchFragment(requireActivity().getSupportFragmentManager(), new JobDesc());
        });

        return view;
    }
}