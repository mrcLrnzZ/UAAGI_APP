package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.dto.JobEnums.Company;
import com.example.uaagi_app.ui.users.FragmentsCareers.DivisionOption;
import com.example.uaagi_app.ui.utils.UiHelpers;


public class Brand extends Fragment {
    LinearLayout fotonBrandCard, baicBrandCard, lynkcoBrandCard, muttBrandCard, cheryBrandCard;
    private final String FTON_PHILIPPINES = "Foton Philippines";
    private final String CHERY_PHILIPPINES = "Chery Auto Philippines";
    private final String LYNKCO_PHILIPPINES = "Lynk & Co Philippines";
    private final String MUTT_PHILIPPINES = "Mutt Motorcycle Philippines";
    private final String BAIC_PHILIPPINES = "BAIC Philippines";
    private final String TAG = "BrandFragment";
    public Brand() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigate_brands, container, false);
        initializeViews(view);
        setupListeners();
        return view;
    }
    private void initializeViews(View view) {
        fotonBrandCard = view.findViewById(R.id.fotonBrandCard);
        baicBrandCard = view.findViewById(R.id.baicBrandCard);
        lynkcoBrandCard = view.findViewById(R.id.lynkcoBrandCard);
        muttBrandCard = view.findViewById(R.id.muttBrandCard);
        cheryBrandCard = view.findViewById(R.id.cheryBrandCard);
    }
    private void setupListeners() {
        fotonBrandCard.setOnClickListener(v -> openCareers(Company.FOTON_PHILIPPINES));
        baicBrandCard.setOnClickListener(v -> openCareers(Company.BAIC_PHILIPPINES));
        lynkcoBrandCard.setOnClickListener(v -> openCareers(Company.LYNK_AND_CO_PHILIPPINES));
        muttBrandCard.setOnClickListener(v -> openCareers(Company.MUTT_MOTORCYCLE_PHILIPPINES));
        cheryBrandCard.setOnClickListener(v -> openCareers(Company.CHERY_AUTO_PHILIPPINES));
    }

    private void openCareers(Company company) {
        DivisionOption fragment = DivisionOption.newInstance(company);

        UiHelpers.switchFragment(
                requireActivity().getSupportFragmentManager(),
                fragment
        );
    }
}