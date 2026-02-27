package com.example.uaagi_app.ui.users.ActivityPreEmpForm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.PreEmpFormViewModel;
import com.example.uaagi_app.network.Services.PreEmpSendService;
import com.example.uaagi_app.ui.users.ActivityHomePage;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.FormStepFragment;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.PreEmpFormStep1;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.PreEmpFormStep2;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.PreEmpFormStep3;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.PreEmpFormStep4;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.PreEmpFormStep5;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.PreEmpFormStep6;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.utils.SessionManager;

public class PreEmpForm extends AppCompatActivity {
    private static final String TAG = "MainPreEmpLifeCycle";
    private TextView[] steps, stepsTitle;
    private ScrollView scrollView;
    private int currentStep = 1;
    private final int TOTAL_STEPS = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preemp_main);
        setupUI();
        getFragmentForStep(currentStep);
        if (savedInstanceState != null) {
            currentStep = savedInstanceState.getInt("CURRENT_STEP", 1);
            highlightSteps();
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.step_container, new PreEmpFormStep1())
                    .commit();
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                if (currentStep > 1) {
                    previousStep();
                } else {
                    finish();
                }
            }
        });
    }
    private Fragment getFragmentForStep(int step) {
        switch (step) {
            case 1: return new PreEmpFormStep1();
            case 2: return new PreEmpFormStep2();
            case 3: return new PreEmpFormStep3();
            case 4: return new PreEmpFormStep4();
            case 5: return new PreEmpFormStep5();
            case 6: return new PreEmpFormStep6();
            default: return new PreEmpFormStep1();
        }
    }
    public void nextStep() {
        navigateToStep(currentStep + 1, true);
    }
    public void submitForm() {
        PreEmpFormViewModel viewModel =
                new ViewModelProvider(this).get(PreEmpFormViewModel.class);

        PreEmpSendService service = new PreEmpSendService(this);
        service.sendPreEmploymentForm(viewModel, new PreEmpSendService.SendPreEmploymentCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(PreEmpForm.this, "Form submitted successfully!", Toast.LENGTH_SHORT).show();
                SessionManager.getInstance(PreEmpForm.this).savePreEmpResponse(true);
                Intent intent = new Intent(PreEmpForm.this, ActivityHomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(PreEmpForm.this, "Error: " + error, Toast.LENGTH_LONG).show();
                Log.e("PreEmpForm", "Submission error: " + error);
            }
        });
    }

    public void previousStep() {
        navigateToStep(currentStep - 1, false);
    }
    private void setupUI(){
        scrollView = findViewById(R.id.preemp_scrollview);
        stepsTitle = new TextView[]{
                findViewById(R.id.step1title),
                findViewById(R.id.step2title),
                findViewById(R.id.step3title),
                findViewById(R.id.step4title),
                findViewById(R.id.step5title),
                findViewById(R.id.step6title)
        };
        steps = new TextView[]{
                findViewById(R.id.step1),
                findViewById(R.id.step2),
                findViewById(R.id.step3),
                findViewById(R.id.step4),
                findViewById(R.id.step5),
                findViewById(R.id.step6)
        };
    }
    private void highlightSteps(){
        unHighlightSteps();
        UiHelpers.showToast("Step " + currentStep, this);
        if (currentStep >= 1 && currentStep <= steps.length) {
            steps[currentStep - 1].setBackgroundResource(R.drawable.circle_white);
            steps[currentStep - 1].setTextColor(getResources().getColor(R.color.Deepwater));
            steps[currentStep - 1].setTextSize(18);
            stepsTitle[currentStep - 1].setTextColor(getResources().getColor(R.color.White));
        }
    }
    private void unHighlightSteps() {

        for (int i = 0; i < TOTAL_STEPS; i++) {

            if (i < currentStep) {
                steps[i].setBackgroundResource(R.drawable.circle_background_done);
                steps[i].setTextColor(getResources().getColor(R.color.White));

            } else {

                steps[i].setBackgroundResource(R.drawable.circle_outline);
                steps[i].setTextColor(getResources().getColor(R.color.White));
            }
        }

        for (TextView stepTitle : stepsTitle) {
            stepTitle.setTextColor(getResources().getColor(R.color.PaleBlue));
        }
    }
    private void navigateToStep(int step, boolean addToBackStack) {

        step = Math.max(1, Math.min(TOTAL_STEPS, step));

        // Save current fragment first
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.step_container);

        if (currentFragment instanceof FormStepFragment) {
            ((FormStepFragment) currentFragment).saveFormData();
        }

        currentStep = step;
        Log.d(TAG, "navigateToStep: "+currentStep);
        highlightSteps();
        scrollView.post(() -> scrollView.smoothScrollTo(0, 0));

        Fragment fragment = getFragmentForStep(step);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.step_container, fragment)
                .commit();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CURRENT_STEP", currentStep);
    }
    @Override protected void onStart() { super.onStart(); Log.d(TAG, "onStart"); }
    @Override protected void onResume() { super.onResume(); Log.d(TAG, "onResume"); }
    @Override protected void onPause() { super.onPause(); Log.d(TAG, "onPause"); }
    @Override protected void onStop() { super.onStop(); Log.d(TAG, "onStop"); }
    @Override protected void onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy"); }
}
