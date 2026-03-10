package com.example.uaagi_app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.Services.JobService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

public class Helpers {

    public static void showOtpError(TextView otpErrorText, String message) {
        if (otpErrorText != null) {
            otpErrorText.setText(message);
            otpErrorText.setVisibility(View.VISIBLE);

            otpErrorText.setAlpha(0f);
            otpErrorText.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .start();
        }
    }

    public static void hideOtpError(TextView otpErrorText) {
        if (otpErrorText != null && otpErrorText.getVisibility() == View.VISIBLE) {
            otpErrorText.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .withEndAction(() -> otpErrorText.setVisibility(View.GONE))
                    .start();
        }
    }

    public static void showOtpFieldError(Context context, EditText[] otpInputs) {
        for (EditText input : otpInputs) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                input.setBackgroundTintList(
                        android.content.res.ColorStateList.valueOf(Color.parseColor("#F44336"))
                );
            }
        }
        shakeOtpFields(context, otpInputs);
    }

    public static void resetOtpFieldsColor(EditText[] otpInputs) {
        for (EditText input : otpInputs) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                input.setBackgroundTintList(
                        android.content.res.ColorStateList.valueOf(Color.BLACK)
                );
            }
        }
    }

    private static void shakeOtpFields(Context context, EditText[] otpInputs) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        for (EditText input : otpInputs) {
            input.startAnimation(shake);
        }
    }

    public static void clearOtp(EditText[] otpInputs) {
        for (EditText input : otpInputs) {
            input.setText("");
        }
        otpInputs[0].requestFocus();
    }

    public static void hideKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Context context, View view) {
        view.postDelayed(() -> {
            InputMethodManager imm =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }, 200);
    }
    public static String calculateAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        dob.set(year, month, day);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH) &&
                        today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }
        return String.valueOf(age);
    }
    public static void generateCurrentAddress(TextInputEditText streetInput, AutoCompleteTextView barangaySpinner, AutoCompleteTextView citySpinner, AutoCompleteTextView regionSpinner, TextInputEditText currentAddressInput) {
        String street = Objects.requireNonNull(streetInput.getText()).toString().trim();
        String barangay = Objects.requireNonNull(barangaySpinner.getText()).toString().trim();
        String city = Objects.requireNonNull(citySpinner.getText()).toString().trim();
        String region = Objects.requireNonNull(regionSpinner.getText()).toString().trim();

        if (!street.isEmpty() && !barangay.isEmpty() && !city.isEmpty() && !region.isEmpty()) {
            String fullAddress = street + ", " + barangay + ", " + city + ", " + region;
            currentAddressInput.setText(fullAddress);
        }
    }
    public static String getTimeAgo(long time) {
        long now = System.currentTimeMillis();
        long diff = now - time;

        final long SECOND_MILLIS = 1000;
        final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
        final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
        final long DAY_MILLIS = 24 * HOUR_MILLIS;

        if (diff < MINUTE_MILLIS) {
            long seconds = diff / SECOND_MILLIS;
            return seconds <= 1 ? "1 second ago" : seconds + " seconds ago";
        } else if (diff < HOUR_MILLIS) {
            long minutes = diff / MINUTE_MILLIS;
            return minutes == 1 ? "1 minute ago" : minutes + " minutes ago";
        } else if (diff < DAY_MILLIS) {
            long hours = diff / HOUR_MILLIS;
            return hours == 1 ? "1 hour ago" : hours + " hours ago";
        } else {
            long days = diff / DAY_MILLIS;
            return days == 1 ? "1 day ago" : days + " days ago";
        }
    }
    public static String safeText(String value) {
        return TextUtils.isEmpty(value) ? "—" : value;
    }
    public static void actionSaveJob(Context context, int jobId, JobService.FeedbackCallback callback) {
        JobService service = new JobService(context);
        service.saveJob(jobId, callback);
    }
    public static void actionUnsaveJob(Context context, int jobId, JobService.FeedbackCallback callback) {
        JobService service = new JobService(context);
        service.unsaveJob(jobId, callback);
    }
    public static void actionArchiveJob(Context context, int jobId, JobService.FeedbackCallback callback) {
        JobService service = new JobService(context);
        service.archiveJob(jobId, callback);
    }
    public static void actionUnarchiveJob(Context context, int jobId, JobService.FeedbackCallback callback) {
        JobService service = new JobService(context);
        service.unarchiveJob(jobId, callback);
    }
    public static void actionFetchSavedJobId(Context context, JobService.JobIdServiceCallback callback) {
        JobService service = new JobService(context);
        service.fetchSavedJobId(SessionManager.getInstance(context).getUserId(), callback);
    }
    public static void actionFetchArchivedJobId(Context context, JobService.JobIdServiceCallback callback) {
        JobService service = new JobService(context);
        service.fetchArchivedJobId(SessionManager.getInstance(context).getUserId(), callback);
    }
}
