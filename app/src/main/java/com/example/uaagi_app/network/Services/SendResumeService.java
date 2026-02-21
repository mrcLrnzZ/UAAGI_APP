package com.example.uaagi_app.network.Services;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.uaagi_app.network.RetrofitClient;
import com.example.uaagi_app.network.dto.ApiResponse;
import com.example.uaagi_app.network.api.DocumentApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendResumeService {

    private final Context context;
    private final DocumentApi api;

    public SendResumeService(Context context) {
        this.context = context;
        this.api = RetrofitClient.getInstance().create(DocumentApi.class);
    }

    public void uploadResume(Uri fileUri, String userId, String jobId, UploadCallback callback) {

        try {
            File file = createTempFileFromUri(fileUri);

            RequestBody requestFile =
                    RequestBody.create(file, MediaType.parse("application/octet-stream"));

            MultipartBody.Part filePart =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            RequestBody userIdBody =
                    RequestBody.create(userId, MediaType.parse("text/plain"));

            RequestBody jobIdBody =
                    RequestBody.create(jobId, MediaType.parse("text/plain"));

            Call<ApiResponse> call = api.uploadDocument(filePart, userIdBody, jobIdBody);

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().isSuccess()) {
                            callback.onSuccess(response.body().getMessage());
                        } else {
                            callback.onError(response.body().getMessage());
                        }

                    } else {
                        callback.onError("Upload failed: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    callback.onError(t.getMessage());
                }
            });

        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

    @NonNull
    private File createTempFileFromUri(Uri uri) throws Exception {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                throw new Exception("Cannot open file");
            }

            String fileName = "upload_" + System.currentTimeMillis();
            File tempFile = new File(context.getCacheDir(), fileName);

            outputStream = new FileOutputStream(tempFile);

            byte[] buffer = new byte[4096];
            int read;
            long totalBytes = 0;
            long maxSize = 5 * 1024 * 1024;

            while ((read = inputStream.read(buffer)) != -1) {
                totalBytes += read;
                if (totalBytes > maxSize) {
                    tempFile.delete();
                    throw new Exception("File exceeds 5MB limit");
                }
                outputStream.write(buffer, 0, read);
            }

            return tempFile;

        } finally {
            if (inputStream != null) {
                try { inputStream.close(); } catch (Exception e) { }
            }
            if (outputStream != null) {
                try { outputStream.close(); } catch (Exception e) { }
            }
        }
    }

    public interface UploadCallback {
        void onSuccess(String message);
        void onError(String error);
    }
}