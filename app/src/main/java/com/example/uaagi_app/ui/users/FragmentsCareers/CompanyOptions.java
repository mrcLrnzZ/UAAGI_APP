package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.dto.JobEnums.Company;
import com.example.uaagi_app.ui.users.FragmentsHomePage.Careers;
import com.example.uaagi_app.ui.utils.UiHelpers;


public class CompanyOptions extends Fragment {
    CardView fotonBrandCard, baicBrandCard, lynkcoBrandCard, muttBrandCard, cheryBrandCard;
    private static final String ARG_IS_INTERN = "isIntern";
    private final String FTON_PHILIPPINES = "Foton Philippines";
    private final String CHERY_PHILIPPINES = "Chery Auto Philippines";
    private final String LYNKCO_PHILIPPINES = "Lynk & Co Philippines";
    private final String MUTT_PHILIPPINES = "Mutt Motorcycle Philippines";
    private final String BAIC_PHILIPPINES = "BAIC Philippines";
    private final String TAG = "BrandFragment";
    private boolean isIntern;

    private CompanyOptions() {
        // Required empty public constructor
    }
    public static CompanyOptions newInstance(boolean isIntern) {
        CompanyOptions fragment = new CompanyOptions();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_INTERN, isIntern);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isIntern = getArguments().getBoolean(ARG_IS_INTERN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_careers_company, container, false);
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
        DivisionOption fragment = DivisionOption.newInstance(company, isIntern);

        UiHelpers.switchFragment(
                requireActivity().getSupportFragmentManager(),
                fragment
        );
    }
}