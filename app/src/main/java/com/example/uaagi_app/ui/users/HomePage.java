package com.example.uaagi_app.ui.users;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uaagi_app.R;

public class HomePage extends AppCompatActivity {
    private static final String TAG = "MainActivityLifecycle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MainAct onCreate()");
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page);

    }
}