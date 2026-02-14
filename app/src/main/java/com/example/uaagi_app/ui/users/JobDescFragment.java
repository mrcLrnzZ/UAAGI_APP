package com.example.uaagi_app.ui.users;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.uaagi_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobDescFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class JobDescFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobDescFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobDescFragment newInstance(String param1, String param2) {
        JobDescFragment fragment = new JobDescFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public JobDescFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.job_desc, container, false);

        // Find CardView
        View firstJob = view.findViewById(R.id.btnApplyNow);
        View btnBack = view.findViewById(R.id.btnBack);
        // Click listener
        firstJob.setOnClickListener(v -> {
            Fragment secondFragment = new ApplyOptionFragment();

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, secondFragment)
                    .addToBackStack(null) // allows back button
                    .commit();
        });

        btnBack.setOnClickListener(v -> {
            Fragment secondFragment = new CareersFragment();

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, secondFragment)
                    .addToBackStack(null) // allows back button
                    .commit();

        });
        return view;
    }
}