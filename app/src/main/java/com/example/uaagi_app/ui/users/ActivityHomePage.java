package com.example.uaagi_app.ui.users;


import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uaagi_app.network.Services.NotificationService;
import com.example.uaagi_app.ui.users.FragmentsHomePage.Careers;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.TimeAgo;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.NotificationModel;
import com.example.uaagi_app.data.repository.NotificationRepository;
import com.example.uaagi_app.network.Realtime.NotificationCenter;
import com.example.uaagi_app.network.Realtime.PusherManager;
import com.example.uaagi_app.ui.users.FragmentsHomePage.AppliedJobs;
import com.example.uaagi_app.ui.users.FragmentsHomePage.Home;
import com.example.uaagi_app.ui.users.FragmentsHomePage.Profile;
import com.example.uaagi_app.ui.users.FragmentsHomePage.Notification;
import com.example.uaagi_app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

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
    long eventTime = System.currentTimeMillis();
    private ImageView notifIcon;
    private PusherManager pusherManager;
    private static final int ANIMATION_DURATION = 300;

    private String currentSelectedTab = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        checkAndRequestPermissions();
        initializeNotificationSystem();

        if (savedInstanceState == null) {
            UiHelpers.switchToFragment(getSupportFragmentManager(), new Home(), "home");
        }

        initializeViews();
        setupBackHandler();

        tabHome.setOnClickListener(v -> {
            if (isClickable()) { // Check if safe to click
                UiHelpers.switchToFragment(getSupportFragmentManager(), new Home(), "home");
                setSelectedTab("home");
                logFragmentStack(getSupportFragmentManager());
            }
        });

        tabApplied.setOnClickListener(v -> {
            if (isClickable()) {
                UiHelpers.switchToFragment(getSupportFragmentManager(), new AppliedJobs(), "applied");
                setSelectedTab("applied");
                logFragmentStack(getSupportFragmentManager());
            }
        });

        tabCareers.setOnClickListener(v -> {
            if (isClickable()) {
                UiHelpers.switchToFragment(getSupportFragmentManager(), new Careers(), "careers");
                setSelectedTab("careers");
                logFragmentStack(getSupportFragmentManager());
            }
        });

        tabProfile.setOnClickListener(v -> {
            if (isClickable()) {
                UiHelpers.switchToFragment(getSupportFragmentManager(), new Profile(), "profile");
                setSelectedTab("profile");
                logFragmentStack(getSupportFragmentManager());
            }
        });

        notifIcon.setOnClickListener(v -> {
            View notifDot = findViewById(R.id.notifDot);
            notifDot.setVisibility(View.GONE);
            UiHelpers.switchToFragment(getSupportFragmentManager(), new Notification());
        });
        logFragmentStack(getSupportFragmentManager());
    }

    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        101);
            }
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100
                );
            }
        }
    }

    private void initializeNotificationSystem() {
        FirebaseMessaging.getInstance().subscribeToTopic("all_users")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("FCM", "Subscribed to all_users topic");
                    }
                });

        int userId = SessionManager.getInstance(this).getUserId();
        NotificationService service = new NotificationService(this);

        service.fetchUserNotifications(userId,
                new NotificationService.NotificationCallback() {
                    @Override
                    public void onResponse() {
                        // Pusher will deliver notifications
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });

        pusherManager = new PusherManager();
        pusherManager.connect();

        pusherManager.subscribeToUserChannel(userId, (title, message, createdAt) -> {
            runOnUiThread(() -> handleRealtimeNotification(title, message, createdAt));
        });

        pusherManager.subscribeToPublicChannel((title, message, createdAt) -> {
            runOnUiThread(() -> handleRealtimeNotification(title, message, createdAt));
        });
    }

    private void handleRealtimeNotification(String title, String message, String createdAt) {
        View notifDot = findViewById(R.id.notifDot);
        if (notifDot != null) {
            notifDot.setVisibility(View.VISIBLE);
        }

        String timeAgo = TimeAgo.getTimeAgo(createdAt);
        NotificationModel notif = new NotificationModel(title, message, timeAgo);

        NotificationRepository.getInstance().addNotification(notif);
        NotificationCenter.notify(title, message, timeAgo);
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

        notifIcon = findViewById(R.id.notifIcon);
    }

    private void setSelectedTab(String selectedTab) {
        if (currentSelectedTab.equals(selectedTab)) {
            return;
        }

        if (currentSelectedTab.equals("home")) {
            animateToInactive(iconHome, textHome, indicatorHome);
        } else if (currentSelectedTab.equals("applied")) {
            animateToInactive(iconApplied, textApplied, indicatorApplied);
        } else if (currentSelectedTab.equals("careers")) {
            animateToInactive(iconCareers, textCareers, indicatorCareers);
        } else if (currentSelectedTab.equals("profile")) {
            animateToInactive(iconProfile, textProfile, indicatorProfile);
        }

        if (selectedTab.equals("home")) {
            animateToActive(iconHome, textHome, indicatorHome);
        } else if (selectedTab.equals("applied")) {
            animateToActive(iconApplied, textApplied, indicatorApplied);
        } else if (selectedTab.equals("careers")) {
            animateToActive(iconCareers, textCareers, indicatorCareers);
        } else if (selectedTab.equals("profile")) {
            animateToActive(iconProfile, textProfile, indicatorProfile);
        }

        currentSelectedTab = selectedTab;
    }

    private void animateToActive(ImageView icon, TextView text, View indicator) {
        animateColorFilter(icon, Color.parseColor("#99FFFFFF"), Color.WHITE);

        animateTextColor(text, Color.parseColor("#99FFFFFF"), Color.WHITE);

        text.setTypeface(null, Typeface.BOLD);

        animateIndicatorIn(indicator);

        animateScale(icon);
    }

    private void animateToInactive(ImageView icon, TextView text, View indicator) {
        animateColorFilter(icon, Color.WHITE, Color.parseColor("#666666")); // dark gray

        animateTextColor(text, Color.WHITE, Color.parseColor("#666666"));

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
    private void setupBackHandler() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getSupportFragmentManager();

                logFragmentStack(fm);

                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void logFragmentStack(FragmentManager fragmentManager) {

        List<Fragment> fragments = fragmentManager.getFragments();

        Log.d("FRAGMENT_DEBUG", "----- Active Fragments -----");

        for (Fragment fragment : fragments) {
            if (fragment != null) {
                Log.d("FRAGMENT_DEBUG",
                        "Fragment: " + fragment.getClass().getSimpleName()
                                + " | Tag: " + fragment.getTag()
                                + " | Visible: " + fragment.isVisible()
                                + " | Added: " + fragment.isAdded());
            }
        }

        Log.d("FRAGMENT_DEBUG", "BackStack Count: " + fragmentManager.getBackStackEntryCount());

        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
            Log.d("FRAGMENT_DEBUG", "BackStack[" + i + "] = " + entry.getName());
        }

        Log.d("FRAGMENT_DEBUG", "---------------------------");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pusherManager != null) {
            pusherManager.disconnect();
        }
    }
}
