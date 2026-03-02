package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.users.FragmentProfile.ChildProfile;

public class Profile extends Fragment {

    public Profile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_navigate_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().findViewById(R.id.top_bar)
                .setVisibility(View.GONE);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.profileOptionsContainer, new ChildProfile())
                .commit();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().findViewById(R.id.top_bar)
                .setVisibility(View.VISIBLE);
    }
}