package com.example.uaagi_app.ui.users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.uaagi_app.R;
public class apply_options extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.apply_options);

        // Only handle back button
        ImageButton btnBack = findViewById(R.id.btnBackToDesc);
        btnBack.setOnClickListener(v -> finish());
    }
}
