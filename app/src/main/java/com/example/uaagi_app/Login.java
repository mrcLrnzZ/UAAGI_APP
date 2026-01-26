package com.example.uaagi_app;

import android.os.Build;
import android.view.Window;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Change status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(Color.parseColor("#002F62"));
        }

        // Slide-up animation for the CardView
        CardView loginCard = findViewById(R.id.loginCard);

        // Load the animation from res/anim/slide_up.xml
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        // Start the animation
        loginCard.startAnimation(slideUp);
    }
}
