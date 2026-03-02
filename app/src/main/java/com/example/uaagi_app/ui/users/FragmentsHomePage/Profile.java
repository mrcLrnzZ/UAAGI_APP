package com.example.uaagi_app.ui.users.FragmentsHomePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigate_profile, container, false);

        fullNameTv = view.findViewById(R.id.FullName);
        progressBar = view.findViewById(R.id.profileProgressBar);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.fetchContent(requireContext());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            fullNameTv.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        });

        viewModel.getPreEmpData().observe(getViewLifecycleOwner(), data -> {
            if (data != null && data.getUserInfo() != null) {
                String fullName = data.getUserInfo().getFirstName() + " " +
                        data.getUserInfo().getMiddleName() + " " +
                        data.getUserInfo().getLastName();
                fullNameTv.setText(fullName);
            }
        });

        requireActivity().findViewById(R.id.top_bar).setVisibility(View.GONE);

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.profileOptionsContainer, new ChildProfile())
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().findViewById(R.id.top_bar).setVisibility(View.VISIBLE);
    }
}