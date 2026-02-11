package com.example.uaagi_app.ui.users;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uaagi_app.R;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.ui.users.PreEmpActvityForm.*;
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
        runnable = () -> {
            Intent intent;
            if (Helpers.isLoggedIn(SplashActivity.this)){
                 intent = new Intent(SplashActivity.this, HomePage.class);
            }else{
                 intent = new Intent(SplashActivity.this, Login.class);
            }
            startActivity(intent);
            finish();
        };
        handler.postDelayed(runnable, 6000);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}