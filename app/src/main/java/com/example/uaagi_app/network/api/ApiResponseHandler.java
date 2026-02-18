package com.example.uaagi_app.network.api;

import android.util.Log;

import com.example.uaagi_app.utils.Helpers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiResponseHandler {
    private static final String TAG = "ApiResponseHandler";
    public interface ApiResponseCallback<T> {
        void onResponse(List<T> response);
    }

    public interface ApiSingleResponseCallback<T> {
        void onResponse(T response);
    }
    public interface JsonMapper<T> {
        T fromJson(JSONObject json);
    }

    public static <T> void handleSuccess(
            JSONObject response,
            JsonMapper<T> mapper,
            ApiResponseCallback<T> callback,
            ApiErrorHandler.ApiErrorCallback errorCallback
    ) {
        if (response == null) {
            errorCallback.onError("No response received");
            return;
        }

        Log.d(TAG, "Response: " + response);

        Object dataObj = response.opt("data");
        if (dataObj == null) {
            errorCallback.onError("No data received");
            return;
        }

        List<T> result = new ArrayList<>();

        if (dataObj instanceof JSONArray) {
            JSONArray data = (JSONArray) dataObj;
            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.optJSONObject(i);
                if (item != null) {
                    result.add(mapper.fromJson(item));
                }
            }
        }
        else if (dataObj instanceof JSONObject) {
            result.add(mapper.fromJson((JSONObject) dataObj));
        }
        else {
            errorCallback.onError("Invalid data format");
            return;
        }

        callback.onResponse(result);
    }
    public static <T> void handleSingleSuccess(
            JSONObject response,
            JsonMapper<T> mapper,
            ApiSingleResponseCallback<T> callback,
            ApiErrorHandler.ApiErrorCallback errorCallback
    ) {
        Log.d(TAG, "FULL response: " + response.toString());
        Object dataObj = response.opt("data");
        Log.d(TAG, "Response data: " + dataObj);
        if  (dataObj instanceof JSONObject) {
            callback.onResponse(mapper.fromJson((JSONObject) dataObj));

            return;
        }
        errorCallback.onError("Invalid data format")    ;
    }

}
