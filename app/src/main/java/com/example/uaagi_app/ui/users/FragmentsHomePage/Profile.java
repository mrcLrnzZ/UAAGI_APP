package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.ProfileViewModel;
import com.example.uaagi_app.ui.users.FragmentProfile.ChildProfile;

public class Profile extends Fragment {

    private ProfileViewModel viewModel;
    private TextView fullNameTv;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigate_profile, container, false);

        fullNameTv = view.findViewById(R.id.FullName);
        progressBar = view.findViewById(R.id.profileProgressBar);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.fetchContent(requireContext());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observe loading state
        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            fullNameTv.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        });

        // Observe profile data
        viewModel.getPreEmpData().observe(getViewLifecycleOwner(), data -> {
            if (data != null && data.getUserInfo() != null) {

                String fullName =
                        data.getUserInfo().getFirstName() + " " +
                                data.getUserInfo().getMiddleName() + " " +
                                data.getUserInfo().getLastName();

                fullNameTv.setText(fullName);
            }
        });

        // Load child fragment
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.profileOptionsContainer, new ChildProfile())
                .commit();
    }

    // 🔥 Hide header ONLY when Profile is visible
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.top_bar)
                .setVisibility(View.GONE);
    }

    // 🔥 Show header again when leaving Profile
    @Override
    public void onPause() {
        super.onPause();
        requireActivity().findViewById(R.id.top_bar)
                .setVisibility(View.VISIBLE);
    }
}