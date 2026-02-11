package com.example.uaagi_app.ui.users.PreEmpActvityForm;

import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.uaagi_app.R;

public class PreEmpStepperActivity extends AppCompatActivity {
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
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.step_container, new PreEmpFormStep1())
                    .commit();
        }
    }
    public void nextStep(Fragment fragment) {
        changeStep(1);
        highlightSteps();
        scrollView.post(() -> scrollView.smoothScrollTo(0, 0));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.step_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    public void previousStep(Fragment fragment) {
        changeStep(-1);
        highlightSteps();
        scrollView.post(() -> scrollView.smoothScrollTo(0, 0));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.step_container, fragment)
                .commit();
    }
    private void changeStep(int direction) {
        currentStep += direction;
        currentStep = Math.max(1, Math.min(TOTAL_STEPS, currentStep));
        Toast.makeText(this, "Step " + currentStep + " of " + TOTAL_STEPS, Toast.LENGTH_SHORT).show();
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

        if (currentStep >= 1 && currentStep <= steps.length) {
            steps[currentStep - 1].setBackgroundResource(R.drawable.circle_white);
            steps[currentStep - 1].setTextColor(getResources().getColor(R.color.Deepwater));
            steps[currentStep - 1].setTextSize(18);
            stepsTitle[currentStep - 1].setTextColor(getResources().getColor(R.color.White));
        }
    }
    private void unHighlightSteps() {
        for (TextView step : steps) {
            step.setBackgroundResource(R.drawable.circle_outline);
            step.setTextColor(getResources().getColor(R.color.White));
            step.setTextSize(16);
        }
        for (TextView step : stepsTitle) {
            step.setTextColor(getResources().getColor(R.color.PaleBlue));

        }
    }
    @Override protected void onStart() { super.onStart(); Log.d(TAG, "onStart"); }
    @Override protected void onResume() { super.onResume(); Log.d(TAG, "onResume"); }
    @Override protected void onPause() { super.onPause(); Log.d(TAG, "onPause"); }
    @Override protected void onStop() { super.onStop(); Log.d(TAG, "onStop"); }
    @Override protected void onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy"); }
}
