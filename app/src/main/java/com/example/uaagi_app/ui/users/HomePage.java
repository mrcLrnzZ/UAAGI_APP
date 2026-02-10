package com.example.uaagi_app.ui.users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.uaagi_app.R;

public class HomePage extends AppCompatActivity {
    private static final String TAG = "MainActivityLifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MainAct onCreate()");
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page);

        // Initialize the CardView
        CardView firstJobCard = findViewById(R.id.firstjob);

        // Set click listener
        firstJobCard.setOnClickListener(v -> {
            // Create intent to navigate to job details page
            Intent intent = new Intent(HomePage.this, JobDesc.class);


            // Start the activity
            startActivity(intent);
        });
    }
}