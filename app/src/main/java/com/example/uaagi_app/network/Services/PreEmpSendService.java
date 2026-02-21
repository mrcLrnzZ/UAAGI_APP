package com.example.uaagi_app.network.Services;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.uaagi_app.data.model.PreEmploymentForm.PreEmpFormDataModel;
import com.example.uaagi_app.network.VolleySingleton;
import com.example.uaagi_app.utils.NetworkUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class PreEmpSendService {
    private static final String TAG = "PreEmpSendService";
    private static final String BASE_URL = "https://uaagionehire.bscs3b.com/MobileAPI/api/index.php";
    private static final String SEND_PREEMP_URL = BASE_URL + "/preemployment/submit";
    private static final int TIMEOUT_MS = 15000;

    private final Context context;
    private final Gson gson;

    public PreEmpSendService(Context context) {
        this.context = context;
        this.gson = new Gson();
    }

    public void sendPreEmploymentForm(PreEmpFormDataModel formData, SendPreEmploymentCallback callback) {
        if (!NetworkUtils.isInternetAvailable(context)) {
            callback.onError("No internet connection. Please check your network.");
            return;
        }

        if (formData == null) {
            callback.onError("Form data cannot be null");
            return;
        }

        try {
            String jsonString = gson.toJson(formData);
            JSONObject body = new JSONObject(jsonString);

            Log.d(TAG, "Request URL: " + SEND_PREEMP_URL);
            Log.d(TAG, "Request body: " + body.toString());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    SEND_PREEMP_URL,
                    body,
                    response -> ApiResponseHandler.handleSendOnlySuccess(
                            response,
                            callback::onSuccess,
                            callback::onError
                    ),
                    error -> ApiErrorHandler.handleError(error, callback::onError)
            );

            request.setRetryPolicy(new DefaultRetryPolicy(
                    TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));

            VolleySingleton.getInstance(context).addToRequestQueue(request);

        } catch (JSONException e) {
            callback.onError("Failed to create request body: " + e.getMessage());
            Log.e(TAG, "JSON conversion error", e);
        } catch (Exception e) {
            callback.onError("Unexpected error: " + e.getMessage());
            Log.e(TAG, "Unexpected error", e);
        }
    }

    public interface SendPreEmploymentCallback extends ApiErrorHandler.ApiErrorCallback {
        void onSuccess(String message);
    }
}
