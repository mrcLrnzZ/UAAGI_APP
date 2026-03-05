package com.example.uaagi_app.ui.users.FragmentsHomePage;

import static com.example.uaagi_app.network.RetrofitClient.IMAGE_BASE_URL;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.ProfileViewModel;
import com.example.uaagi_app.network.Services.UploadImageService;
import com.example.uaagi_app.ui.users.FragmentsProfile.ChildProfile;
import com.example.uaagi_app.utils.SessionManager;

public class Profile extends Fragment {

    private ProfileViewModel viewModel;
    private static final String TAG = "Profile";
    private TextView fullNameTv;
    private ProgressBar progressBar;
    private ImageView profilePhoto;
    private ActivityResultLauncher<String> imagePickerLauncher;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigate_profile, container, false);
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        UploadImageService.getInstance().uploadImage(uri, requireContext(), profilePhoto);
                    }
                });
        fullNameTv = view.findViewById(R.id.FullName);
        progressBar = view.findViewById(R.id.profileProgressBar);
        profilePhoto = view.findViewById(R.id.ProfilePhoto);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.fetchContent(requireContext());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int userId = SessionManager.getInstance(requireContext()).getUserId();
        String imageUrl = IMAGE_BASE_URL + "user_" + userId + ".jpg?ts=" + System.currentTimeMillis();


        Glide.with(requireContext())
                .load(imageUrl)
                .circleCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(profilePhoto);

        profilePhoto.setOnClickListener(v -> {
            imagePickerLauncher.launch("image/*");
        });

        ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        UploadImageService.getInstance().uploadImage(uri, requireContext(), profilePhoto);
                    }
                }
        );

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            fullNameTv.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        });

        viewModel.getPreEmpData().observe(getViewLifecycleOwner(), data -> {
            if (data != null && data.getUserInfo() != null) {
                Log.d(TAG, "onViewCreated: "+ data.getEducation());
                String fullName =
                        data.getUserInfo().getFirstName() + " " +
                                data.getUserInfo().getMiddleName() + " " +
                                data.getUserInfo().getLastName();

                fullNameTv.setText(fullName);
            }
        });

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.profileOptionsContainer, new ChildProfile())
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.top_bar)
                .setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().findViewById(R.id.top_bar)
                .setVisibility(View.VISIBLE);
    }


}