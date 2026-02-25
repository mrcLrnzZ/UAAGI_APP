package com.example.uaagi_app.ui.users;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.users.FragmentsHomePage.AppliedJobs;
import com.example.uaagi_app.ui.users.FragmentsHomePage.Careers;
import com.example.uaagi_app.ui.users.FragmentsHomePage.Home;
import com.example.uaagi_app.ui.users.FragmentsHomePage.Profile;
import com.example.uaagi_app.ui.utils.UiHelpers;

public class ActivityHomePage extends AppCompatActivity {

    private LinearLayout tabHome;
    private LinearLayout tabApplied;
    private LinearLayout tabCareers;
    private LinearLayout tabProfile;

    private ImageView iconHome;
    private TextView textHome;
    private View indicatorHome;

    private ImageView iconApplied;
    private TextView textApplied;
    private View indicatorApplied;

    private ImageView iconCareers;
    private TextView textCareers;
    private View indicatorCareers;

    private ImageView iconProfile;
    private TextView textProfile;
    private View indicatorProfile;

    private long lastClickTime = 0;

    private static final int ANIMATION_DURATION = 300;

    private int currentSelectedTab = R.id.tab_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initializeViews();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Home())
                    .commit();
            setSelectedTab(R.id.tab_home);
        }

        tabHome.setOnClickListener(v -> {
            if (isClickable()) { // Check if safe to click
                UiHelpers.switchToFragment(getSupportFragmentManager(), new Home());
                setSelectedTab(R.id.tab_home);
            }
        });

        tabApplied.setOnClickListener(v -> {
            if (isClickable()) {
                UiHelpers.switchToFragment(getSupportFragmentManager(), new AppliedJobs());
                setSelectedTab(R.id.tab_applied);
            }
        });

        tabCareers.setOnClickListener(v -> {
            if (isClickable()) {
                UiHelpers.switchToFragment(getSupportFragmentManager(), new Careers());
                setSelectedTab(R.id.tab_careers);
            }
        });

        tabProfile.setOnClickListener(v -> {
            if (isClickable()) {
                UiHelpers.switchToFragment(getSupportFragmentManager(), new Profile());
                setSelectedTab(R.id.tab_profile);
            }
        });
    }

    private boolean isClickable() {
        long currentTime = System.currentTimeMillis();
        // If less than 300ms (animation duration) has passed, ignore the click
        if (currentTime - lastClickTime < ANIMATION_DURATION) {
            return false;
        }
        lastClickTime = currentTime;
        return true;
    }

    private void initializeViews() {
        tabHome = findViewById(R.id.tab_home);
        tabApplied = findViewById(R.id.tab_applied);
        tabCareers = findViewById(R.id.tab_careers);
        tabProfile = findViewById(R.id.tab_profile);

        iconHome = findViewById(R.id.icon_home);
        textHome = findViewById(R.id.text_home);
        indicatorHome = findViewById(R.id.indicator_home);

        iconApplied = findViewById(R.id.icon_applied);
        textApplied = findViewById(R.id.text_applied);
        indicatorApplied = findViewById(R.id.indicator_applied);

        iconCareers = findViewById(R.id.icon_careers);
        textCareers = findViewById(R.id.text_careers);
        indicatorCareers = findViewById(R.id.indicator_careers);

        iconProfile = findViewById(R.id.icon_profile);
        textProfile = findViewById(R.id.text_profile);
        indicatorProfile = findViewById(R.id.indicator_profile);
    }

    private void setSelectedTab(int selectedTabId) {
        if (currentSelectedTab == selectedTabId) {
            return;
        }

        if (currentSelectedTab == R.id.tab_home) {
            animateToInactive(iconHome, textHome, indicatorHome);
        } else if (currentSelectedTab == R.id.tab_applied) {
            animateToInactive(iconApplied, textApplied, indicatorApplied);
        } else if (currentSelectedTab == R.id.tab_careers) {
            animateToInactive(iconCareers, textCareers, indicatorCareers);
        } else if (currentSelectedTab == R.id.tab_profile) {
            animateToInactive(iconProfile, textProfile, indicatorProfile);
        }

        if (selectedTabId == R.id.tab_home) {
            animateToActive(iconHome, textHome, indicatorHome);
        } else if (selectedTabId == R.id.tab_applied) {
            animateToActive(iconApplied, textApplied, indicatorApplied);
        } else if (selectedTabId == R.id.tab_careers) {
            animateToActive(iconCareers, textCareers, indicatorCareers);
        } else if (selectedTabId == R.id.tab_profile) {
            animateToActive(iconProfile, textProfile, indicatorProfile);
        }

        currentSelectedTab = selectedTabId;
    }

    private void animateToActive(ImageView icon, TextView text, View indicator) {
        animateColorFilter(icon, Color.parseColor("#99FFFFFF"), Color.WHITE);

        animateTextColor(text, Color.parseColor("#99FFFFFF"), Color.WHITE);

        text.setTypeface(null, Typeface.BOLD);

        animateIndicatorIn(indicator);

        animateScale(icon);
    }

    private void animateToInactive(ImageView icon, TextView text, View indicator) {
        animateColorFilter(icon, Color.WHITE, Color.parseColor("#99FFFFFF"));

        animateTextColor(text, Color.WHITE, Color.parseColor("#99FFFFFF"));

        text.setTypeface(null, Typeface.NORMAL);

        animateIndicatorOut(indicator);
    }

    private void animateColorFilter(ImageView imageView, int fromColor, int toColor) {
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        colorAnimator.setDuration(ANIMATION_DURATION);
        colorAnimator.setInterpolator(new DecelerateInterpolator());
        colorAnimator.addUpdateListener(animator -> {
            imageView.setColorFilter((int) animator.getAnimatedValue());
        });
        colorAnimator.start();
    }

    private void animateTextColor(TextView textView, int fromColor, int toColor) {
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        colorAnimator.setDuration(ANIMATION_DURATION);
        colorAnimator.setInterpolator(new DecelerateInterpolator());
        colorAnimator.addUpdateListener(animator -> {
            textView.setTextColor((int) animator.getAnimatedValue());
        });
        colorAnimator.start();
    }

    private void animateIndicatorIn(View indicator) {
        indicator.setVisibility(View.VISIBLE);
        indicator.setAlpha(0f);
        indicator.setScaleX(0.3f);

        indicator.animate()
                .alpha(1f)
                .scaleX(1f)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void animateIndicatorOut(View indicator) {
        indicator.animate()
                .alpha(0f)
                .scaleX(0.3f)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> indicator.setVisibility(View.GONE))
                .start();
    }

    private void animateScale(ImageView icon) {
        icon.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(150)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    icon.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(150)
                            .setInterpolator(new DecelerateInterpolator())
                            .start();
                })
                .start();
    }

}