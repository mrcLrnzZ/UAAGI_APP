package com.example.uaagi_app.ui.users;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.uaagi_app.R;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        // Bottom bar tabs
        LinearLayout tabHome = findViewById(R.id.tab_home);
        LinearLayout tabApplied = findViewById(R.id.tab_applied);
        LinearLayout tabCareers = findViewById(R.id.tab_careers);
        LinearLayout tabProfile = findViewById(R.id.tab_profile);

        // Load default fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        // Click listeners
        tabHome.setOnClickListener(v -> switchFragment(new HomeFragment()));
        tabApplied.setOnClickListener(v -> switchFragment(new AppliedJobsFragment()));
        tabCareers.setOnClickListener(v -> switchFragment(new CareersFragment()));
        tabProfile.setOnClickListener(v -> switchFragment(new ProfileFragment()));
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
