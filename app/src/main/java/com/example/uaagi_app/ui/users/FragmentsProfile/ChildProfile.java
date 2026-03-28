package com.example.uaagi_app.ui.users.FragmentsProfile;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.ProfileViewModel;
import com.example.uaagi_app.network.RetrofitClient;
import com.example.uaagi_app.network.api.DocumentApi;
import com.example.uaagi_app.ui.users.FragmentsHomePage.Profile;
import com.example.uaagi_app.utils.SessionManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildProfile extends Fragment {

    private RelativeLayout personalInfoOption;
    private RelativeLayout professionalOption;
    private TextView profileEmail;
    private TextView profileContact;
    private TextView profileAdd;
    private ProfileViewModel viewModel;
    private ProgressBar progressBar;
    private LinearLayout profileContainer, getDocumentBtn;

    public ChildProfile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        personalInfoOption = view.findViewById(R.id.PersonalInfo);
        professionalOption = view.findViewById(R.id.ProfessionalInfo);
        profileContainer = view.findViewById(R.id.profileContainer);
        progressBar = view.findViewById(R.id.profileProgressBar);
        profileEmail = view.findViewById(R.id.ProfileEmail);
        profileContact = view.findViewById(R.id.ProfileContact);
        profileAdd = view.findViewById(R.id.ProfileAdd);
        getDocumentBtn = view.findViewById(R.id.getDocumentBtn);

        viewModel = new ViewModelProvider(
                requireParentFragment() != null ? requireParentFragment() : this
        ).get(ProfileViewModel.class);

        personalInfoOption.setOnClickListener(v -> {
            if (getParentFragment() instanceof Profile) {
                ((Profile) getParentFragment()).updateHeaderButtons(true);
            }
            loadFragment(new PersonalInfo());
        });

        getDocumentBtn.setOnClickListener(v -> {
            preEmpPDF(SessionManager.getInstance(requireContext()).getUserId());
        });

        professionalOption.setOnClickListener(v -> {
            if (getParentFragment() instanceof Profile) {
                ((Profile) getParentFragment()).updateHeaderButtons(true);
            }
            loadFragment(new ProfessionalInfo());
        });

        return view;
    }

    private void preEmpPDF(int userId) {
        DocumentApi api = RetrofitClient.getInstance().create(DocumentApi.class);
        Call<ResponseBody> call = api.downloadPdf(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    savePdf(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void savePdf(ResponseBody body) {
        try {
            InputStream inputStream = body.byteStream();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, "preemployment.pdf");
                values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                Uri uri = requireContext().getContentResolver().insert(
                        MediaStore.Files.getContentUri("external"), values
                );

                if (uri == null) return;

                OutputStream outputStream = requireContext().getContentResolver().openOutputStream(uri);

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                openPdf(uri);

            } else {
                File file = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "preemployment.pdf"
                );

                FileOutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                Uri uri = FileProvider.getUriForFile(
                        requireContext(),
                        requireContext().getPackageName() + ".provider",
                        file
                );

                openPdf(uri);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openPdf(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
                profileContainer.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                profileContainer.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getPreEmpData().observe(getViewLifecycleOwner(), data -> {
            if (data != null && data.getUserInfo() != null) {
                profileEmail.setText(data.getUserInfo().getEmail());
                profileContact.setText(data.getUserInfo().getCellNo());
                profileAdd.setText(data.getUserInfo().getCurrentAddress());
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.profileOptionsContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}
