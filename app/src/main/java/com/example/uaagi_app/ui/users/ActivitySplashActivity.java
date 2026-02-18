package com.example.uaagi_app.ui.users;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.PreEmpForm;
import com.example.uaagi_app.utils.Helpers;

public class ActivitySplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenLifecycle";
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SplashAct onCreate()");
        int userId = Helpers.getUserId(this);
        Log.d(TAG, "User ID: " + userId);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        runnable = () -> {
            Intent intent;
            if (Helpers.isLoggedIn(ActivitySplashActivity.this)){
                 intent = new Intent(ActivitySplashActivity.this, PreEmpForm.class);
            }else{
                 intent = new Intent(ActivitySplashActivity.this, ActivityLoginPage.class);
            }
            startActivity(intent);
            finish();
        };
        handler.postDelayed(runnable, 0);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}