package com.example.uaagi_app.ui.users.FragmentsHomePage;

import static com.example.uaagi_app.network.RetrofitClient.IMAGE_BASE_URL;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.uaagi_app.network.dto.PreEmpDto.UserInfo;
import com.example.uaagi_app.network.dto.PreEmpFetchResponse;
import com.example.uaagi_app.network.dto.UpdateProfileDTO;
import com.example.uaagi_app.ui.users.ActivityLoginPage;
import com.example.uaagi_app.ui.users.FragmentsProfile.ChildProfile;
import com.example.uaagi_app.ui.users.FragmentsProfile.PersonalInfo;
import com.example.uaagi_app.ui.users.FragmentsProfile.ProfessionalInfo;
import com.example.uaagi_app.utils.SessionManager;

public class Profile extends Fragment {

    private ProfileViewModel viewModel;
    private static final String TAG = "Profile";
    private TextView fullNameTv;
    private ProgressBar progressBar;
    private ImageView profilePhoto, btnLogout, btnEdit;
    private ImageButton btnBack;
    private ActivityResultLauncher<String> imagePickerLauncher;
    private boolean isEditing = false;

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
        btnLogout = view.findViewById(R.id.logout);
        btnEdit = view.findViewById(R.id.btnedit);
        btnBack = view.findViewById(R.id.btnback);


        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.fetchContent(requireContext());

        return view;
    }


    public void updateHeaderButtons(boolean isSubProfile) {
        if (btnLogout != null && btnEdit != null && btnBack != null) {
            btnLogout.setVisibility(isSubProfile ? View.GONE : View.VISIBLE);
            btnEdit.setVisibility(isSubProfile ? View.VISIBLE : View.GONE);
            btnBack.setVisibility(isSubProfile ? View.VISIBLE : View.GONE);
            
            if (!isSubProfile) {
                isEditing = false;
                btnEdit.setImageResource(R.drawable.edit);
            }
        }
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

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            fullNameTv.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        });

        viewModel.getUpdateSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getPreEmpData().observe(getViewLifecycleOwner(), data -> {
            if (data != null && data.getUserInfo() != null) {
                String fullName =
                        data.getUserInfo().getFirstName() + " " +
                                data.getUserInfo().getMiddleName() + " " +
                                data.getUserInfo().getLastName();

                fullNameTv.setText(fullName);
            }
        });

        btnLogout.setOnClickListener(v -> {
            Dialog dialog = new Dialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.item_profile_logout);

            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            ImageView btnClose = dialog.findViewById(R.id.btnClose);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            Button btnSignOut = dialog.findViewById(R.id.btnSignOut);

            btnClose.setOnClickListener(e -> dialog.dismiss());
            btnCancel.setOnClickListener(e -> dialog.dismiss());

            btnSignOut.setOnClickListener(e -> {
                SessionManager.getInstance(requireContext()).logout();
                requireActivity()
                        .getSupportFragmentManager()
                        .popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);

                Intent intent = new Intent(requireContext(), ActivityLoginPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
                dialog.dismiss();
            });
            dialog.show();
        });

        btnBack.setOnClickListener(v -> {
            if (getChildFragmentManager().getBackStackEntryCount() > 0) {
                getChildFragmentManager().popBackStack();
                updateHeaderButtons(false);
            }
        });

        btnEdit.setOnClickListener(v -> {
            Fragment current = getChildFragmentManager().findFragmentById(R.id.profileOptionsContainer);
            
            isEditing = !isEditing;
            btnEdit.setImageResource(isEditing ? R.drawable.ic_check_circle : R.drawable.edit);

            if (current instanceof PersonalInfo) {
                PersonalInfo personalInfo = (PersonalInfo) current;
                if (!isEditing) {
                    personalInfo.syncData();
                }
                personalInfo.setEditEnabled(isEditing);
            } else if (current instanceof ProfessionalInfo) {
                ((ProfessionalInfo) current).setEditEnabled(isEditing);
            }

            if (!isEditing) {
                saveProfileChanges();
            }
        });

        if (savedInstanceState == null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_up, 0)
                    .replace(R.id.profileOptionsContainer, new ChildProfile())
                    .commit();
        }
    }

    private void saveProfileChanges() {
        PreEmpFetchResponse currentData = viewModel.getPreEmpData().getValue();
        if (currentData == null) return;

        UpdateProfileDTO dto = new UpdateProfileDTO();
        UserInfo userInfo = currentData.getUserInfo();
        
        if (userInfo != null) {
            dto.setFirstName(userInfo.getFirstName());
            dto.setLastName(userInfo.getLastName());
            dto.setMiddleName(userInfo.getMiddleName());
            dto.setDob(userInfo.getDob());
            try {
                dto.setAge(Integer.parseInt(userInfo.getAge()));
            } catch (NumberFormatException e) {
                dto.setAge(0);
            }
            dto.setGender(userInfo.getGender());
            dto.setReligion(userInfo.getReligion());
            dto.setCivilStatus(userInfo.getCivilStatus());
            dto.setNationality(userInfo.getNationality());
            dto.setCellNo(userInfo.getCellNo());
            dto.setTelNo(userInfo.getTelNo());
            dto.setEmail(userInfo.getEmail());
            dto.setCurrentAddress(userInfo.getCurrentAddress());
            dto.setPermanentAddress(userInfo.getPermanentAddress());
        }

        dto.setEducation(currentData.getEducation());
        dto.setWorkExperience(currentData.getWorkExperience());
        dto.setCertificate(currentData.getCertificate());
        dto.setProfessionalSkills(currentData.getProfessionalSkills());

        viewModel.updateProfile(requireContext(), dto);
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
