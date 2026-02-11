package com.example.uaagi_app.ui.users;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.uaagi_app.R;

public class JobList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.job_list);


        // Initialize the CardView
        CardView fotonjobCard = findViewById(R.id.fotonjob);

        // Set click listener
        fotonjobCard.setOnClickListener(v -> {
            // Create intent to navigate to job details page
            Intent intent = new Intent(JobList.this, JobDesc.class);


            // Start the activity
            startActivity(intent);
        });

    }
}