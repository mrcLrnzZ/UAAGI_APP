package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.uaagi_app.R;

public class Profile extends Fragment {

    public Profile() {
        // Required empty public constructor
    }

    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_navigate_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hide the top bar when Profile is opened
        requireActivity().findViewById(R.id.top_bar)
                .setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Show the top bar again when leaving Profile
        requireActivity().findViewById(R.id.top_bar)
                .setVisibility(View.VISIBLE);
    }
}