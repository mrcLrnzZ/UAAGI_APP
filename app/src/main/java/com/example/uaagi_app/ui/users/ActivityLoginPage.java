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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.KeyEvent;
import android.text.TextWatcher;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.uaagi_app.network.RetrofitClient;
import com.example.uaagi_app.network.api.LoginApi;
import com.example.uaagi_app.network.dto.ApiResponse;
import com.example.uaagi_app.network.dto.GoogleAuthRequest;
import com.example.uaagi_app.network.dto.LoginData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.uaagi_app.network.Services.LoginOtpService;
import com.example.uaagi_app.network.Services.LoginAuthService;
import com.example.uaagi_app.R;
import com.example.uaagi_app.network.dto.LoginFetchResponse;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.utils.InputValidator;
import com.example.uaagi_app.ui.utils.UiHelpers;

import com.example.uaagi_app.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import androidx.activity.OnBackPressedCallback;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

public class ActivityLoginPage extends AppCompatActivity {

    private static final String TAG = "LoginLifecycle";

    // Views
    private CardView loginCard;
    private ProgressBar progressLogin, progressGoogleLogin;
    private LinearLayout emailSection;
    private LinearLayout otpSection;
    private MaterialButton loginBtn, backBtn, btnVerifyOTP;
    private EditText Email;
    private EditText[] otpInputs;
    private TextView otpErrorText;
    private TextView resendOtpText;
    private boolean isDebug = true;
    private static final int RC_SIGN_IN = 100;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private SignInButton btnGoogleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_login_page);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.server_client_id)) // Web Client ID
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn);
        btnGoogleSignIn.setOnClickListener(v -> {
            showLoginLoading();

            googleSignInClient.signOut().addOnCompleteListener(task -> {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            });
        });


        initViews();
        setupStatusBar();
        setupAnimations();
        setupClickListeners();
        setupBackHandler();
        setupResendOtp();
        setupBiometrics();

        // Use post to ensure the activity is fully initialized before showing the biometric prompt
        findViewById(android.R.id.content).post(this::checkExistingSessionForBiometric);
    }

    private void setupBiometrics() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(ActivityLoginPage.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.d(TAG, "Biometric error (" + errorCode + "): " + errString);
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                SessionManager.getInstance(ActivityLoginPage.this).saveLoginState(true);
                //UiHelpers.showToast("Authentication succeeded!", ActivityLoginPage.this);
                navigateToHome();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.d(TAG, "Biometric authentication failed");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Confirm using your fingerprint")
                .setNegativeButtonText("Use Gmail OTP")
                .setConfirmationRequired(false) // Show immediately
                .build();
    }

    private void checkExistingSessionForBiometric() {
        SessionManager sessionManager = SessionManager.getInstance(this);
        Log.d(TAG, "Checking biometric availability. Email present: " + !sessionManager.getUserEmail().isEmpty());
        
        // We offer biometric if the user has a saved email (even if logged out)
        if (!sessionManager.getUserEmail().isEmpty()) {
            BiometricManager biometricManager = BiometricManager.from(this);
            int canAuthenticate = biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
            
            if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
                Log.d(TAG, "Biometric available, showing prompt");
                biometricPrompt.authenticate(promptInfo);
            } else {
                Log.d(TAG, "Biometric not available, error code: " + canAuthenticate);
            }
        }
    }

    private void navigateToHome() {
        SessionManager sessionManager = SessionManager.getInstance(this);
        Intent intent;
        if (sessionManager.hasPreEmpResponse()) {
            intent = new Intent(ActivityLoginPage.this, ActivityHomePage.class);
        } else {
            intent = new Intent(ActivityLoginPage.this, PreEmpForm.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void showLoginLoading() {
        loginBtn.setEnabled(false);
        btnGoogleSignIn.setEnabled(false);
        loginBtn.setVisibility(View.GONE);
        btnGoogleSignIn.setVisibility(View.GONE);
        progressLogin.setVisibility(View.VISIBLE);
        progressGoogleLogin.setVisibility(View.VISIBLE);
    }

    private void hideLoginLoading() {
        loginBtn.setEnabled(true);
        btnGoogleSignIn.setEnabled(true);
        loginBtn.setVisibility(View.VISIBLE);
        btnGoogleSignIn.setVisibility(View.VISIBLE);
        progressLogin.setVisibility(View.GONE);
        progressGoogleLogin.setVisibility(View.GONE);
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String name = account.getDisplayName();
            String email = account.getEmail();
            String idToken = account.getIdToken();

            LoginApi loginApi = RetrofitClient.getInstance().create(LoginApi.class);

            GoogleAuthRequest request = new GoogleAuthRequest(idToken);

            Call<ApiResponse<LoginData>> call = loginApi.authGoogle(request);

            call.enqueue(new Callback<ApiResponse<LoginData>>() {

                @Override
                public void onResponse(Call<ApiResponse<LoginData>> call, Response<ApiResponse<LoginData>> response) {
                    if(response.isSuccessful() && response.body() != null){
                        ApiResponse<LoginData> apiResponse = response.body();
                        if(apiResponse.isSuccess()) {
                            LoginData data = apiResponse.getData();
                            SessionManager sessionManager = SessionManager.getInstance(ActivityLoginPage.this);
                            sessionManager.saveLoginState(true);
                            sessionManager.saveUserId(data.getUserId());
                            sessionManager.saveUserEmail(email);
                            sessionManager.savePreEmpResponse(data.isFormExist());
                            hideLoginLoading();
                            navigateToHome();
                        } else {
                            hideLoginLoading();
                            Log.e("GOOGLE_AUTH", "Login failed: " + apiResponse.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<LoginData>> call, Throwable t) {
                    hideLoginLoading();
                    Log.e("GOOGLE_AUTH", "Network error", t);
                }
            });

        } catch (ApiException e) {
            hideLoginLoading();
            Log.e("LOGIN_ERROR", "Code: " + e.getStatusCode());
            Log.e("GOOGLE_SIGN_IN", "Sign in failed", e);
        }
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
        progressLogin = findViewById(R.id.progressLogin);
        progressGoogleLogin = findViewById(R.id.progressGoogleLogin);

        Email = findViewById(R.id.btnemail);
        EditText otpInput1 = findViewById(R.id.otpInput1);
        EditText otpInput2 = findViewById(R.id.otpInput2);
        EditText otpInput3 = findViewById(R.id.otpInput3);
        EditText otpInput4 = findViewById(R.id.otpInput4);
        EditText otpInput5 = findViewById(R.id.otpInput5);
        EditText otpInput6 = findViewById(R.id.otpInput6);

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

        // Long click to manually trigger biometric prompt if it was dismissed
        loginBtn.setOnLongClickListener(v -> {
            checkExistingSessionForBiometric();
            return true;
        });
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
    private void handleResendOtp() {
        String email = Email.getText().toString().trim();

        if (email.isEmpty()) {
            UiHelpers.showToast("Email not found. Please go back and enter your email.", ActivityLoginPage.this);
            return;
        }

        // Clear current OTP inputs
        Helpers.clearOtp(otpInputs);
        Helpers.hideOtpError(otpErrorText);
        Helpers.resetOtpFieldsColor(otpInputs);

        // Request new OTP
        UiHelpers.showToast("Resending OTP...", ActivityLoginPage.this);
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
                            Helpers.hideKeyboard(ActivityLoginPage.this, getCurrentFocus());
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
        LoginAuthService authService = new LoginAuthService(this);
        authService.verifyLogin(email, otp, new LoginAuthService.VerifyLoginCallback() {
            @Override
            public void onResponse(LoginFetchResponse response) {
                SessionManager sessionManager = SessionManager.getInstance(ActivityLoginPage.this);
                sessionManager.saveLoginState(true);
                sessionManager.saveUserId(response.userId);
                sessionManager.saveUserEmail(email);
                sessionManager.savePreEmpResponse(response.formExist);
                Log.d(TAG, "Success: " + response.success + " UserId: " + response.userId);
                UiHelpers.showToast("Login successful", ActivityLoginPage.this);
                
                navigateToHome();
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Error: " + errorMessage);
                if (!errorMessage.contains("Invalid OTP")) {
                    Helpers.showOtpError(otpErrorText, "Failed to verify OTP");
                } else {
                    Helpers.showOtpError(otpErrorText, "Incorrect OTP");
                }
            }
        });
    }
    private void requestOtpFromServer(String email) {
        try {
            LoginOtpService service = new LoginOtpService(this);
            service.requestOtp(email, new LoginOtpService.LoginCallback() {
                @Override
                public void onResponse(String message) {
                    hideLoginLoading();
                    showOtpSection();
                }

                @Override
                public void onError(String errorMessage) {
                    hideLoginLoading();
                    UiHelpers.showToast("Network error", ActivityLoginPage.this);
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Error requesting OTP", e);
            UiHelpers.showToast("Network error occurred", ActivityLoginPage.this);
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
                    Helpers.showKeyboard(ActivityLoginPage.this, otpInputs[0]);
                })
                .start();

        loginCard.animate()
                .translationY(-1000f)
                .setDuration(400)
                .setInterpolator(new DecelerateInterpolator())
                .start();
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
    private void handleLoginButtonClick() {
        String emailInput = Email.getText().toString().trim();
        if (!InputValidator.validateEmailInput(findViewById(R.id.emailInputLayout), emailInput)) return;

        showLoginLoading();
        requestOtpFromServer(emailInput);
    }
    @Override protected void onStart() { super.onStart(); Log.d(TAG, "onStart"); }
    @Override protected void onResume() { super.onResume(); Log.d(TAG, "onResume"); }
    @Override protected void onPause() { super.onPause(); Log.d(TAG, "onPause"); }
    @Override protected void onStop() { super.onStop(); Log.d(TAG, "onStop"); }
    @Override protected void onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy"); }
}
