package com.example.uaagi_app.ui.users;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.uaagi_app.network.dto.LoginRequest;
import com.example.uaagi_app.utils.InputValidator;

import com.example.uaagi_app.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import androidx.activity.OnBackPressedCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private static final String TAG = "LoginLifecycle";

    // Views
    private CardView loginCard;
    private LinearLayout emailSection;
    private LinearLayout otpSection;
    private MaterialButton loginBtn, backBtn, btnVerifyOTP;
    private EditText Email, otpInput1, otpInput2, otpInput3, otpInput4, otpInput5, otpInput6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.login);

        initViews();
        setupStatusBar();
        setupAnimations();
        setupClickListeners();
        setupBackHandler();
    }

    private void initViews() {
        loginCard = findViewById(R.id.loginCard);
        emailSection = findViewById(R.id.emailSection);
        otpSection = findViewById(R.id.otpSection);
        loginBtn = findViewById(R.id.btnlogin);
        backBtn = findViewById(R.id.btnBackToLogin);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP); // Fixed: Was missing initialization

        Email = findViewById(R.id.btnemail);
        otpInput1 = findViewById(R.id.otpInput1);
        otpInput2 = findViewById(R.id.otpInput2);
        otpInput3 = findViewById(R.id.otpInput3);
        otpInput4 = findViewById(R.id.otpInput4);
        otpInput5 = findViewById(R.id.otpInput5);
        otpInput6 = findViewById(R.id.otpInput6);
    }

    private void setupStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#002F62"));
        }
    }


    private void setupAnimations() {
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        loginCard.startAnimation(slideUp);
    }

    private void setupClickListeners() {
        loginBtn.setOnClickListener(v -> handleLoginButtonClick());

        if (backBtn != null) {
            backBtn.setOnClickListener(v -> showEmailSection());
        }
        if (btnVerifyOTP != null) {
            btnVerifyOTP.setOnClickListener(v -> verifyOtp());
        }
    }

    private void verifyOtp() {
        EditText[] otpInputs = {otpInput1, otpInput2, otpInput3, otpInput4, otpInput5, otpInput6};
        StringBuilder otpBuilder = new StringBuilder();

        for (EditText input : otpInputs) {
            if (input != null) {
                otpBuilder.append(input.getText().toString().trim());
            }
        }

        String otp = otpBuilder.toString();
        // Handle OTP verification logic here
    }

    private void handleLoginButtonClick() {
        String emailInput = Email.getText().toString().trim();



        // For now, let's just show the OTP section to test UI transition
        // until your backend integration is fully ready.


        if (!validateEmailInput(emailInput)) return;

        showOtpSection();
        // requestOtpFromServer(emailInput);
    }

    private boolean validateEmailInput(String email) {
        TextInputLayout emailInputLayout = findViewById(R.id.emailInputLayout);

        if (email.isEmpty()) {
            emailInputLayout.setError("Please enter your email");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Please enter a valid email address");
            return false;
        }

        // Clear error if validation passes
        emailInputLayout.setError(null);
        emailInputLayout.setErrorEnabled(false);
        return true;
    }

    private void requestOtpFromServer(String email) {
        // This requires your LoginRequest and network classes to be implemented
        try {
            LoginRequest loginRequest = new LoginRequest(this);
            loginRequest.requestLoginOtp(email, new LoginRequest.LoginCallback() {
                @Override
                public void onResponse(boolean success, JSONObject response) {
                    try {
                        String message = response.getString("message");
                        showToast(message);
                        if (success) {
                            showOtpSection();
                        }
                    } catch (JSONException e) {
                        showToast("Invalid server response");
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    showToast(errorMessage);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error requesting OTP", e);
            showToast("Network error occurred");
        }
    }

    private void showOtpSection() {
        emailSection.setVisibility(View.GONE);
        otpSection.setVisibility(View.VISIBLE);

        otpSection.setAlpha(0f);
        otpSection.animate()
                .alpha(1f)
                .setDuration(400)
                .start();

        loginCard.animate()
                .translationY(-1000f)
                .setDuration(400)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showEmailSection() {
        Log.d(TAG, "Back button clicked");

        otpSection.setVisibility(View.GONE);
        emailSection.setVisibility(View.VISIBLE);

        emailSection.setAlpha(0f);
        emailSection.animate()
                .alpha(1f)
                .setDuration(400)
                .start();

        loginCard.animate()
                .translationY(0f)
                .setDuration(400)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void setupBackHandler() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (otpSection.getVisibility() == View.VISIBLE) {
                    showEmailSection();
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override protected void onStart() { super.onStart(); Log.d(TAG, "onStart"); }
    @Override protected void onResume() { super.onResume(); Log.d(TAG, "onResume"); }
    @Override protected void onPause() { super.onPause(); Log.d(TAG, "onPause"); }
    @Override protected void onStop() { super.onStop(); Log.d(TAG, "onStop"); }
    @Override protected void onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy"); }
}
