package com.example.uaagi_app.ui.users;

import android.content.Intent;
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
import android.widget.TextView;
import android.view.KeyEvent;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.uaagi_app.network.dto.LoginRequest;
import com.example.uaagi_app.network.api.LoginAuth;
import com.example.uaagi_app.R;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.utils.InputValidator;

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
    private EditText[] otpInputs;
    private TextView otpErrorText;
    private TextView resendOtpText;

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
        setupResendOtp();
    }

    private void initViews() {
        loginCard = findViewById(R.id.loginCard);
        emailSection = findViewById(R.id.emailSection);
        otpSection = findViewById(R.id.otpSection);
        loginBtn = findViewById(R.id.btnlogin);
        backBtn = findViewById(R.id.btnBackToLogin);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        otpErrorText = findViewById(R.id.otpErrorText);
        resendOtpText = findViewById(R.id.titemahabamasarapREAL);

        Email = findViewById(R.id.btnemail);
        otpInput1 = findViewById(R.id.otpInput1);
        otpInput2 = findViewById(R.id.otpInput2);
        otpInput3 = findViewById(R.id.otpInput3);
        otpInput4 = findViewById(R.id.otpInput4);
        otpInput5 = findViewById(R.id.otpInput5);
        otpInput6 = findViewById(R.id.otpInput6);

        otpInputs = new EditText[]{otpInput1, otpInput2, otpInput3, otpInput4, otpInput5, otpInput6};

        setupOtpInputs();
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

    private void setupResendOtp() {
        String fullText = "Didn't receive OTP? Resend OTP";
        SpannableString spannableString = new SpannableString(fullText);

        int startIndex = fullText.indexOf("Resend OTP");
        int endIndex = startIndex + "Resend OTP".length();

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                handleResendOtp();
            }

            @Override
            public void updateDrawState(android.text.TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        // Apply clickable span to "Resend OTP"
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Make "Resend OTP" blue color
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#002F62"));
        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the spannable text
        resendOtpText.setText(spannableString);
        resendOtpText.setMovementMethod(LinkMovementMethod.getInstance());
        resendOtpText.setHighlightColor(Color.TRANSPARENT); // Remove highlight on click
    }

    // Handle resend OTP
    private void handleResendOtp() {
        String email = Email.getText().toString().trim();

        if (email.isEmpty()) {
            showToast("Email not found. Please go back and enter your email.");
            return;
        }

        // Clear current OTP inputs
        Helpers.clearOtp(otpInputs);
        Helpers.hideOtpError(otpErrorText);
        Helpers.resetOtpFieldsColor(otpInputs);

        // Request new OTP
        showToast("Resending OTP...");
        requestOtpFromServer(email);
    }

    private void setupOtpInputs() {
        for (int i = 0; i < otpInputs.length; i++) {
            final int index = i;

            otpInputs[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Hide error when user starts typing
                    if (s.length() > 0) {
                        Helpers.hideOtpError(otpErrorText);
                    }

                    if (s.length() == 1) {
                        // Move to next field
                        if (index < otpInputs.length - 1) {
                            otpInputs[index + 1].requestFocus();
                        } else {
                            // Last field - hide keyboard
                            otpInputs[index].clearFocus();
                            Helpers.hideKeyboard(Login.this, getCurrentFocus());
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            // Handle backspace to go to previous field
            otpInputs[i].setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_DEL &&
                        event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (otpInputs[index].getText().toString().isEmpty() && index > 0) {
                        otpInputs[index - 1].requestFocus();
                        otpInputs[index - 1].setText("");
                    }
                }
                return false;
            });
        }
    }

    private void verifyOtp() {
        StringBuilder otpBuilder = new StringBuilder();
        String email = Email.getText().toString().trim();

        for (EditText input : otpInputs) {
            if (input != null) {
                otpBuilder.append(input.getText().toString().trim());
            }
        }
        String otp = otpBuilder.toString();

        // Validate OTP length
        if (otp.length() != 6) {
            Helpers.showOtpError(otpErrorText, "Please enter complete OTP");
            return;
        }

        verifyLoginFromServer(otp, email);
    }

    private void verifyLoginFromServer(String otp, String email) {
        try {
            LoginAuth loginauth = new LoginAuth(this);

            Log.d(TAG, "verifyLoginFromServer() called");
            Log.d(TAG, "Sending OTP: " + otp + ", Email: " + email);

            loginauth.verifyLogin(otp, email, new LoginAuth.verifyLoginCallback() {
                @Override
                public void onResponse(boolean success, JSONObject response) {
                    Log.d(TAG, "Server response received");
                    Log.d(TAG, "Success: " + success);
                    Log.d(TAG, "Response JSON: " + response.toString());

                    try {
                        String message = response.getString("message");

                        if (success) {
                            Log.d(TAG, "OTP verification successful");
                            Helpers.hideOtpError(otpErrorText);
                            Helpers.resetOtpFieldsColor(otpInputs);
                            showToast(message);
                            // Navigate to next screen or perform success action
                             Intent intent = new Intent(Login.this, PreEmpActivity.class);
                             startActivity(intent);
                             finish();
                        } else {
                            Log.d(TAG, "OTP verification failed");
                            Helpers.showOtpError(otpErrorText, message);
                            Helpers.showOtpFieldError(Login.this, otpInputs);


                            // Clear OTP after delay
                            new android.os.Handler().postDelayed(() -> {
                                Helpers.clearOtp(otpInputs);
                                Helpers.resetOtpFieldsColor(otpInputs);
                            }, 1500);
                        }

                    } catch (JSONException e) {
                        Log.e(TAG, "JSON parsing error", e);
                        Helpers.showOtpError(otpErrorText, "Invalid server response");
                        Helpers.showOtpFieldError(Login.this, otpInputs);
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e(TAG, "verifyLogin error: " + errorMessage);
                    Helpers.showOtpError(otpErrorText, errorMessage);
                    Helpers.showOtpFieldError(Login.this, otpInputs);

                    // Clear OTP after delay
                    new android.os.Handler().postDelayed(() -> {
                        Helpers.clearOtp(otpInputs);
                        Helpers.resetOtpFieldsColor(otpInputs);
                    }, 1500);
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Error verifying user", e);
            Helpers.showOtpError(otpErrorText, "Network error occurred");
            Helpers.showOtpFieldError(Login.this, otpInputs);
        }
    }

    private void handleLoginButtonClick() {
        String emailInput = Email.getText().toString().trim();
        if (!InputValidator.validateEmailInput(
                findViewById(R.id.emailInputLayout),
                emailInput
        )) return;
        requestOtpFromServer(emailInput);
    }

    private void requestOtpFromServer(String email) {
        try {
            Log.d(TAG, "requestOtpFromServer() called");
            Log.d(TAG, "Requesting OTP for email: " + email);

            LoginRequest loginRequest = new LoginRequest(this);
            loginRequest.requestLoginOtp(email, new LoginRequest.LoginCallback() {

                @Override
                public void onResponse(boolean success, JSONObject response) {
                    Log.d(TAG, "OTP response received");
                    Log.d(TAG, "Success: " + success);
                    Log.d(TAG, "Raw response: " + response.toString());

                    try {
                        String message = response.getString("message");
                        Log.d(TAG, "Server message: " + message);

                        showToast(message);

                        if (success) {
                            Log.d(TAG, "OTP request successful, showing OTP section");
                            showOtpSection();
                        } else {
                            Log.d(TAG, "OTP request failed");
                        }

                    } catch (JSONException e) {
                        Log.e(TAG, "JSON parsing error", e);
                        showToast("Invalid server response");
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e(TAG, "OTP request error: " + errorMessage);
                    showToast(errorMessage);
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Error requesting OTP", e);
            showToast("Network error occurred");
        }
    }

    // ---------------------- backend pang UI ------------------------------

    private void showOtpSection() {
        emailSection.setVisibility(View.GONE);
        otpSection.setVisibility(View.VISIBLE);

        otpSection.setAlpha(0f);
        otpSection.animate()
                .alpha(1f)
                .setDuration(400)
                .withEndAction(() -> {
                    // Focus first OTP input after animation
                    otpInputs[0].requestFocus();
                    Helpers.showKeyboard(Login.this, otpInputs[0]);
                })
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

        // Clear OTP fields and error when going back
        Helpers.clearOtp(otpInputs);
        Helpers.resetOtpFieldsColor(otpInputs);
        Helpers.hideOtpError(otpErrorText);
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