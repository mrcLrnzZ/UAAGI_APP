package com.example.uaagi_app.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.uaagi_app.network.Services.ApplicationService;
import com.example.uaagi_app.utils.SessionManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PdfPickerHelper {

    public interface PdfUploadCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    private final Fragment fragment;
    private final ActivityResultLauncher<Intent> pdfPickerLauncher;
    private PdfUploadCallback callback;
    private int jobId;

    public PdfPickerHelper(Fragment fragment) {
        this.fragment = fragment;

        this.pdfPickerLauncher = fragment.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri pdfUri = result.getData().getData();
                        if (pdfUri != null) {
                            uploadPdfFile(pdfUri);
                        }
                    }
                }
        );
    }

    public void openPdfPicker(int jobId, PdfUploadCallback callback) {
        this.jobId = jobId;
        this.callback = callback;

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        pdfPickerLauncher.launch(Intent.createChooser(intent, "Select PDF"));
    }

    private void uploadPdfFile(Uri pdfUri) {
        try {
            Context context = fragment.requireContext();

            File tempFile = new File(context.getCacheDir(), "upload_resume.pdf");

            try (InputStream inputStream =
                         context.getContentResolver().openInputStream(pdfUri);
                 FileOutputStream outputStream =
                         new FileOutputStream(tempFile)) {

                byte[] buffer = new byte[4096];
                int read;

                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
            }

            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("application/pdf"),
                            tempFile
                    );

            MultipartBody.Part filePart =
                    MultipartBody.Part.createFormData(
                            "file",
                            tempFile.getName(),
                            requestFile
                    );

            ApplicationService service =
                    new ApplicationService(context);

            service.submitResumeApplication(
                    SessionManager.getInstance(context).getUserId(),
                    jobId,
                    "resume",
                    filePart,
                    new ApplicationService.SubmitApplicationCallback() {
                        @Override
                        public void onResponse() {
                            Toast.makeText(context,
                                    "PDF uploaded successfully!",
                                    Toast.LENGTH_SHORT).show();
                            if (callback != null) callback.onSuccess();
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(context,
                                    "Error: " + errorMessage,
                                    Toast.LENGTH_SHORT).show();
                            if (callback != null) callback.onError(errorMessage);
                        }
                    }
            );

        } catch (Exception e) {
            String errorMsg = "Error preparing file: " + e.getMessage();
            Log.e("PdfPickerHelper", errorMsg, e);
            Toast.makeText(fragment.requireContext(),
                    errorMsg,
                    Toast.LENGTH_SHORT).show();
            if (callback != null) callback.onError(errorMsg);
        }
    }

}