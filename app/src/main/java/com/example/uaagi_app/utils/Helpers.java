package com.example.uaagi_app.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.google.android.material.textfield.TextInputLayout;

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
}
