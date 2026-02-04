package com.example.uaagi_app.ui.users;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uaagi_app.MainActivity;
import com.example.uaagi_app.R;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenLifecycle";
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SplashAct onCreate()");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(runnable, 6000);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}