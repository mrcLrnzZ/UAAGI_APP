package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.utils.UiHelpers;

public class ApplyOption extends Fragment {
    private TextView jobTitleTV, jobLocCom;
    private String jobTitle;
    private String jobLocation;
    private String jobCompany;
    public ApplyOption() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_careers_apply_options, container, false);
        String jobID = getArgumentsFromBundle();
        if (getArguments() != null) {
            jobTitle = getArguments().getString("jobTitle");
            jobLocation = getArguments().getString("jobLocation");
            jobCompany = getArguments().getString("jobCompany");

        }
        jobTitleTV = view.findViewById(R.id.jobTitle);
        jobLocCom = view.findViewById(R.id.jobCompany_jobLocation);
        jobTitleTV.setText(jobTitle);
        jobLocCom.setText(String.format("%s • %s", jobCompany, jobLocation));
        // Find CardView
        View btnBackToDesc = view.findViewById(R.id.btnBackToDesc);

        // Click listener
        btnBackToDesc.setOnClickListener(v -> {
            JobDesc fragment = new JobDesc();

            Bundle bundle = new Bundle();
            String jobId = String.valueOf(jobID);
            bundle.putString("jobId", jobId);
            fragment.setArguments(bundle);
            UiHelpers.switchFragment(requireActivity().getSupportFragmentManager(),fragment);
        });

        return view;
    }
    private String getArgumentsFromBundle(){
        if (getArguments() != null) {
            return getArguments().getString("jobId");
        }
        return null;
    }
}