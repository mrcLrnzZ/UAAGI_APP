package com.example.uaagi_app.network.Services;

import static com.example.uaagi_app.network.RetrofitClient.BASE_URL;
import static com.example.uaagi_app.network.RetrofitClient.IMAGE_BASE_URL;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.uaagi_app.network.RetrofitClient;
import com.example.uaagi_app.network.api.ProfileImgApi;
import com.example.uaagi_app.utils.SessionManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImageService {

    private static final String TAG = "UploadImageService";
    private static UploadImageService instance;
    private final ProfileImgApi api = RetrofitClient.getInstance().create(ProfileImgApi.class);

    private UploadImageService() {}

    public static UploadImageService getInstance() {
        if (instance == null) {
            instance = new UploadImageService();
            Log.d(TAG, "UploadImageService instance created");
        }
        return instance;
    }

    public void uploadImage(Uri uri, Context context, ImageView profilePhoto) {
        try {
            Log.d(TAG, "Starting image upload for URI: " + uri);
            File file = uriToFile(uri, context);
            Log.d(TAG, "File created: " + file.getAbsolutePath());

            RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/*"));
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("profile_image", file.getName(), requestFile);

            RequestBody userId = RequestBody.create(
                    String.valueOf(SessionManager.getInstance(context).getUserId()),
                    MediaType.parse("text/plain")
            );

            Log.d(TAG, "Uploading image for userId: " + SessionManager.getInstance(context).getUserId());

            api.uploadProfileImage(body, userId).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Image upload successful! Response code: " + response.code());
                        refreshProfileImage(context, profilePhoto);
                    } else {
                        Log.e(TAG, "Image upload failed. Response code: " + response.code() +
                                ", response: " + response.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, "Image upload failed due to network/error", t);
                }
            });

        } catch (IOException e) {
            Log.e(TAG, "Failed to convert URI to file", e);
        }
    }

    private File uriToFile(Uri uri, Context context) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        if (inputStream == null) throw new IOException("Failed to open input stream");
        File file = new File(context.getCacheDir(), "upload.jpg");

        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[4096];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.close();
        inputStream.close();
        Log.d(TAG, "URI converted to file: " + file.getAbsolutePath());
        return file;
    }

    private void refreshProfileImage(Context context, ImageView profilePhoto) {
        String imageUrl = IMAGE_BASE_URL + "user_"
                + SessionManager.getInstance(context).getUserId()
                + ".jpg?timestamp=" + System.currentTimeMillis();

        Glide.with(context)
                .load(imageUrl)
                .circleCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(profilePhoto);
    }
}