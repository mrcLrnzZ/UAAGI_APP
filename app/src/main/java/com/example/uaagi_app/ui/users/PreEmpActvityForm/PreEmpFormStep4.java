package com.example.uaagi_app.ui.users.PreEmpActvityForm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.uaagi_app.R;

public class PreEmpFormStep4 extends Fragment {
    private Button btnPrevious, btnNext, btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preemp_step_4, container, false);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnSubmit = view.findViewById(R.id.btnSubmit);


        btnPrevious.setOnClickListener(v -> {
            ((PreEmpStepperActivity) requireActivity()).previousStep(new PreEmpFormStep3());
        });

        btnNext.setOnClickListener(v -> {
            ((PreEmpStepperActivity) requireActivity()).nextStep(new PreEmpFormStep5());
        });

        return view;
    }
}