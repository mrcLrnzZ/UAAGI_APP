package com.example.uaagi_app.ui.users.FragmentsCareers;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.users.FragmentsHomePage.Careers;
import com.example.uaagi_app.ui.utils.UiHelpers;

public class JobDesc extends Fragment {

    View btnApplyNow, btnBack;

    public JobDesc(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_careers_job_desc, container, false);
        btnApplyNow = view.findViewById(R.id.btnApplyNow);
        btnBack = view.findViewById(R.id.btnBack);

        btnApplyNow.setOnClickListener(v -> {
            UiHelpers.switchFragment(requireActivity().getSupportFragmentManager(), new ApplyOption());
        });

        btnBack.setOnClickListener(v -> {
            UiHelpers.switchFragment(requireActivity().getSupportFragmentManager(), new Careers());
        });


        return view;
    }
}