package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.users.FragmentsCareers.CompanyOptions;
import com.example.uaagi_app.ui.utils.UiHelpers;

public class Careers extends Fragment {

    private LinearLayout internCard, jobCard;



    public Careers() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigate_careers, container, false);
        initViews(view);
        internCard.setOnClickListener(v ->
                        UiHelpers.switchFragment(
                                requireActivity().getSupportFragmentManager(),
                                CompanyOptions.newInstance(true)
                        )
                );
        jobCard.setOnClickListener( v ->
                        UiHelpers.switchFragment(
                                requireActivity().getSupportFragmentManager(),
                                CompanyOptions.newInstance(false)
                        )
                );

        return view;
    }
    private void initViews(View view) {
        internCard = view.findViewById(R.id.internCard);
        jobCard = view.findViewById(R.id.jobCard);
    }
}